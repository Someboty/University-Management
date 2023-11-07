package botscrew.task.repository;

import botscrew.task.model.Employee;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByDepartmentAndIsHead(String departmentName, boolean isHead);

    int countByDepartment(String departmentName);

    int countByDepartmentAndDegree(String departmentName, Employee.Degree degree);

    boolean existsByDepartment(String departmentName);

    @Query("SELECT AVG(e.salary) FROM Employee e WHERE e.department = :department")
    BigDecimal findAverageSalaryByDepartment(String department);

    List<Employee> findAllByNameContains(String namePart);
}
