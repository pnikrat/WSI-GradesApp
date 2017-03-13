package entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by student on 26.02.2017.
 */
@XmlRootElement
public class Student {
    private static Integer indexCounter = 0;

    private Integer index;
    private String firstName;
    private String lastName;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "CET")
    private Date birthDate;

    public Student() {

    }

    public Student(String firstName, String lastName, Date birthDate) {
        this.index = createUniqueIndex();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public static synchronized Integer createUniqueIndex() {
        return indexCounter++;
    }

    public Integer getIndex() {
        return index;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Integer setIndex() {
        this.index = createUniqueIndex();
        return this.index;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
