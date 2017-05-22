package entitiescontainers;

import entities.Course;
import entities.Grade;
import entities.GradeValue;
import entities.Student;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import server.MorphiaService;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pnikrat on 18.03.17.
 */
public class Courses {
    private MorphiaService morphiaService;
    
    public Courses(MorphiaService morphiaService) {
        this.morphiaService = morphiaService;
    }

    public List<Course> getCourses() {
        Query<Course> getAllCourses = morphiaService.getDatastore().createQuery(Course.class);
        return getAllCourses.asList();
    }

    public List<Course> getCoursesByInstructor(String specificInstructorParam) {
        return morphiaService.getDatastore().createQuery(Course.class).field("courseInstructor")
                .equal(specificInstructorParam).asList();
    }

    public void addCourse(Course courseToAdd) {
        morphiaService.getDatastore().save(courseToAdd);
    }

    public void commitCourseWithGradesChanges(Course courseWithGradeChange) {
        morphiaService.getDatastore().save(courseWithGradeChange);
    }

    public void removeCourse(Course courseToRemove) {
        Query<Course> getCourseToDelete =getQueryFindCourseById(courseToRemove.getObjectId());
        morphiaService.getDatastore().delete(getCourseToDelete);
    }

    public Course findCourseById(String courseId) {
        Query<Course> getSingleCourseById = getQueryFindCourseById(createObjectIdFromString(courseId));
        return getSingleCourseById.get();
    }

    public void removeStudentGrades(Student studentForGradeRemoval) {
        List<Course> courses = getCourses();
        for (Course singleCourse : courses) {
            singleCourse.removeStudentGrades(studentForGradeRemoval);
            commitCourseWithGradesChanges(singleCourse);
        }
    }

    public List<Grade> getCourseGrades(Course courseWithGradesToGet) {
        return courseWithGradesToGet.getCourseGrades();
    }

    public List<Grade> getCourseGradesByStudent(Course queriedCourse, Integer studentIndex) {
        List<Grade> courseGrades = getCourseGrades(queriedCourse);
        if (courseGrades == null)
            return null;
        return courseGrades.stream().filter(x -> x.getConcreteStudent().getIndex().equals(studentIndex))
                .collect(Collectors.toList());
    }

    public List<Grade> getAllStudentGrades(MultivaluedMap<String, String> queryParams) {
        Integer studentIndexParam;
        String comparatorParam;
        Double gradeValueParam;
        try {
            studentIndexParam = Integer.valueOf(queryParams.getFirst("studentIndex"));
            comparatorParam = queryParams.getFirst("comparator");
            gradeValueParam = Double.valueOf(queryParams.getFirst("gradeValue"));
        }
        catch (NumberFormatException e) {
            return null;
        }

        List<Grade> studentGradesFromAllCourses = filterStudentGradesFromAllCourses(studentIndexParam);

        List<Grade> studentFilteredGradesFromAllCourses = new ArrayList<>();
        if (studentGradesFromAllCourses.size() > 0) {
            switch(comparatorParam) {
                case "gt":
                    studentFilteredGradesFromAllCourses = studentGradesFromAllCourses.stream()
                            .filter(x -> x.getConcreteGrade().getValue() > gradeValueParam).collect(Collectors.toList());
                    break;
                case "lt":
                    studentFilteredGradesFromAllCourses = studentGradesFromAllCourses.stream()
                            .filter(x -> x.getConcreteGrade().getValue() < gradeValueParam).collect(Collectors.toList());
                    break;
                case "eq":
                    studentFilteredGradesFromAllCourses = studentGradesFromAllCourses.stream()
                            .filter(x -> x.getConcreteGrade().getValue().equals(gradeValueParam)).collect(Collectors.toList());
                    break;
            }
        }
        return studentFilteredGradesFromAllCourses;
    }

    public void setGradesObjectIdField(List<Grade> grades) {
        for (Grade g : grades) {
            g.setObjectId(new ObjectId());
        }
    }

    private Query<Course> getQueryFindCourseById(ObjectId courseObjectId) {
        return morphiaService.getDatastore()
                .createQuery(Course.class).field("_id").equal(courseObjectId);
    }

    private List<Grade> filterStudentGradesFromAllCourses(Integer studentIndexParam) {
        List<Course> allCourses = getCourses();
        List<Grade> studentGradesFromAllCourses = new ArrayList<>();
        for (Course x : allCourses) {
            List<Grade> studentGradesFromSingleCourse = getCourseGradesByStudent(x, studentIndexParam);
            if (studentGradesFromSingleCourse != null && studentGradesFromSingleCourse.size() > 0)
                studentGradesFromAllCourses.addAll(studentGradesFromSingleCourse);
        }
        return studentGradesFromAllCourses;
    }

    private ObjectId createObjectIdFromString(String courseObjectId) {
        return new ObjectId(courseObjectId);
    }
}
