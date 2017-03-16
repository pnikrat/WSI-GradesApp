package jerseyresources;

import entities.Course;
import entities.Model;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
@Path("courses")
public class CoursesCollectionResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Course> getCourses() {
        return Model.getInstance().getCourses();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createCourse(Course newCourse) {
        Integer id = newCourse.setCourseId();
        Model.getInstance().getCourses().add(newCourse);
        URI createdURI = URI.create("courses/" + id.toString());
        return Response.created(createdURI).build();
    }

    @GET @Path("/{courseid}")
    @Produces({"application/xml", "application/json"})
    public Response getCourse(@PathParam("courseid") Integer courseId) {
        Course courseFromParam = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        if (courseFromParam != null)
            return Response.status(200).entity(courseFromParam).build();
        else
            return Response.status(404).build();
    }

    @PUT @Path("/{courseid}")
    @Consumes({"application/xml", "application/json"})
    public Response editCourse(@PathParam("courseid") Integer courseId, Course editedCourse) {
        Course previousCourse = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        if (previousCourse != null) {
            Model.getInstance().getCourses().remove(previousCourse);
            editedCourse.replaceCourseId(courseId);
            Model.getInstance().getCourses().add(editedCourse);
            return Response.status(200).build();
        }
        else
            return Response.status(404).build();
    }

    @DELETE @Path("/{courseid}")
    @Produces({"application/xml", "application/json"})
    public Response deleteCourse(@PathParam("courseid") Integer courseId) {
        Course courseFromParam = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        if (courseFromParam != null) {
            Model.getInstance().getCourses().remove(courseFromParam);
            URI coursesContainerURI = URI.create("courses");
            return Response.status(200).location(coursesContainerURI).build();
        }
        else
            return Response.status(404).build();
    }
}
