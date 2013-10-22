package com.drago.jerseyexample.model;

import com.drago.jerseyexample.payload.RecordPayload;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 11/10/13
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
@Document
public class Record {

    @Id
    private String id;

    private Integer number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public static Record instanceOf(Integer number) {
        Record record = new Record();
        record.setNumber(number);
        return record;
    }

    public RecordPayload toPayLoad(){
        return RecordPayload.instanceOf(number);
    }

}
