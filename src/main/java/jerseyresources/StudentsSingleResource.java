package jerseyresources;

import entities.Model;
import entities.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.transform.Result;
import java.net.URI;

/**
 * Created by student on 26.02.2017.
 */
@Path("students/{studentindex}")
public class StudentsSingleResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public Response getStudent(@PathParam("studentindex") Integer studentIndex) {
        Student studentFromParam = Model.getInstance().getStudents()
                .stream().filter(x -> x.getIndex().equals(studentIndex)).findFirst().orElse(null);
        if (studentFromParam != null)
            return Response.status(200).entity(studentFromParam).build();
        else
            return Response.status(404).build();
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response editStudent(@PathParam("studentindex") Integer studentIndex, Student editedStudent) {
        Student previousStudent = Model.getInstance().getStudents()
                .stream().filter(x -> x.getIndex().equals(studentIndex)).findFirst().orElse(null);
        if (previousStudent != null) {
            Model.getInstance().getStudents().remove(previousStudent);
            editedStudent.replaceIndex(studentIndex);
            Model.getInstance().getStudents().add(editedStudent);
            return Response.status(200).build();
        }
        else
            return Response.status(404).build();
    }

    @DELETE
    @Produces({"application/xml", "application/json"})
    public Response deleteStudent(@PathParam("studentindex") Integer studentIndex) {
        Student studentFromParam = Model.getInstance().getStudents()
                .stream().filter(x -> x.getIndex().equals(studentIndex)).findFirst().orElse(null);
        if (studentFromParam != null) {
            Model.getInstance().getStudents().remove(studentFromParam);
            URI studentsContainerURI = URI.create("students");
            return Response.status(200).location(studentsContainerURI).build();
        }
        else
            return Response.status(404).build();
    }
}
