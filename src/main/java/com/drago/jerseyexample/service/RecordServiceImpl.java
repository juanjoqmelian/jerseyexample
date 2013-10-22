package com.drago.jerseyexample.service;

import com.drago.jerseyexample.model.Record;
import com.drago.jerseyexample.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 11/10/13
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
@Service("service")
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository repository;

    @Override
    public String create(Record record){
          return repository.save(record).getId();
    }

    @Override
    public Record update(Record record){
        return repository.save(record);
    }

    @Override
    public Record get(String id){
        return repository.findOne(id);
    }

    @Override
    public List<Record> getAll(){
        return (List<Record>)repository.findAll();
    }

    @Override
    public void delete(String id){
        repository.delete(id);
    }


}
