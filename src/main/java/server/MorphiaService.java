package server;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by pnikrat on 26.03.17.
 */
public class MorphiaService {
    private Morphia morphia;
    private Datastore datastore;

    public MorphiaService(){
        MongoClient mongoClient = new MongoClient();

        this.morphia = new Morphia();
        String databaseName = "eprotoDB";
        this.datastore = morphia.createDatastore(mongoClient, databaseName);
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
}
