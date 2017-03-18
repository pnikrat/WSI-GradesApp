package jerseyresources;

import entities.Course;
import entities.Grade;
import entities.Model;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
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

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createGrade(@PathParam("courseid") Integer courseId, Grade newGrade) {
        //have to check if student exists, if course exists, etc
        Integer id = newGrade.setGradeId();
        Course courseWithNewGrade = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        courseWithNewGrade.getCourseGrades().addGrade(newGrade);
        URI createdURI = URI.create("courses/" + courseId.toString() + "/" + "grades/" + id.toString());
        return Response.created(createdURI).build();
    }

    @GET @Path("/{gradeid}")
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

    @PUT @Path("/{gradeid}")
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

    @DELETE @Path("/{gradeid}")
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
