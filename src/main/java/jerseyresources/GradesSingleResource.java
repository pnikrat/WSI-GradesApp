package jerseyresources;

import entities.Course;
import entities.Grade;
import entities.Model;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

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
            Grade gradeFromParam = courseFromParam.getCourseGrades().getSingleGrade(gradeId);
            if (gradeFromParam != null)
                return Response.status(200).entity(gradeFromParam).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response editGrade(@PathParam("courseid") Integer courseId, @PathParam("gradeid") Integer gradeId,
                              Grade editedGrade) {
        Course courseFromParam = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        if (courseFromParam != null) {
            Grade previousGrade = courseFromParam.getCourseGrades().getSingleGrade(gradeId);
            if (previousGrade != null) {
                courseFromParam.getCourseGrades().removeGrade(previousGrade);
                editedGrade.replaceGradeId(gradeId);
                courseFromParam.getCourseGrades().addGrade(editedGrade);
                return Response.status(200).build();
            }
        }
        return Response.status(404).build();
    }

    @DELETE
    @Produces({"application/xml", "application/json"})
    public Response deleteGrade(@PathParam("courseid") Integer courseId, @PathParam("gradeid") Integer gradeId) {
        Course courseFromParam = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        if (courseFromParam != null) {
            Grade gradeFromParam = courseFromParam.getCourseGrades().getSingleGrade(gradeId);
            if (gradeFromParam != null) {
                courseFromParam.getCourseGrades().removeGrade(gradeFromParam);
                URI gradesContainerURI = URI.create("courses/" + courseId + "/grades");
                return Response.status(200).location(gradesContainerURI).build();
            }
        }
        return Response.status(404).build();
    }
}
