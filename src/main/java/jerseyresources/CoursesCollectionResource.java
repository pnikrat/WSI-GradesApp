package jerseyresources;

import com.sun.javafx.sg.prism.NGShape;
import entities.Course;
import entities.Grade;
import server.Model;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
@Path("courses")
public class CoursesCollectionResource {
    @GET
    @Produces({"application/xml", "application/json"})
    public Response getCourses(@QueryParam("courseInstructor") String specificInstructorParam) {
        List<Course> courses;
        if (specificInstructorParam == null)
            courses = Model.getInstance().getCoursesContainer().getCourses();
        else
            courses = Model.getInstance().getCoursesContainer().getCoursesByInstructor(specificInstructorParam);
        if (courses.size() != 0) {
            GenericEntity<List<Course>> coursesEntity = new GenericEntity<List<Course>>(courses) {};
            return Response.status(200).entity(coursesEntity).build();
        }
        else
            return Response.noContent().build();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createCourse(Course newCourse) {
        List<Grade> newCourseGrades = new ArrayList<>();
        newCourse.setCourseGrades(newCourseGrades);
        Model.getInstance().getCoursesContainer().addCourse(newCourse);
        String courseId = newCourse.getObjectId().toHexString();
        URI createdURI = URI.create("courses/" + courseId);
        return Response.created(createdURI).build();
    }

    @GET @Path("/{courseid}")
    @Produces({"application/xml", "application/json"})
    public Response getCourse(@PathParam("courseid") String courseId) {
        Course courseFromParam = Model.getInstance().getCoursesContainer().findCourseById(courseId);
        if (courseFromParam != null)
            return Response.status(200).entity(courseFromParam).build();
        else
            return Response.status(404).build();
    }

    @PUT @Path("/{courseid}")
    @Consumes({"application/xml", "application/json"})
    public Response editCourse(@PathParam("courseid") String courseId, Course editedCourse) {
        Course previousCourse = Model.getInstance().getCoursesContainer().findCourseById(courseId);
        if (previousCourse != null) {
            Model.getInstance().getCoursesContainer().removeCourse(previousCourse);
            editedCourse.replaceCourseId(courseId);
            Model.getInstance().getCoursesContainer().addCourse(editedCourse);
            return Response.status(200).build();
        }
        else
            return Response.status(404).build();
    }

    @DELETE @Path("/{courseid}")
    @Produces({"application/xml", "application/json"})
    public Response deleteCourse(@PathParam("courseid") String courseId) {
        Course courseFromParam = Model.getInstance().getCoursesContainer().findCourseById(courseId);
        if (courseFromParam != null) {
            Model.getInstance().getCoursesContainer().removeCourse(courseFromParam);
            URI coursesContainerURI = URI.create("courses");
            return Response.status(200).location(coursesContainerURI).build();
        }
        else
            return Response.status(404).build();
    }

    @Path("/{courseid}/grades")
    public GradesCollectionResource getCourseGrades(@PathParam("courseid") String courseId) {
        Course courseFromParam = Model.getInstance().getCoursesContainer().findCourseById(courseId);
        return new GradesCollectionResource(courseFromParam);
    }
}
