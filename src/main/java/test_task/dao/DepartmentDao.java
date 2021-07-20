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
    @Query(
            value = "",
            nativeQuery = true)
    List<Long> findAllByMaxTotalSalary();
}
