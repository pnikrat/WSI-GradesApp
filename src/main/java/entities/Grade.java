package entities;

import com.fasterxml.jackson.annotation.*;
import org.mongodb.morphia.annotations.Reference;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by student on 26.02.2017.
 */
@XmlRootElement
public class Grade {
    private static Integer idCounter = 0;

    @XmlElement
    private Integer gradeId;
    private GradeValue concreteGrade;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "CET")
    private Date dateOfGrade;
    @Reference
    private Student concreteStudent;

    public Grade() {

    }

    public Grade(GradeValue concreteGrade, Date dateOfGrade, Student concreteStudent) {
        this.gradeId = createUniqueId();
        this.concreteGrade = concreteGrade;
        this.dateOfGrade = dateOfGrade;
        this.concreteStudent = concreteStudent;
    }

    private static synchronized Integer createUniqueId() {
        return idCounter++;
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

    public Integer getGradeId() {
        return gradeId;
    }

    public Integer setGradeId() {
        this.gradeId = createUniqueId();
        return this.gradeId;
    }

    @JsonIgnore
    public void replaceGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
}
