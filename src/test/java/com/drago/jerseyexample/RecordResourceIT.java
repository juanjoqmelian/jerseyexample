package com.drago.jerseyexample;

import com.drago.jerseyexample.model.Record;
import com.drago.jerseyexample.payload.RecordPayload;
import com.drago.jerseyexample.rules.MongoRule;
import com.drago.jerseyexample.rules.ServerRule;
import com.drago.jerseyexample.util.HttpUtil;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecordResourceIT {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    private static final String WEB_API = "webapi";
    private static final String MY_RESOURCE = "myresource";

    @ClassRule
    public static ServerRule serverRule = new ServerRule();

    @ClassRule
    public static MongoRule mongoServices = new MongoRule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldGetAll() throws Exception {

        HttpEntity<?> request = HttpUtil.setAcceptHeader(MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<List> response = REST_TEMPLATE.exchange(serverRule.baseUri()
                .path(WEB_API)
                .path(MY_RESOURCE).build(),
                HttpMethod.GET, request, List.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        List<RecordPayload> list = response.getBody();

        assertThat(list.size(), is(0));
    }


    @Test
    public void shouldCreateRecord() throws Exception {

        URI uri = REST_TEMPLATE.postForLocation(serverRule.baseUri()
                .path(WEB_API)
                .path(MY_RESOURCE).build(), Record.instanceOf(7));

        Record record = MongoRule.MONGO_TEMPLATE.findOne(Query.query(Criteria.where("number").is(7)), Record.class);

        assertThat(uri, is(serverRule.baseUri().path(WEB_API).path(MY_RESOURCE).path(record.getId()).build()));

    }

    @Test
    public void shouldUpdateRecord() throws Exception {

        MongoRule.MONGO_TEMPLATE.save(Record.instanceOf(11));

        Record record = MongoRule.MONGO_TEMPLATE.findOne(Query.query(Criteria.where("number").is(11)), Record.class);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> request = HttpUtil.setParameter("recordPayload", RecordPayload.instanceOf(12), headers);

        ResponseEntity response = REST_TEMPLATE.exchange(serverRule.baseUri().path(WEB_API).path(MY_RESOURCE)
               .path(record.getId()).build(), HttpMethod.PUT, request, Response.class);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_MODIFIED));

        Thread.sleep(1000); //TODO - fix flaky test -> Use another better way

        Record updatedRecord = MongoRule.MONGO_TEMPLATE.findOne(Query.query(Criteria.where("number").is(12)), Record.class);

        assertThat(updatedRecord, is(notNullValue()));
    }


    @Test
    public void shouldGetRecord() throws Exception {


        MongoRule.MONGO_TEMPLATE.save(Record.instanceOf(10));

        Record record = MongoRule.MONGO_TEMPLATE.findOne(Query.query(Criteria.where("number").is(10)), Record.class);

        HttpEntity<?> request = HttpUtil.setAcceptHeader(MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<RecordPayload> response = REST_TEMPLATE.exchange(serverRule.baseUri().path(WEB_API)
                .path(MY_RESOURCE)
                .path(record.getId()).build(),
                HttpMethod.GET, request, RecordPayload.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        RecordPayload recordPayload = response.getBody();

        assertThat(recordPayload, notNullValue());

    }


    @Test
    public void shouldDeleteRecord() throws Exception {

        MongoRule.MONGO_TEMPLATE.save(Record.instanceOf(11));

        Record record = MongoRule.MONGO_TEMPLATE.findOne(Query.query(Criteria.where("number").is(11)), Record.class);

        REST_TEMPLATE.exchange(serverRule.baseUri().path(WEB_API).path(MY_RESOURCE).path(record.getId()).build(), HttpMethod.DELETE, null, Response.class );

        record = MongoRule.MONGO_TEMPLATE.findOne(Query.query(Criteria.where("number").is(11)), Record.class);

        assertThat(record, is(nullValue()));

    }


    @Test
    public void shouldReturnNotFoundWhenUpdatingARecordThatDoesNotExist(){

        HttpEntity<?> request = HttpUtil.setAcceptHeader(MediaType.APPLICATION_JSON_VALUE);

        expectedException.expect(HttpClientErrorException.class);

        REST_TEMPLATE.exchange(serverRule.baseUri().path(WEB_API).path(MY_RESOURCE).path("a00000000").build(),
                                    HttpMethod.PUT, request, ResponseEntity.class);

    }


    @Test
    public void shouldReturnNotFoundGettingARecordThatDoesNotExist(){

        HttpEntity<?> request = HttpUtil.setAcceptHeader(MediaType.APPLICATION_JSON_VALUE);

        expectedException.expect(HttpClientErrorException.class);

        REST_TEMPLATE.exchange(serverRule.baseUri().path(WEB_API).path(MY_RESOURCE).path("a000000000").build(),
                                                                HttpMethod.GET, request, RecordPayload.class);
    }

    @Test
    public void shouldReturnNotFoundDeletingARecordThatDoesNotExist(){

        expectedException.expect(HttpClientErrorException.class);

        REST_TEMPLATE.exchange(serverRule.baseUri().path(WEB_API).path(MY_RESOURCE).build(), HttpMethod.DELETE, null, RecordPayload.class);

    }

}
