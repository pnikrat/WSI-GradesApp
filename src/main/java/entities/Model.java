package entities;

import utilities.DateUtilities;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
public class Model {
    private static Model singleton = new Model();
    private List<Course> Courses = new ArrayList<>();
    private Grades fullGradesList = new Grades();
    private List<Student> Students = new ArrayList<>();

    private Model() {
        fillCollections();
    }

    private void fillCollections() {

        Student std1 = new Student("Przemyslaw", "Nikratowicz",
                DateUtilities.fromYearMonthDay(1993, 7,23));
        Students.add(std1);

        Student std2 = new Student("Jakub", "Piechocinski",
                DateUtilities.fromYearMonthDay(1995, 11, 28));
        Students.add(std2);

        Grade grd1 = new Grade(GradeValue.FIVE, DateUtilities.fromYearMonthDay(2013, 10, 10), std1);
        fullGradesList.addGrade(grd1);
        Grade grd2 = new Grade(GradeValue.FOURHALF, DateUtilities.fromYearMonthDay(2013, 11, 2), std1);
        fullGradesList.addGrade(grd2);
        Grade grd3 = new Grade(GradeValue.THREEHALF, DateUtilities.fromYearMonthDay(2013, 12, 18), std2);
        fullGradesList.addGrade(grd3);

        Grades secondGradesList = new Grades();

        Course crs1 = new Course("Wytwarzanie systemow internetowych", "dr inz. Tomasz Pawlak");
        crs1.setCourseGrades(fullGradesList);
        Courses.add(crs1);
        Course crs2 = new Course("Multimedialne interfejsy uzytkownika", "dr inz Bartlomiej Predki");
        crs2.setCourseGrades(secondGradesList);
        Courses.add(crs2);
    }

    public static Model getInstance() {
        return singleton;
    }

    public List<Student> getStudents() {
        return Students;
    }

    public List<Course> getCourses() {
        return Courses;
    }

    public Grades getGrades() {
        return fullGradesList;
    }
}
