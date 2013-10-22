package com.drago.jerseyexample.payload;

import com.drago.jerseyexample.model.Record;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 15/10/13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class RecordPayload {

    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public static RecordPayload instanceOf(Integer number) {
        RecordPayload record = new RecordPayload();
        record.setNumber(number);
        return record;
    }



}

