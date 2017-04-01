package entitiescontainers;

import entities.Course;
import entities.Student;
import org.mongodb.morphia.query.Query;
import server.MorphiaService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnikrat on 18.03.17.
 */
public class Courses {

    private List<Course> courses = new ArrayList<>();
    private MorphiaService morphiaService;
    
    public Courses(MorphiaService morphiaService) {
        this.morphiaService = morphiaService;
    }

    public List<Course> getCourses() {
        Query<Course> getAllCourses = morphiaService.getDatastore().createQuery(Course.class);
        return getAllCourses.asList();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course courseToAdd) {
        courses.add(courseToAdd);
        morphiaService.getDatastore().save(courseToAdd);
    }

    public void removeCourse(Course courseToRemove) {
        courses.remove(courseToRemove);
    }

    public Course findSingleCourse(Integer courseId) {
        return courses.stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
    }

    public void removeStudentGrades(Student studentForGradeRemoval) {
        for (Course singleCourse : courses) {
            singleCourse.getCourseGrades().removeStudentGrades(studentForGradeRemoval);
        }
    }
}
