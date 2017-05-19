package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import utilities.ObjectIdJaxbAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
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
    @Embedded
    private List<Grade> courseGrades;

    public Course() {

    }

    public Course(String courseName, String courseInstructor) {
        this.courseName = courseName;
        this.courseInstructor = courseInstructor;
        this.courseGrades = new ArrayList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    @XmlElement(name="grades")
    public List<Grade> getCourseGrades() {
        return courseGrades;
    }

    public void addGrade(Grade newGrade) {
        if (courseGrades == null) {
            courseGrades = new ArrayList<>();
        }
        courseGrades.add(newGrade);
    }

    public void removeGrade(Grade gradeForRemoval) {
        courseGrades.remove(gradeForRemoval);
    }

    public Grade findSingleGrade(String gradeId) {
        if (courseGrades == null)
            return null;
        ObjectId convertedGradeId = new ObjectId(gradeId);
        return courseGrades.stream().filter(x -> x.getObjectId().equals(convertedGradeId)).findFirst().orElse(null);
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