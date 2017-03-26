package entitiescontainers;

import entities.Student;
import server.MorphiaService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnikrat on 18.03.17.
 */
public class Students {

    private List<Student> students = new ArrayList<>();

    public Students() {

    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student studentToAdd) {
        students.add(studentToAdd);
    }

    public void removeStudent(Student studentToRemove) {
        students.remove(studentToRemove);
    }

    public Student findSingleStudent(Integer studentIndex) {
        return students.stream().filter(x -> x.getIndex().equals(studentIndex)).findFirst().orElse(null);
    }
}
