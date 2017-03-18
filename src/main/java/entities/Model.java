package entities;

import entitiescontainers.Courses;
import entitiescontainers.Grades;
import entitiescontainers.Students;
import utilities.DateUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
public class Model {

    private static Model singleton = new Model();
    private Students studentsContainer = new Students();
    private Courses coursesContainer = new Courses();

    private Model() {
        fillCollections();
    }

    private void fillCollections() {

        Student std1 = new Student("Przemyslaw", "Nikratowicz",
                DateUtilities.fromYearMonthDay(1993, 7,23));
        studentsContainer.addStudent(std1);

        Student std2 = new Student("Jakub", "Piechocinski",
                DateUtilities.fromYearMonthDay(1995, 11, 28));
        studentsContainer.addStudent(std2);

        Grades fullGradesList = new Grades();

        Grade grd1 = new Grade(GradeValue.FIVE, DateUtilities.fromYearMonthDay(2013, 10, 10), std1);
        fullGradesList.addGrade(grd1);
        Grade grd2 = new Grade(GradeValue.FOURHALF, DateUtilities.fromYearMonthDay(2013, 11, 2), std1);
        fullGradesList.addGrade(grd2);
        Grade grd3 = new Grade(GradeValue.THREEHALF, DateUtilities.fromYearMonthDay(2013, 12, 18), std2);
        fullGradesList.addGrade(grd3);

        Grades secondGradesList = new Grades();

        Course crs1 = new Course("Wytwarzanie systemow internetowych", "dr inz. Tomasz Pawlak");
        crs1.setCourseGrades(fullGradesList);
        coursesContainer.addCourse(crs1);
        Course crs2 = new Course("Multimedialne interfejsy uzytkownika", "dr inz Bartlomiej Predki");
        crs2.setCourseGrades(secondGradesList);
        coursesContainer.addCourse(crs2);
    }

    public static Model getInstance() {
        return singleton;
    }

    public Students getStudentsContainer() {
        return studentsContainer;
    }

    public Courses getCoursesContainer() {
        return coursesContainer;
    }
}
