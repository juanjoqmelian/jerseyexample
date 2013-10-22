package com.drago.jerseyexample.repository;


import com.drago.jerseyexample.model.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 12/10/13
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface RecordRepository extends CrudRepository<Record, String> {

}
