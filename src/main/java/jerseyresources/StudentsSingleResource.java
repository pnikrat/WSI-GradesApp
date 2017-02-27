package jerseyresources;

import entities.Model;
import entities.Student;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by student on 26.02.2017.
 */
@Path("students/{studentindex}")
public class StudentsSingleResource {

    @GET
    @Produces({"application/xml", "application/json"})
    public Student getStudent(@PathParam("studentindex") Integer studentIndex) {
        return Model.getInstance().getStudents()
                .stream().filter(x -> x.getIndex().equals(studentIndex)).findFirst().get();
    }
}
