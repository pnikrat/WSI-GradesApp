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
    private MorphiaService morphiaService;
    
    public Students(MorphiaService morphiaService) {
        this.morphiaService = morphiaService;
    }

    public List<Student> getStudents() {
        Query<Student> getAllStudents = morphiaService.getDatastore().createQuery(Student.class);
        return getAllStudents.asList();
    }

    public void addStudent(Student studentToAdd) {
        morphiaService.getDatastore().save(studentToAdd);
    }

    public void removeStudent(Student studentToRemove) {
        Query<Student> getStudentToDelete = getQueryFindStudentByIndex(studentToRemove.getIndex());
        morphiaService.getDatastore().delete(getStudentToDelete);
    }

    public Student findStudentByIndex(Integer studentIndex) {
        Query<Student> getSingleStudentByIndex = getQueryFindStudentByIndex(studentIndex);
        return getSingleStudentByIndex.get();
    }

    private Query<Student> getQueryFindStudentByIndex(Integer studentIndex) {
        return morphiaService.getDatastore()
                .createQuery(Student.class).field("index").equal(studentIndex);
    }
}
