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
@Path("students")
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
}
