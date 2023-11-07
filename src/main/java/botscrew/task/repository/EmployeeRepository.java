package botscrew.task.repository;

import botscrew.task.model.Employee;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("FROM Employee e JOIN e.departments d "
            + "WHERE d.name = :departmentName AND e.id = d.head.id")
    Optional<Employee> findByDepartmentAndIsHead(String departmentName);

    @Query("SELECT COUNT(e) FROM Employee e JOIN e.departments d "
            + "WHERE e.degree = :degree AND d.name = :departmentName")
    int countByDepartmentAndDegree(String departmentName, Employee.Degree degree);

    @Query("SELECT AVG(e.salary) FROM Employee e JOIN e.departments d "
            + "WHERE d.name = :department")
    BigDecimal findAverageSalaryByDepartment(String department);

    @Query("SELECT COUNT(e) FROM Employee e JOIN e.departments d "
            + "WHERE d.name = :departmentName")
    int countByDepartment(String departmentName);

    List<Employee> findAllByNameContains(String namePart);
}
