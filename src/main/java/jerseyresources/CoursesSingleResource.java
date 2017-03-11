package jerseyresources;

import entities.Course;
import entities.Model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by student on 26.02.2017.
 */
@Path("courses/{courseid}")
public class CoursesSingleResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public Response getCourse(@PathParam("courseid") Integer courseId) {
        Course courseFromParam = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        if (courseFromParam != null)
            return Response.status(200).entity(courseFromParam).build();
        else
            return Response.status(404).build();
    }
}
