import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import jerseyresources.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import server.Model;
import utilities.CustomHeaders;
import utilities.DateParamConverterProvider;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by student on 26.02.2017.
 */
public class Main {
    public static void main(String[] args) {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig(StudentsCollectionResource.class,
                CoursesCollectionResource.class,
                GradesCollectionResource.class);
        config.register(new DateParamConverterProvider("yyyy-MM-dd"));
        config.register(CustomHeaders.class);
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        Model.getInstance();
    }
}
