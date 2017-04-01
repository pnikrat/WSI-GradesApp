package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entitiescontainers.Grades;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.query.Query;
import server.MorphiaService;
import utilities.ObjectIdJaxbAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
@Entity("courses")
@XmlRootElement
public class Course {
    @Id
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId objectId;

    private String courseName;
    private String courseInstructor;
    private Grades courseGrades;

    public Course() {

    }

    public Course(String courseName, String courseInstructor) {
        this.courseName = courseName;
        this.courseInstructor = courseInstructor;
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

    @JsonIgnore
    public void replaceCourseId(String courseId) {
        this.objectId = new ObjectId(courseId);
    }

    @XmlTransient
    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
}