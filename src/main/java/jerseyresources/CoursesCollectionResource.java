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
}
