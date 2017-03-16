package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnikrat on 13.03.17.
 */
@XmlRootElement(name="grades")
public class Grades {

    public Grades() {

    }

    private List<Grade> grades = new ArrayList<>();

    public void addGrade(Grade newGrade) {
        grades.add(newGrade);
    }

    public void removeGrade(Grade gradeToRemove) {
        grades.remove(gradeToRemove);
    }

    public Grade getSingleGrade(Integer gradeId) {
        Grade theGrade = grades.stream().filter(x -> x.getGradeId().equals(gradeId)).findFirst().orElse(null);
        return theGrade;
    }
    @XmlElement(name="grade")
    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
