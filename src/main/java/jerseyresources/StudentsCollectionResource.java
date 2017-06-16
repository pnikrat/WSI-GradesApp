package jerseyresources;

import server.Model;
import entities.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * Created by student on 26.02.2017.
 */
@Path("/students")
public class StudentsCollectionResource {
    @GET
    @Produces({"application/xml", "application/json"})
    public Response getStudents(@Context UriInfo uriInfo, @QueryParam("birthDate") Date birthDateParam) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        List<Student> students;
        if (queryParams.isEmpty())
            students = Model.getInstance().getStudentsContainer().getStudents();
        else if (queryParams.containsKey("firstName") || queryParams.containsKey("lastName"))
            students = Model.getInstance().getStudentsContainer().getStudentsByNames(queryParams);
        else
            students = Model.getInstance().getStudentsContainer().getStudentsByBirthdays(queryParams, birthDateParam);
        GenericEntity<List<Student>> studentsEntity = new GenericEntity<List<Student>>(students) {};
        return Response.status(200).entity(studentsEntity).build();
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
            Model.getInstance().getStudentsContainer().removeStudent(previousStudent.getIndex());
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
            Model.getInstance().getCoursesContainer().removeStudentGrades(studentIndex);
            Model.getInstance().getStudentsContainer().removeStudent(studentIndex);
            URI studentsContainerURI = URI.create("students");
            return Response.status(200).location(studentsContainerURI).build();
    }
}
