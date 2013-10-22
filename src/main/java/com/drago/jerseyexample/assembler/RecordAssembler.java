package com.drago.jerseyexample.assembler;

import com.drago.jerseyexample.model.Record;
import com.drago.jerseyexample.payload.RecordPayload;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 15/10/13
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
public class RecordAssembler {


    public static Record toEntity(RecordPayload recordPayload){
        return Record.instanceOf(recordPayload.getNumber());
    }

    public static Record toEntityWithId(String id, RecordPayload recordPayload){
        Record record = Record.instanceOf(recordPayload.getNumber());
        record.setId(id);
        return record;
    }
}
