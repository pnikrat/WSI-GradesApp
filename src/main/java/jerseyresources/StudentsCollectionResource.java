package jerseyresources;

import entities.Model;
import entities.Student;

import javax.ws.rs.*;
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
    public List<Student> getStudents() {
        return Model.getInstance().getStudents();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createStudent(Student newStudent) {
        Integer index = newStudent.setIndex();
        Model.getInstance().getStudents().add(newStudent);
        URI createdURI = URI.create("students/" + index.toString());
        return Response.created(createdURI).build();
    }

    @GET @Path("/{studentindex}")
    @Produces({"application/xml", "application/json"})
    public Response getStudent(@PathParam("studentindex") Integer studentIndex) {
        Student studentFromParam = Model.getInstance().getStudents()
                .stream().filter(x -> x.getIndex().equals(studentIndex)).findFirst().orElse(null);
        if (studentFromParam != null)
            return Response.status(200).entity(studentFromParam).build();
        else
            return Response.status(404).build();
    }

    @PUT @Path("/{studentindex}")
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

    @DELETE @Path("/{studentindex}")
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
