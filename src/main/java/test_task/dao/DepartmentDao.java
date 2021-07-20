package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Department;

import java.util.List;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Long> {
    @Query( value =
            "SELECT d.id FROM department d " +
            "JOIN employee e ON d.id = e.department_id " +
            "GROUP BY d.id " +
            "HAVING COUNT(e) <= 3",
            nativeQuery = true)
    List<Long> findAllWhereDepartmentDoesntExceedThreePeople();

    @Query( value =
            "SELECT id FROM (" +
                    "SELECT d.id, rank() OVER (ORDER BY SUM(e.salary) DESC) AS rank " +
                    "FROM department d " +
                    "JOIN employee e ON d.id = e.department_id " +
                    "GROUP BY d.id) sq " +
            "WHERE rank = 1",
            nativeQuery = true)
    List<Long> findAllByMaxTotalSalary();
}
