package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Department;

import java.util.List;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Long> {
    //TODO Get a list of department IDS where the number of employees doesn't exceed 3 people
    @Query( value = """
            SELECT d.id FROM department d
            JOIN employee e ON d.id = e.department_id
            GROUP BY d.id
            HAVING COUNT(e) <= 3""",
            nativeQuery = true)
    List<Long> findAllWhereDepartmentDoesntExceedThreePeople();

    //TODO Get a list of departments IDs with the maximum total salary of employees
    @Query( value = """
            SELECT d.id FROM department d
            JOIN employee e ON d.id = e.department_id
            JOIN (
                SELECT d2.id, SUM(e2.salary) AS total_salary FROM department d2
                JOIN employee e2 ON d2.id = e2.department_id
                GROUP BY d2.id
            ) s ON s.id = d.id
            GROUP BY d.id
            HAVING SUM(e.salary) = MAX(s.total_salary)""",
            nativeQuery = true)
    List<Long> findAllByMaxTotalSalary();
}
