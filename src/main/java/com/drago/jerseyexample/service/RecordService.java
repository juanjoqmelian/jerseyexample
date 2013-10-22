package com.drago.jerseyexample.service;

import com.drago.jerseyexample.model.Record;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 14/10/13
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
public interface RecordService {
    String create(Record record);

    Record update(Record record);

    Record get(String id);

    List<Record> getAll();

    void delete(String id);
}
