package jerseyresources;

import entities.Model;
import entities.Student;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
}
