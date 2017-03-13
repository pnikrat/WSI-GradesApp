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
    private List<Grade> courseGrades = new ArrayList<>();

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

    @XmlElement(name="entities.Grade")
    public List<Grade> getCourseGrades() {
        return courseGrades;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public void setCourseGrades(List<Grade> courseGrades) {
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