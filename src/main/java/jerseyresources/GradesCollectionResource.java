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
        Model.getInstance().getGrades().add(newGrade);
        Course courseWithNewGrade = Model.getInstance().getCourses()
                .stream().filter(x -> x.getCourseId().equals(courseId)).findFirst().orElse(null);
        courseWithNewGrade.getCourseGrades().add(newGrade);
        URI createdURI = URI.create("courses/" + courseId.toString() + "/" + "grades/" + id.toString());
        return Response.created(createdURI).build();
    }
}
