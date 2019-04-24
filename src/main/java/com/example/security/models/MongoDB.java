package com.example.security.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;
import org.mongodb.morphia.logging.slf4j.SLF4JLoggerImplFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

/**
 * @author <a href="mailto:nahuel@lcc.uma.es"> Nahuel </a>
 */
public class MongoDB {

    /**
     * The config.
     */
    private static PropertiesConfiguration config = new PropertiesConfiguration();
    private static final Logger LOG = LogManager.getLogger(MongoDB.class);

    static {
        try {
            // set config
            config = new PropertiesConfiguration("config.properties");
        } catch (ConfigurationException e) {
            LOG.error("Error ConfigurationException: " + e.getMessage());
            LOG.error(e);
        }
    }

    private static final String SERVER_ADDRESS = config.getString("database.host");
    public static final String DB = config.getString("database.name", "test");
    private final Datastore datastore;
    private static final MongoDB INSTANCE = new MongoDB();

    private MongoDB() {
        MongoClientOptions mongoOptions =
                MongoClientOptions.builder().socketTimeout(60000).connectTimeout(1200000).maxWaitTime(60000)
                        .sslEnabled(true).sslInvalidHostNameAllowed(true).build();
        MongoClient mongoClient;
        List<MongoCredential> mongoCredentials = new ArrayList<>();
        String user = config.getString("database.user", "testAdmin");
        String pass = config.getString("database.pass", "asd.123.");

        mongoCredentials.add(MongoCredential.createCredential(user, DB, pass.toCharArray()));
        mongoClient =
                new MongoClient(new ServerAddress(SERVER_ADDRESS, 27017), mongoCredentials, mongoOptions);
        mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);

        MorphiaLoggerFactory.registerLogger(SLF4JLoggerImplFactory.class);
        Morphia m = new Morphia();
        m.getMapper().getOptions().setStoreEmpties(false);
        m.mapPackage("es.uma.ogmios.mongo.entity");
        datastore = m.createDatastore(mongoClient, DB);
        datastore.ensureIndexes();
        LOG.info("Conexion a la base de datos '" + DB + "' inicializada.");
    }

    /**
     * @return
     */
    public static MongoDB instance() {
        return INSTANCE;
    }

    // singleton necesario para evitar la carga de crear una nueva conexion
    public Datastore getDatabase() {
        return datastore;
    }
}
