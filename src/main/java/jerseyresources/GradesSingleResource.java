package jerseyresources;

import entities.Course;
import entities.Grade;
import entities.Model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by student on 26.02.2017.
 */
@Path("courses/{courseid}/grades/{gradeid}")
public class GradesSingleResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public Response getGrade(@PathParam("courseid") Integer courseId, @PathParam("gradeid") Integer gradeId) {
        Course courseFromParam = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        if (courseFromParam != null) {
            Grade gradeFromParam = courseFromParam.getCourseGrades()
                    .stream().filter(x -> x.getGradeId().equals(gradeId)).findFirst().orElse(null);
            if (gradeFromParam != null)
                return Response.status(200).entity(gradeFromParam).build();
        }
        return Response.status(404).build();
    }
}
