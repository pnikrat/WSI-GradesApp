package entitiescontainers;

import entities.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnikrat on 18.03.17.
 */
public class Courses {

    private List<Course> courses = new ArrayList<>();

    public Courses() {

    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course courseToAdd) {
        courses.add(courseToAdd);
    }

    public void removeCourse(Course courseToRemove) {
        courses.remove(courseToRemove);
    }

    public Course findSingleCourse(Integer courseId) {
        return courses.stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
    }
}
