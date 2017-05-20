package entitiescontainers;

import entities.Course;
import entities.Grade;
import entities.Student;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import server.MorphiaService;

import java.util.ArrayList;
import java.util.List;

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

    public void setGradesObjectIdField(List<Grade> grades) {
        for (Grade g : grades) {
            g.setObjectId(new ObjectId());
        }
    }

    private Query<Course> getQueryFindCourseById(ObjectId courseObjectId) {
        return morphiaService.getDatastore()
                .createQuery(Course.class).field("_id").equal(courseObjectId);
    }

    private ObjectId createObjectIdFromString(String courseObjectId) {
        return new ObjectId(courseObjectId);
    }
}
