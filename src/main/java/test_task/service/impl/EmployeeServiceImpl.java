package test_task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.dao.EmployeeDao;
import test_task.model.Employee;
import test_task.service.EmployeeService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<Employee> findAllBySalaryGreaterThatBoss() {
        return employeeDao.findAllWhereSalaryGreaterThatBoss();
    }

    @Override
    public List<Employee> findAllByMaxSalary() {
        return employeeDao.findAllByMaxSalary();
    }

    @Override
    public List<Employee> findAllWithoutBoss() {
        return employeeDao.findAllWithoutBoss();
    }

    @Override
    public Long fireEmployee(String name) {
        Iterable<Employee> employees = employeeDao.findAll();

        Iterator<Employee> iterator = employees.iterator();
        while(iterator.hasNext()) {
            if (iterator.next().getName().equals(name)) {
                iterator.remove();
            }
        }

        employeeDao.saveAll(employees);
        return 0L;
    }

    @Override
    public Long changeSalary(String name) {
        Iterable<Employee> employees = employeeDao.findAll();

        //how should the salary change?
        BigDecimal increaseSalaryLevel = new BigDecimal(1000);

        employees.forEach(employee -> {
            if (employee.getName().equals(name)) {
                employee.setSalary(employee.getSalary().add(increaseSalaryLevel));
            }
        });

        employeeDao.saveAll(employees);
        return 0L;
    }

    @Override
    public Long hireEmployee(Employee employee) {
        //TODO Implement method using Collection and DAO
        // ---write your code here


        return 0L;
    }
}
