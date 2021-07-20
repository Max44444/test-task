package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    //TODO Get a list of employees receiving a salary greater than that of the boss
    @Query( value = """
            SELECT * FROM employee e
            WHERE salary > (SELECT salary FROM employee b WHERE b.id = e.boss_id);""",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    //TODO Get a list of employees receiving the maximum salary in their department
    @Query( value = """
            SELECT * FROM employee e
            WHERE salary = (
                SELECT MAX(salary) FROM employee
                JOIN department d ON d.id = employee.department_id
                WHERE d.id = e.department_id
                GROUP BY d.id
            )""",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    //TODO Get a list of employees who do not have boss in the same department
    @Query( value = """
            SELECT * FROM employee e
            WHERE boss_id NOT IN (
                SELECT e2.id FROM employee e2
                JOIN department d ON d.id = e2.department_id
                WHERE d.id = e.department_id
            )""",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}
