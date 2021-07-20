package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    @Query( value =
            "SELECT * FROM employee e " +
            "WHERE salary > (SELECT salary FROM employee b WHERE b.id = e.boss_id)",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    @Query( value =
            "SELECT * FROM employee e " +
            "WHERE salary = (" +
                    "SELECT MAX(salary) FROM employee " +
                    "JOIN department d ON d.id = employee.department_id " +
                    "WHERE d.id = e.department_id GROUP BY d.id)",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    @Query( value =
            "SELECT * FROM employee e " +
            "WHERE boss_id IS NULL OR boss_id NOT IN (" +
                    "SELECT e2.id FROM employee e2 " +
                    "JOIN department d ON d.id = e2.department_id " +
                    "WHERE d.id = e.department_id)",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}
