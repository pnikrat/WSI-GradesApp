package entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by student on 26.02.2017.
 */
@XmlRootElement
public class Grade {
    public enum GradeValue {
        TWO(2.0), TWOHALF(2.5), THREE(3.0), THREEHALF(3.5), FOUR(4.0), FOURHALF(4.5), FIVE(5.0);

        GradeValue(double value) {
            this.value = value;
        }
        public double value;
    };
    private static Integer idCounter = 0;
    private Integer gradeId;
    private GradeValue concreteGrade;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "CET")
    private Date dateOfGrade;
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

    public double getConcreteGrade() {
        return concreteGrade.value;
    }

    public void setDateOfGrade(Date dateOfGrade) {
        this.dateOfGrade = dateOfGrade;
    }

    public void setConcreteStudent(Student concreteStudent) {
        this.concreteStudent = concreteStudent;
    }

    public void setConcreteGrade(double value) {
        this.concreteGrade.value = value;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId() {
        this.gradeId = createUniqueId();
    }
}
