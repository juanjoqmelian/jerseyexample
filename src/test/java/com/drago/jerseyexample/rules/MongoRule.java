package com.drago.jerseyexample.rules;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.junit.rules.ExternalResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 13/10/13
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public class MongoRule extends ExternalResource {

    public static MongoTemplate MONGO_TEMPLATE;

    private static final String HOST = "localhost";
    private static final String DATABASE_NAME = "yourdb";

    static{
        try {
            MONGO_TEMPLATE =  new MongoTemplate(new MongoClient(HOST, 27017), DATABASE_NAME);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void before() throws Throwable {
        MONGO_TEMPLATE.dropCollection("record");
    }

    @Override
    protected void after() {
        super.after();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
