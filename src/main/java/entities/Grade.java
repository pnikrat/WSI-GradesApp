package entities;

import com.fasterxml.jackson.annotation.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import utilities.ObjectIdJaxbAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by student on 26.02.2017.
 */
@Embedded
@XmlRootElement
public class Grade {
    @Id
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId objectId;

    private GradeValue concreteGrade;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "CET")
    private Date dateOfGrade;
    @Reference
    private Student concreteStudent;

    public Grade() {

    }

    public Grade(GradeValue concreteGrade, Date dateOfGrade, Student concreteStudent) {
        this.concreteGrade = concreteGrade;
        this.dateOfGrade = dateOfGrade;
        this.concreteStudent = concreteStudent;
    }

    public Date getDateOfGrade() {
        return dateOfGrade;
    }

    public Student getConcreteStudent() {
        return concreteStudent;
    }

    public GradeValue getConcreteGrade() {
        return concreteGrade;
    }

    public void setDateOfGrade(Date dateOfGrade) {
        this.dateOfGrade = dateOfGrade;
    }

    public void setConcreteStudent(Student concreteStudent) {
        this.concreteStudent = concreteStudent;
    }

    public void setConcreteGrade(GradeValue value) {
        this.concreteGrade = value;
    }

    @JsonIgnore
    public void replaceGradeId(String gradeId) {
        this.objectId = new ObjectId(gradeId);
    }

    @XmlTransient
    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
}
