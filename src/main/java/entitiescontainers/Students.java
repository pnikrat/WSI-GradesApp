package entitiescontainers;

import entities.Student;
import org.mongodb.morphia.query.Query;
import server.MorphiaService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnikrat on 18.03.17.
 */
public class Students {

    private List<Student> students = new ArrayList<>();
    private MorphiaService morphiaService;
    
    public Students(MorphiaService morphiaService) {
        this.morphiaService = morphiaService;
    }

    public List<Student> getStudents() {
        Query<Student> getAllStudents = morphiaService.getDatastore().createQuery(Student.class);
        return getAllStudents.asList();
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student studentToAdd) {
        students.add(studentToAdd);
        morphiaService.getDatastore().save(studentToAdd);
    }

    public void removeStudent(Student studentToRemove) {
        students.remove(studentToRemove);
    }

    public Student findSingleStudent(Integer studentIndex) {
        return students.stream().filter(x -> x.getIndex().equals(studentIndex)).findFirst().orElse(null);
    }
}
