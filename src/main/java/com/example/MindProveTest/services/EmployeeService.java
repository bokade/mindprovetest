package com.example.MindProveTest.services;

import com.example.MindProveTest.model.Employee;
import com.example.MindProveTest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Service
@Transactional
public class EmployeeService {

    public EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {

        Employee emp = new Employee();
        emp.setName(employee.getName());
        emp.setDepartment(employee.getDepartment());
        emp.setEmail(employee.getEmail());
        emp.setSalary(employee.getSalary());
        emp.setCreatedOn(Instant.now());
        emp.setModifiedOn(Instant.now());
        emp.setIsActive(true);

         return employeeRepository.saveOrUpdate(emp);

    }

    public Employee updateEmployee(String id, Employee employee) {

    Employee emp = employeeRepository.getEmployeeById(id);
    if(emp == null) {
        throw new RuntimeException("Employee not found with id: " + id);
    }

    if(!StringUtils.isEmpty(employee.getName())){
    emp.setName(employee.getName());
    }

        if(employee.getSalary() != null){
            emp.setSalary(employee.getSalary());
        }


        if(!StringUtils.isEmpty(employee.getEmail())){
            emp.setEmail(employee.getName());
        }

        if(!StringUtils.isEmpty(employee.getDepartment())){
            emp.setDepartment(employee.getName());
        }


    emp.setModifiedOn(Instant.now());

        return employeeRepository.saveOrUpdate(emp);
    }

    public Employee getEmployeeById(String id) {
        Employee emp = employeeRepository.getEmployeeById(id);
        if(emp == null) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        return emp;

    }

    public Page<Employee> getAllEmployees(Integer pageIndex, Integer itemsPerPage) {
       return employeeRepository.getAllEmployees(pageIndex, itemsPerPage);
    }

    public void deleteEmployee(String id) {

        Employee emp = employeeRepository.getEmployeeById(id);
        if(emp == null) {
            throw new RuntimeException("Employee not found with id: " + id);
        }

        emp.setIsActive(false);
        emp.setModifiedOn(Instant.now());

        employeeRepository.saveOrUpdate(emp);
    }
}
