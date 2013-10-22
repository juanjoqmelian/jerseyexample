package com.drago.jerseyexample.resource;

import com.drago.jerseyexample.assembler.RecordAssembler;
import com.drago.jerseyexample.model.Record;
import com.drago.jerseyexample.payload.RecordPayload;
import com.drago.jerseyexample.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/myresource")
@Component
public class RecordResource {

    private RecordService service;

    private UriInfo uriInfo;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAll() {
        List<Record> records = service.getAll();
        List<RecordPayload> recordPayloads = new ArrayList<RecordPayload>();
        for(Record record : records){
           recordPayloads.add(record.toPayLoad());
        }
        GenericEntity<List<RecordPayload>> entity = new GenericEntity<List<RecordPayload>>(recordPayloads){};
        return Response.ok(entity).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createRecord(RecordPayload recordPayload){
        String id = service.create(RecordAssembler.toEntity(recordPayload));
        return Response.created(uriInfo.getAbsolutePathBuilder().path("{id}").build(id)).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRecord(@PathParam("id") String id){
        Record record = service.get(id);
        if(record == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(record.toPayLoad()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateRecord(@PathParam("id") String id, RecordPayload recordPayload){
        Record record = service.get(id);
        if(record == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        record.setNumber(recordPayload.getNumber());
        service.update(record);
        return Response.notModified().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteRecord(@PathParam("id") String id){
        if(service.get(id) == null){
             return Response.status(Response.Status.NOT_FOUND).build();
        }
        service.delete(id);
        return Response.ok().build();
    }

    @Autowired
    public void setService(RecordService service){
        this.service = service;
    }

    @Context
    public void setUriInfo(UriInfo uriInfo){
        this.uriInfo = uriInfo;
    }
}
