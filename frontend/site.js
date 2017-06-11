"use strict";
/*global $, ko, console*/

var serverAddress = "http://localhost:9998/";

var DataContainer = function (url, objectIdParameterName) {
    var self = this;
    self.content = ko.observableArray();
    self.url = url;
    
    self.getRequest = function () {
        $.ajax({
            url: self.url,
            dataType: "json"
        })
            .done(function (data) {
                if (self.subscriptionHandler !== undefined) {
                    self.subscriptionHandler.dispose();
                }
                self.content([]);
                data.forEach(function (element, index, array) {
                    if (!ko.utils.arrayFirst(self.content(), function (existingItem, element) {
                            return existingItem[objectIdParameterName] === element[objectIdParameterName];
                        })) {
                        var row_of_data = element;
                        row_of_data = ko.mapping.fromJS(row_of_data);
                        self.content.push(row_of_data);
                        ko.computed(function () {
                            return ko.mapping.toJS(row_of_data);
                        })
                            .subscribe(function () {
                                self.updateRequest(row_of_data);
                            });
                    }

                });
            
                self.subscriptionHandler = self.content.subscribe(function (arrayChanges) {
                    arrayChanges.forEach(function (change) {
                        if (change.status === 'added') {
                            self.postRequest(change.value);
                        } else if (change.status === 'deleted') {
                            self.deleteRequest(change.value);
                        }
                    });
                }, null, "arrayChange");
            
            });
    };

    self.updateRequest = function (row) {
        console.log(row[objectIdParameterName]());
        $.ajax({
            url: self.url + "/" + row[objectIdParameterName](),
            dataType: "json",
            method: "PUT",
            contentType: "application/json",
            data: ko.mapping.toJSON(row, {
                ignore: ["grades", "index", "objectId"]
            })
        });
    };

    self.postRequest = function (row) {
        $.ajax({
            url: self.url,
            method: "POST",
            contentType: "application/json",
            data: ko.mapping.toJSON(row)
        })
            .done(function (data, status, xhr) {
                console.log(xhr.getResponseHeader("Location"));
                console.log(xhr.status);
                if (xhr.status === 201) {
                    $.getJSON(xhr.getResponseHeader("Location"), function (data) {
                        console.log(data);
                        var response = ko.mapping.fromJS(data);
                        row[objectIdParameterName](response[objectIdParameterName]());
                    });
                }
                ko.computed(function () {
                    return ko.mapping.toJS(row);
                })
                    .subscribe(function () {
                        self.updateRequest(row);
                    });
            });
    };
    
    self.deleteRow = function (row) {
        self.content.remove(row);
    };
    
    self.deleteRequest = function (row) {
        $.ajax({
            url: self.url + "/" + row[objectIdParameterName](),
            dataType: "json",
            method: "DELETE",
            contentType: "application/json"
        });
    };
    return self;
};

function ViewModel() {
    var self = this;
    
    self.studentsContainer = new DataContainer(serverAddress + "students", "index");
    self.students = self.studentsContainer.content;
    self.studentsContainer.getRequest();

    self.coursesContainer = new DataContainer(serverAddress + "courses", "objectId");
    self.courses = self.coursesContainer.content;
    self.coursesContainer.getRequest();
    
    self.gradesContainer = new DataContainer(serverAddress, "objectId");
    self.grades = self.gradesContainer.content;
    
    self.initGradesViewModel = function (coursesRow) {
        var currentCourseId = coursesRow.objectId();
        self.gradesContainer.url = serverAddress + "courses/" + currentCourseId + "/grades";
        self.gradesContainer.getRequest();
    };
    

}

var model = new ViewModel();

var student_add_button = "#student_add_button";
var course_add_button = "#course_add_button";
var grade_add_button = "#grade_add_button";

$(student_add_button).click(function () {
    model.students.push(ko.mapping.fromJS({index: null, firstName: null, lastName: null, birthDate: null}));
});

$(course_add_button).click(function () {
    model.courses.push(ko.mapping.fromJS({objectId: null, courseName: null, courseInstructor: null}));
});

$(grade_add_button).click(function () {
    model.grades.push(ko.mapping.fromJS({objectId: null, concreteGrade: null, dateOfGrade: null, concreteStudent: null}));
});


$(document).ready(function () {
//    ko.options.deferUpdates = true;
    ko.applyBindings(model);
});