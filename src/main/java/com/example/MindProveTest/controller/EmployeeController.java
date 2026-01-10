package com.example.MindProveTest.controller;

import com.example.MindProveTest.model.Employee;
import com.example.MindProveTest.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/employees")
public class EmployeeController {

    private EmployeeService employeeService;
    private ObjectMapper objectMapper;


    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/employees")
    public ResponseEntity<ObjectNode> createEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee);

        ObjectNode response = objectMapper.createObjectNode();
        response.put("message", "Employee created successfully");
        response.put("id", saved.getId());
        response.put("employee name", saved.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update Company
    @PutMapping("/employees/{id}")
    public ResponseEntity<ObjectNode> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        Employee updated = employeeService.updateEmployee(id, employee);
        ObjectNode node = objectMapper.valueToTree(updated);
        node.put("message", "Company updated successfully");

        return ResponseEntity.ok(node);
    }

    //Get Company by ID
    @GetMapping("/employee/{id}")
    public ResponseEntity<ObjectNode> getEmployeeById(@PathVariable String id) {

        Employee emp = employeeService.getEmployeeById(id);

        ObjectNode node = objectMapper.valueToTree(emp);
        node.put("status", "SUCCESS");

        return ResponseEntity.ok(node);
    }

    @GetMapping("/employees")
    public ResponseEntity<ObjectNode> getALLEmployees(@RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer itemsPerPage) {

        Page<Employee> page = employeeService.getAllEmployees(pageIndex, itemsPerPage);

        ArrayNode empArray = objectMapper.createArrayNode();
        page.getContent().forEach(emp ->
                empArray.add(objectMapper.valueToTree(emp))
        );

        ObjectNode response = objectMapper.createObjectNode();
        response.put("pageIndex", page.getNumber());
        response.put("itemsPerPage", page.getSize());
        response.put("totalRecords", page.getTotalElements());
        response.set("companies", empArray);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ObjectNode> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        ObjectNode response = objectMapper.createObjectNode();
        response.put("message", "Company deleted successfully");
        return ResponseEntity.ok(response);
    }


}
