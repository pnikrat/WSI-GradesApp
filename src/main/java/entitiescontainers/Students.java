package entitiescontainers;

import entities.Student;
import org.mongodb.morphia.query.Query;
import server.MorphiaService;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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

    public List<Student> getSpecificStudents(MultivaluedMap<String, String> queryParams) {
        String firstNameParam = queryParams.getFirst("firstName");
        String lastNameParam = queryParams.getFirst("lastName");
        List<Student> specificStudents = new ArrayList<>();
        if (firstNameParam == null && lastNameParam == null)
            return specificStudents;
        else {
            if (firstNameParam == null)
                specificStudents = morphiaService.getDatastore().createQuery(Student.class).field("lastName")
                        .equal(lastNameParam).asList();
            else if (lastNameParam == null)
                specificStudents = morphiaService.getDatastore().createQuery(Student.class).field("firstName")
                        .equal(firstNameParam).asList();
            else
                specificStudents = morphiaService.getDatastore().createQuery(Student.class).field("firstName")
                        .equal(firstNameParam).field("lastName").equal(lastNameParam).asList();
        }
        return specificStudents;
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
