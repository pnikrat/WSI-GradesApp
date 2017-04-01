package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import utilities.ObjectIdJaxbAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by student on 26.02.2017.
 */
@Entity("students")
@Indexes(
        @Index(value = "index", fields = @Field("index"))
)
@XmlRootElement
public class Student {
    private static Integer indexCounter = 2;

    @Id
    @XmlTransient
    private ObjectId objectId;

    @XmlElement(name = "index")
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

    @JsonIgnore
    public void replaceIndex(Integer index) {
        this.index = index;
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

    @XmlTransient
    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
}
