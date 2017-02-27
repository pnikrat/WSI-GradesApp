package jerseyresources;

import entities.Course;
import entities.Model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
