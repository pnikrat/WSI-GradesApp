package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entitiescontainers.Grades;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import utilities.ObjectIdJaxbAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by student on 26.02.2017.
 */
@Entity("courses")
@XmlRootElement
public class Course {
    private static Integer idCounter = 0;

    @Id
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId objectId;

    @XmlElement
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

    @XmlElement(name="grades")
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

    @JsonIgnore
    public void replaceCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
}