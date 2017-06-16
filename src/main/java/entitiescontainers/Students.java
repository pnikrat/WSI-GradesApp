package entitiescontainers;

import entities.Student;
import org.mongodb.morphia.query.Query;
import server.MorphiaService;
import utilities.DateUtilities;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
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

    public List<Student> getStudentsByNames(MultivaluedMap<String, String> queryParams) {
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

    public List<Student> getStudentsByBirthdays(MultivaluedMap<String, String> queryParams, Date birthDateParam) {
        String comparator = queryParams.getFirst("comparator");
        List<Student> specificStudents = new ArrayList<>();
        if (birthDateParam == null || comparator == null)
            return specificStudents;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDateParam);
        switch (comparator) {
            case "eq":
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                Date dayBeginning = calendar.getTime();
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                Date dayEnding = calendar.getTime();
                Query<Student> query = morphiaService.getDatastore().find(Student.class);
                query.and(query.criteria("birthDate").lessThan(dayEnding),
                          query.criteria("birthDate").greaterThan(dayBeginning));
                specificStudents = query.asList();
                break;
            case "lt":
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                Date upToDate = calendar.getTime();
                specificStudents = morphiaService.getDatastore().createQuery(Student.class).field("birthDate")
                        .lessThan(upToDate).asList();
                break;
            case "gt":
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                Date fromDate = calendar.getTime();
                specificStudents = morphiaService.getDatastore().createQuery(Student.class).field("birthDate")
                        .greaterThan(fromDate).asList();
                break;
        }
        return specificStudents;
    }

    public void addStudent(Student studentToAdd) {
        morphiaService.getDatastore().save(studentToAdd);
    }

    public void removeStudent(Integer indexOfStudentToRemove) {
        Query<Student> getStudentToDelete = getQueryFindStudentByIndex(indexOfStudentToRemove);
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
