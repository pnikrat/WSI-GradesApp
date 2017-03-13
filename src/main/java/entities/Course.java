package entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
@XmlRootElement
public class Course {
    private static Integer idCounter = 0;

    private Integer courseId;
    private String courseName;
    private String courseInstructor;
    private Grades courseGrades;

    public Course() {

    }

    public Course(String courseName, String courseInstructor) {
        this.courseId = createUniqueId();
        this.courseName = courseName;
        this.courseInstructor = courseInstructor;
    }

    private static synchronized Integer createUniqueId() {
        return idCounter++;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public Grades getCourseGrades() {
        return courseGrades;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public void setCourseGrades(Grades courseGrades) {
        this.courseGrades = courseGrades;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public Integer setCourseId() {
        this.courseId = createUniqueId();
        return this.courseId;
    }
}