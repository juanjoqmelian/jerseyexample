package com.drago.jerseyexample;

import com.drago.jerseyexample.model.Record;
import com.drago.jerseyexample.payload.RecordPayload;
import com.drago.jerseyexample.resource.RecordResource;
import com.drago.jerseyexample.service.RecordService;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 16/10/13
 * Time: 08:18
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class RecordResourceTest {
    

    private static final String TEST_ID = "a02";

    private RecordResource recordResource;

    public RecordService mockService;

    public UriInfo mockUriInfo;


    @Before
    public void setUp(){
        recordResource = new RecordResource();
        mockService = mock(RecordService.class);
        mockUriInfo = mock(UriInfo.class);
        recordResource.setService(mockService);
        recordResource.setUriInfo(mockUriInfo);
    }


    @Test
    public void shouldGetAll() throws Exception {

        List<Record> records = new ArrayList<Record>();

        when(mockService.getAll()).thenReturn(records);

        Response response = recordResource.getAll();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));

        List<RecordPayload> list = (List<RecordPayload>) response.getEntity();

        assertThat(list.size(), is(0));

    }


    @Test
    public void shouldCreateRecord() throws Exception {

        RecordPayload recordPayload = RecordPayload.instanceOf(7);

        when(mockService.create(argThat(isRecord(null, 7)))).thenReturn(TEST_ID);
        when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(UriBuilder.fromPath("http://localhost:8080/services/webapi/myresource"));

        Response response = recordResource.createRecord(recordPayload);

        assertThat(response.getLocation().toString(), is("http://localhost:8080/services/webapi/myresource/a02"));
        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    public void shouldUpdateRecord() throws Exception{
        RecordPayload recordPayload = RecordPayload.instanceOf(10);

        when(mockService.get(argThat(isString(TEST_ID)))).thenReturn(Record.instanceOf(10));
        when(mockService.update(argThat(isRecord(TEST_ID, 10)))).thenReturn(Record.instanceOf(10));

        Response response = recordResource.updateRecord(TEST_ID, recordPayload);

        assertThat(response.getStatus(), is(HttpStatus.NOT_MODIFIED.value()));
    }


    @Test
    public void shouldGetRecord() throws Exception {

        Record record = Record.instanceOf(10);

        when(mockService.get(argThat(isString(TEST_ID)))).thenReturn(record);

        Response response = recordResource.getRecord(TEST_ID);

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));

        RecordPayload recordPayload = (RecordPayload) response.getEntity();

        assertThat(recordPayload.getNumber(), is(10));

    }


    @Test
    public void shouldDeleteRecord() throws Exception {

        when(mockService.get(argThat(isString(TEST_ID)))).thenReturn(Record.instanceOf(10));

        Response response = recordResource.deleteRecord(TEST_ID);

        verify(mockService, times(1)).delete(argThat(isString(TEST_ID)));

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));

    }


    @Test
    public void shouldReturnNotFoundWhenUpdatingARecordThatDoesNotExist(){

        when(mockService.get(argThat(isString(TEST_ID)))).thenReturn(null);

        Response response = recordResource.updateRecord(TEST_ID, RecordPayload.instanceOf(11));

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));

    }

    @Test
    public void shouldReturnNotFoundWhenGettingARecordThatDoesNotExist(){

        when(mockService.get(argThat(isString(TEST_ID)))).thenReturn(null);

        Response response = recordResource.getRecord(TEST_ID);

        RecordPayload recordPayload = (RecordPayload) response.getEntity();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));

        assertThat(recordPayload, is(nullValue()));

    }

    @Test
    public void shouldReturnNotFoundWhenDeletingARecordThatDoesNotExist(){

        when(mockService.get(argThat(isString(TEST_ID)))).thenReturn(null);

        Response response = recordResource.deleteRecord(TEST_ID);

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));

    }


    private TypeSafeMatcher<Record> isRecord(final String id, final int number) {
        return new TypeSafeMatcher<Record>() {
            @Override
            public boolean matchesSafely(Record record) {
                return record.getNumber() == number && record.getId() == id;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void describeTo(Description description) {
                //
            }
        };
    }

    private TypeSafeMatcher<String> isString(final String testId) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return s.equals(testId);
            }

            @Override
            public void describeTo(Description description) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

}
