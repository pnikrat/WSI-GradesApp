package jerseyresources;

import entities.Course;
import entities.Grade;
import org.bson.types.ObjectId;
import server.Model;
import entities.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
public class GradesCollectionResource {

    private String courseId;
    private Course parentCourse;

    public GradesCollectionResource(Course parentCourse) {
        this.parentCourse = parentCourse;
        this.courseId = parentCourse != null ? parentCourse.getObjectId().toHexString() : null;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public Response getGrades(@QueryParam("studentIndex") Integer studentIndexParam) {
        if (parentCourse != null) {
            List<Grade> grades;
            if (studentIndexParam == null)
                grades = Model.getInstance().getCoursesContainer().getCourseGrades(parentCourse);
            else
                grades = Model.getInstance().getCoursesContainer().getCourseGradesByStudent(parentCourse, studentIndexParam);

            if (grades == null)
                grades = new ArrayList<>();

            GenericEntity<List<Grade>> gradesEntity = new GenericEntity<List<Grade>>(grades) {};
            return Response.status(200).entity(gradesEntity).build();
        }
        else
            return Response.status(404).build();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createGrade(Grade newGrade) {
        if (parentCourse != null) {
//            Integer studentsIndex = newGrade.getConcreteStudentIndex().getIndex();
//            Student studentForGrading = Model.getInstance().getStudentsContainer().findStudentByIndex(studentsIndex);
//            if (studentForGrading == null)
//                return Response.status(404).build();
//            newGrade.setConcreteStudentIndex(studentForGrading);
            newGrade.setObjectId(new ObjectId());
            parentCourse.addGrade(newGrade);
            Model.getInstance().getCoursesContainer().commitCourseWithGradesChanges(parentCourse);
            String gradeId = newGrade.getObjectId().toHexString();
            URI createdURI = URI.create("courses/" + courseId + "/" + "grades/" + gradeId);
            return Response.created(createdURI).build();
        }
        else
            return Response.status(404).build();
    }

    @GET @Path("/{gradeid}")
    @Produces({"application/xml", "application/json"})
    public Response getGrade(@PathParam("gradeid") String gradeId) {
        if (parentCourse != null) {
            Grade gradeFromParam = parentCourse.findSingleGrade(gradeId);
            if (gradeFromParam != null)
                return Response.status(200).entity(gradeFromParam).build();
        }
        return Response.status(404).build();
    }

    @PUT @Path("/{gradeid}")
    @Consumes({"application/xml", "application/json"})
    public Response editGrade(@PathParam("gradeid") String gradeId, Grade editedGrade) {
        if (parentCourse != null) {
            Grade previousGrade = parentCourse.findSingleGrade(gradeId);
            if (previousGrade != null) {
                Integer studentIndexForGrading = editedGrade.getConcreteStudentIndex();
                parentCourse.removeGrade(previousGrade);
                editedGrade.replaceGradeId(gradeId);
                if (studentIndexForGrading != null)
                    editedGrade.setConcreteStudentIndex(studentIndexForGrading);
                parentCourse.addGrade(editedGrade);
                Model.getInstance().getCoursesContainer().commitCourseWithGradesChanges(parentCourse);
                return Response.status(200).build();
            }
        }
        return Response.status(404).build();
    }

    @DELETE @Path("/{gradeid}")
    @Produces({"application/xml", "application/json"})
    public Response deleteGrade(@PathParam("gradeid") String gradeId) {
        if (parentCourse != null) {
            Grade gradeFromParam = parentCourse.findSingleGrade(gradeId);
            if (gradeFromParam != null) {
                parentCourse.removeGrade(gradeFromParam);
                Model.getInstance().getCoursesContainer().commitCourseWithGradesChanges(parentCourse);
                URI gradesContainerURI = URI.create("courses/" + courseId + "/grades");
                return Response.status(200).location(gradesContainerURI).build();
            }
        }
        return Response.status(404).build();
    }
}
