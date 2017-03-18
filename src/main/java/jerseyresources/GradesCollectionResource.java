package jerseyresources;

import entities.Course;
import entities.Grade;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
public class GradesCollectionResource {

    private Integer courseId;
    private Course parentCourse;

    public GradesCollectionResource(Course parentCourse) {
        this.parentCourse = parentCourse;
        this.courseId = parentCourse != null ? parentCourse.getCourseId() : null;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public Response getGrades() {
        if (parentCourse != null) {
            List<Grade> grades = parentCourse.getCourseGrades().getGrades();
            if (grades.size() != 0)
                return Response.status(200).entity(parentCourse.getCourseGrades()).build();
            else
                return Response.noContent().build();
        }
        else
            return Response.status(404).build();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createGrade(Grade newGrade) {
        if (parentCourse != null) {
            Integer id = newGrade.setGradeId();
            parentCourse.getCourseGrades().addGrade(newGrade);
            URI createdURI = URI.create("courses/" + courseId.toString() + "/" + "grades/" + id.toString());
            return Response.created(createdURI).build();
        }
        else
            return Response.status(404).build();
    }

    @GET @Path("/{gradeid}")
    @Produces({"application/xml", "application/json"})
    public Response getGrade(@PathParam("gradeid") Integer gradeId) {
        if (parentCourse != null) {
            Grade gradeFromParam = parentCourse.getCourseGrades().findSingleGrade(gradeId);
            if (gradeFromParam != null)
                return Response.status(200).entity(gradeFromParam).build();
        }
        return Response.status(404).build();
    }

    @PUT @Path("/{gradeid}")
    @Consumes({"application/xml", "application/json"})
    public Response editGrade(@PathParam("gradeid") Integer gradeId, Grade editedGrade) {
        if (parentCourse != null) {
            Grade previousGrade = parentCourse.getCourseGrades().findSingleGrade(gradeId);
            if (previousGrade != null) {
                parentCourse.getCourseGrades().removeGrade(previousGrade);
                editedGrade.replaceGradeId(gradeId);
                parentCourse.getCourseGrades().addGrade(editedGrade);
                return Response.status(200).build();
            }
        }
        return Response.status(404).build();
    }

    @DELETE @Path("/{gradeid}")
    @Produces({"application/xml", "application/json"})
    public Response deleteGrade(@PathParam("gradeid") Integer gradeId) {
        if (parentCourse != null) {
            Grade gradeFromParam = parentCourse.getCourseGrades().findSingleGrade(gradeId);
            if (gradeFromParam != null) {
                parentCourse.getCourseGrades().removeGrade(gradeFromParam);
                URI gradesContainerURI = URI.create("courses/" + courseId + "/grades");
                return Response.status(200).location(gradesContainerURI).build();
            }
        }
        return Response.status(404).build();
    }
}
