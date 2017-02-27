package entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
public class Model {
    private static Model singleton = new Model();
    private List<Course> Courses = new ArrayList<>();
    //private HashMap<Integer, entities.Course> Courses = new HashMap<>();
    private List<Grade> Grades = new ArrayList<>();
    //private HashMap<Integer, entities.Grade> Grades = new HashMap<>();
    private List<Student> Students = new ArrayList<>();
    //private HashMap<Integer, entities.Student> Students = new HashMap<>();

    private Model() {
        fillCollections();
    }

    private void fillCollections() {
        Student std1 = new Student("Przemyslaw", "Nikratowicz", Date.from(Instant.now()));
        Students.add(std1);

        Student std2 = new Student("Jakub", "Piechocinski", Date.from(Instant.now()));
        Students.add(std2);

        Grade grd1 = new Grade(Grade.GradeValue.FIVE, Date.from(Instant.now()), std1);
        Grades.add(grd1);
        Grade grd2 = new Grade(Grade.GradeValue.FOURHALF, Date.from(Instant.now()), std1);
        Grades.add(grd2);
        Grade grd3 = new Grade(Grade.GradeValue.THREEHALF, Date.from(Instant.now()), std2);
        Grades.add(grd3);

        ArrayList<Grade> grdList = new ArrayList<>();
        grdList.add(grd1);
        grdList.add(grd2);
        grdList.add(grd3);
        ArrayList<Grade> grdList2 = new ArrayList<>();

        Course crs1 = new Course("Wytwarzanie systemow internetowych", "dr inz. Tomasz Pawlak", grdList);
        Courses.add(crs1);
        Course crs2 = new Course("Multimedialne interfejsy uzytkownika", "dr inz Bartlomiej Predki", grdList2);
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

    public List<Grade> getGrades() {
        return Grades;
    }
}
