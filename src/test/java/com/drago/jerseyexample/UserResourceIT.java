package com.drago.jerseyexample;

import com.drago.jerseyexample.payload.UserPayload;
import com.drago.jerseyexample.rules.ServerRule;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 18/10/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
@Ignore
public class UserResourceIT extends DBTestCase {

    @ClassRule
    public ServerRule serverRule;

    private static final RestTemplate REST_TEMPLATE = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    private static final String WEB_API = "webapi";

    private static final String USERS = "users";

    public UserResourceIT(String name){
        super(name);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:jerseyexampletest" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root" );
        // System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(new FileInputStream("userResourceDataSet.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    @Test
    public void shouldCreateUser(){

        URI uri = REST_TEMPLATE.postForLocation(serverRule.baseUri().path(WEB_API).path(USERS).build(),
                                                                new UserPayload.Builder("Juan", "Quintana").build());



        //TODO

    }

    @Test
    public void shouldUpdateUser(){

    }

    @Test
    public void shouldGetAll(){

    }

    @Test
    public void shouldGetUser(){

    }

    @Test
    public void shouldDeleteUser(){

    }

    @Test
    public void shouldReturnNotFoundWhenGettingAnUserThatDoesNotExist(){

    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingAnUserThatDoesNotExist(){

    }

    @Test
    public void shouldReturnNotFoundWhenDeletingAnUserThatDoesNotExist(){

    }

}
