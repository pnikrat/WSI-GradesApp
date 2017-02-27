package jerseyresources;

import entities.Grade;
import entities.Model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
@Path("courses/{courseid}/grades")
public class GradesCollectionResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Grade> getGrades(@PathParam("courseid") Integer courseId) {
        return Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().get().getCourseGrades();
    }
}
