package jerseyresources;

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
        if (Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().isPresent()) {
            return Response.ok().entity(Model.getInstance().getCourses()
                    .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().get()).build();
        }
        else {
            return Response.status(404).build();
        }
    }
}
