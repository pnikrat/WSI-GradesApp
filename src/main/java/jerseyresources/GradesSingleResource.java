package jerseyresources;

import entities.Grade;
import entities.Model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by student on 26.02.2017.
 */
@Path("courses/{courseid}/grades/{gradeid}")
public class GradesSingleResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public Grade getGrade(@PathParam("courseid") Integer courseId, @PathParam("gradeid") Integer gradeId) {
        return Model.getInstance().getCourses().stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().get()
                .getCourseGrades().stream().filter(x -> x.getGradeId().equals(gradeId)).findFirst().get();
    }
}
