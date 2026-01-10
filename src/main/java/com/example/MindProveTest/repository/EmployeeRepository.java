package com.example.MindProveTest.repository;

import com.example.MindProveTest.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

@Repository
public class EmployeeRepository {


    private MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Employee saveOrUpdate(Employee employee) {

        Employee saved = mongoTemplate.save(employee);
        return saved;
    }

//    public Company findById(String id) {
//        return mongoTemplate.findById(id, Company.class);
//    }

    public Employee getEmployeeById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("isActive").is(true));
        return mongoTemplate.findOne(query, Employee.class);
    }

    public Page<Employee> getAllEmployees(Integer pageIndex, Integer itemsPerPage) {


        Query query = new Query();
        query.addCriteria(Criteria.where("isActive").is(true));

        long total = mongoTemplate.count(query, Employee.class);

        Pageable pageable = PageRequest.of(
                pageIndex,
                itemsPerPage,
                Sort.by(Sort.Direction.DESC, "modifiedOn")
        );

        query.with(pageable);

        List<Employee> companies = mongoTemplate.find(query, Employee.class);

        return new PageImpl<>(companies, pageable, total);
    }

}
