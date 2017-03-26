package entitiescontainers;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.Grade;
import entities.Student;
import org.mongodb.morphia.query.Query;
import server.MorphiaService;

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

    private List<Grade> grades = new ArrayList<>();

    public Grades() {

    }

    @XmlElement(name="grade")
    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public void addGrade(Grade newGrade) {
        grades.add(newGrade);
    }

    public void removeGrade(Grade gradeToRemove) {
        grades.remove(gradeToRemove);
    }

    public Grade findSingleGrade(Integer gradeId) {
        return grades.stream().filter(x -> x.getGradeId().equals(gradeId)).findFirst().orElse(null);
    }

    public void removeStudentGrades(Student studentForGradeRemoval) {
        List<Grade> gradesCopy = new ArrayList<>(grades);
        for (Grade singleGrade : gradesCopy) {
            if (singleGrade.getConcreteStudent().equals(studentForGradeRemoval))
                removeGrade(singleGrade);
        }
    }

}
