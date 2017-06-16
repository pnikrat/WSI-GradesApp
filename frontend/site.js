"use strict";
/*global $, ko, console*/

var serverAddress = "http://localhost:9998/";

var DataContainer = function (url, objectIdParameterName) {
    var self = this;
    self.content = ko.observableArray();
    self.url = url;
    self.otherContainerRef = {};
    
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
                        if (self.additionalMapping !== undefined) {
                            row_of_data = ko.mapping.fromJS(row_of_data, self.additionalMapping);
                        } else {
                            row_of_data = ko.mapping.fromJS(row_of_data, {ignore: ["grades"]});
                        }
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
        $.ajax({
            url: self.url + "/" + row[objectIdParameterName](),
            dataType: "json",
            method: "PUT",
            contentType: "application/json",
            data: ko.mapping.toJSON(row)
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
                if (xhr.status === 201) {
                    $.getJSON(xhr.getResponseHeader("Location"), function (data) {
                        var response = ko.mapping.fromJS(data);
                        row[objectIdParameterName](response[objectIdParameterName]());
                    });
                }
                ko.computed(function () {
                    return ko.mapping.toJS(row, self.additionalMapping);
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
            contentType: "application/json",
            beforeSend: function (xhr) {
                 //before student delete -> disable on DELETE subscriptions and empty grades VM to ensure data integrity
                if (objectIdParameterName === 'index') {
                    if (self.otherContainerRef.subscriptionHandler !== undefined) {
                        self.otherContainerRef.subscriptionHandler.dispose();
                    }
                    self.otherContainerRef.content([]);
                }
            }
        });
    };
    return self;
};


function StudentWithDisplayName(data) {
    function nameCheck(name) {
        return name === null ? '<field not set!>' : name;
    }
    
    ko.mapping.fromJS(data, {}, this);
    this.studentDisplayName = ko.computed(function () {
        return this.index() + ' - ' + nameCheck(this.firstName()) + ' ' + nameCheck(this.lastName());
    }, this);
}

var studentsMapping = {
    create: function (options) {
        return new StudentWithDisplayName(options.data);
    },
    key: function (data) {
        return ko.utils.unwrapObservable(data.index);
    }
};

function ViewModel() {
    var self = this;
    
    self.studentsContainer = new DataContainer(serverAddress + "students", "index");
    self.students = self.studentsContainer.content;
    self.studentsContainer.additionalMapping = studentsMapping;
    self.studentsContainer.getRequest();

    self.coursesContainer = new DataContainer(serverAddress + "courses", "objectId");
    self.courses = self.coursesContainer.content;
    self.coursesContainer.getRequest();
    
    self.gradesContainer = new DataContainer(serverAddress, "objectId");
    self.gradesContainer.currentCourseName = ko.observable();
    self.grades = self.gradesContainer.content;
    
    self.studentsContainer.otherContainerRef = self.gradesContainer;
    
    self.initGradesViewModel = function (coursesRow) {
        var currentCourseId, currentCourseName;
        if (coursesRow === null) {
            currentCourseId = localStorage.getItem("currentCourseId");
            currentCourseName = localStorage.getItem("currentCourseName");
        } else {
            currentCourseId = coursesRow.objectId();
            localStorage.setItem("currentCourseId", currentCourseId);
            currentCourseName = coursesRow.courseName();
            localStorage.setItem("currentCourseName", currentCourseName);
        }
        self.gradesContainer.currentCourseName(currentCourseName);
        self.gradesContainer.url = serverAddress + "courses/" + currentCourseId + "/grades";
        self.gradesContainer.getRequest();
    };
}

var model = new ViewModel();
if (localStorage.getItem("currentCourseId") !== null && window.location.hash === '#grades_list') {
    model.initGradesViewModel(null);
}

var studentAddButton = "#student_add_button";
var courseAddButton = "#course_add_button";
var gradeAddButton = "#grade_add_button";
var dummyStudent = {
    index: null,
    firstName: null,
    lastName: null,
    birthDate: null
};
var dummyCourse = {
    objectId: null,
    courseName: null,
    courseInstructor: null
};
var dummyGrade = {
    objectId: null,
    concreteGrade: null,
    concreteStudentIndex: null,
    dateOfGrade: null

};

$(studentAddButton).click(function () {
    model.students.push(ko.mapping.fromJS(dummyStudent, studentsMapping));
});

$(courseAddButton).click(function () {
    model.courses.push(ko.mapping.fromJS(dummyCourse));
});

$(gradeAddButton).click(function () {
    model.grades.push(ko.mapping.fromJS(dummyGrade));
});


$(document).ready(function () {
    ko.applyBindings(model);
});