package jerseyresources;

import entities.Course;
import entities.Grade;
import entities.Model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
@Path("courses/{courseid}/grades")
public class GradesCollectionResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public Response getGrades(@PathParam("courseid") Integer courseId) {
        Course courseFromParam = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        if (courseFromParam != null)
            return Response.status(200).entity(courseFromParam.getCourseGrades()).build();
        else
            return Response.status(404).build();
    }
}
