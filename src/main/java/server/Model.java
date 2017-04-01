package server;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entities.Course;
import entities.Grade;
import entities.GradeValue;
import entities.Student;
import entitiescontainers.Courses;
import entitiescontainers.Grades;
import entitiescontainers.Students;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import utilities.DateUtilities;

import javax.swing.text.Document;

/**
 * Created by student on 26.02.2017.
 */
public class Model {

    private static Model singleton = new Model();
    private MorphiaService morphiaService;
    private Students studentsContainer;
    private Courses coursesContainer;
    

    private Model() {
        setUpDatabase();
        cleanUpDatabaseEntries();
        createEntitiesContainers();
        if (!checkIfDatabaseHasEntries())
            fillCollections(true);
        else
            fillCollections(false);
    }

    private void cleanUpDatabaseEntries() {
        morphiaService.getDatastore().delete(morphiaService.getDatastore().createQuery(Student.class));
        morphiaService.getDatastore().delete(morphiaService.getDatastore().createQuery(Course.class));
    }

    private void setUpDatabase() {
        morphiaService = new MorphiaService();
    }

    private boolean checkIfDatabaseHasEntries() {
        long numberOfStudents = morphiaService.getDatastore().getCount(Student.class);
        long numberOfCourses = morphiaService.getDatastore().getCount(Course.class);
        return !(numberOfStudents == 0 && numberOfCourses == 0);
    }
    
    private void createEntitiesContainers() {
        studentsContainer = new Students(morphiaService);
        coursesContainer = new Courses(morphiaService);
    }

    private void fillCollections(boolean fillDB) {
        Student std1 = new Student("Przemyslaw", "Nikratowicz",
                DateUtilities.fromYearMonthDay(1993, 7,23));
        studentsContainer.addStudent(std1);

        Student std2 = new Student("Jakub", "Piechocinski",
                DateUtilities.fromYearMonthDay(1995, 11, 28));
        studentsContainer.addStudent(std2);

        Grades firstGradesList = new Grades();

        Grade grd1 = new Grade(GradeValue.FIVE, DateUtilities.fromYearMonthDay(2013, 10, 10), std1);
        firstGradesList.addGrade(grd1);
        Grade grd2 = new Grade(GradeValue.FOURHALF, DateUtilities.fromYearMonthDay(2013, 11, 2), std1);
        firstGradesList.addGrade(grd2);
        Grade grd3 = new Grade(GradeValue.THREEHALF, DateUtilities.fromYearMonthDay(2013, 12, 18), std2);
        firstGradesList.addGrade(grd3);

        Grades secondGradesList = new Grades();

        Course crs1 = new Course("Wytwarzanie systemow internetowych", "dr inz. Tomasz Pawlak");
        crs1.setCourseGrades(firstGradesList);
        coursesContainer.addCourse(crs1);

        Course crs2 = new Course("Multimedialne interfejsy uzytkownika", "dr inz Bartlomiej Predki");
        crs2.setCourseGrades(secondGradesList);
        coursesContainer.addCourse(crs2);

        if (fillDB) {
            morphiaService.getDatastore().save(std1);
            morphiaService.getDatastore().save(std2);
            morphiaService.getDatastore().save(crs1);
            morphiaService.getDatastore().save(crs2);
        }
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
