package jerseyresources;

import server.Model;
import entities.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
@Path("/students")
public class StudentsCollectionResource {
    @GET
    @Produces({"application/xml", "application/json"})
    public Response getStudents() {
        List<Student> students = Model.getInstance().getStudentsContainer().getStudents();
        if (students.size() != 0) {
            GenericEntity<List<Student>> studentsEntity = new GenericEntity<List<Student>>(students) {};
            return Response.status(200).entity(studentsEntity).build();
        }
        else
            return Response.noContent().build();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createStudent(Student newStudent) {
        Integer index = newStudent.setIndex();
        Model.getInstance().getStudentsContainer().addStudent(newStudent);
        URI createdURI = URI.create("students/" + index.toString());
        return Response.created(createdURI).build();
    }

    @GET @Path("/{studentindex}")
    @Produces({"application/xml", "application/json"})
    public Response getStudent(@PathParam("studentindex") Integer studentIndex) {
        Student studentFromParam = Model.getInstance().getStudentsContainer().findStudentByIndex(studentIndex);
        if (studentFromParam != null)
            return Response.status(200).entity(studentFromParam).build();
        else
            return Response.status(404).build();
    }

    @PUT @Path("/{studentindex}")
    @Consumes({"application/xml", "application/json"})
    public Response editStudent(@PathParam("studentindex") Integer studentIndex, Student editedStudent) {
        Student previousStudent = Model.getInstance().getStudentsContainer().findStudentByIndex(studentIndex);
        if (previousStudent != null) {
            Model.getInstance().getStudentsContainer().removeStudent(previousStudent);
            editedStudent.replaceIndex(studentIndex);
            editedStudent.replaceCourseId(previousStudent.getObjectId().toHexString());
            Model.getInstance().getStudentsContainer().addStudent(editedStudent);
            return Response.status(200).build();
        }
        else
            return Response.status(404).build();
    }

    @DELETE @Path("/{studentindex}")
    @Produces({"application/xml", "application/json"})
    public Response deleteStudent(@PathParam("studentindex") Integer studentIndex) {
        Student studentFromParam = Model.getInstance().getStudentsContainer().findStudentByIndex(studentIndex);
        if (studentFromParam != null) {
            Model.getInstance().getCoursesContainer().removeStudentGrades(studentFromParam);
            Model.getInstance().getStudentsContainer().removeStudent(studentFromParam);
            URI studentsContainerURI = URI.create("students");
            return Response.status(200).location(studentsContainerURI).build();
        }
        else
            return Response.status(404).build();
    }
}
