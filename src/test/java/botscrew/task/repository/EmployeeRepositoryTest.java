package botscrew.task.repository;

import static botscrew.task.res.TestResources.ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD;
import static botscrew.task.res.TestResources.INVALID_DEPARTMENT;
import static botscrew.task.res.TestResources.REMOVE_ALL_EMPLOYEES;
import static botscrew.task.res.TestResources.VALID_DEPARTMENT;
import static botscrew.task.res.TestResources.expectedHead;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import botscrew.task.model.Employee;
import botscrew.task.res.TestResources;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Find existing head of correct department")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentAndIsHead_CorrectData_ReturnsString() {
        Employee expectedHead = TestResources.expectedHead;
        Optional<Employee> expected = Optional.of(expectedHead);

        Optional<Employee> actual = employeeRepository
                .findByDepartmentAndIsHead(VALID_DEPARTMENT);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find existing head of incorrect department")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentAndIsHead_IncorrectDepartment_ReturnsEmpty() {
        Optional<Employee> expected = Optional.empty();

        Optional<Employee> actual = employeeRepository
                .findByDepartmentAndIsHead(INVALID_DEPARTMENT);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Show count of employee for correct department with degrees")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countByDepartmentAndDegree_CorrectData_ReturnsCorrectCount() {
        int expected = 1;

        int actual = employeeRepository.countByDepartmentAndDegree(
                VALID_DEPARTMENT, Employee.Degree.PROFESSOR);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Show count of employee for incorrect department with degrees")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countByDepartmentAndDegree_IncorrectDepartment_ReturnsZero() {
        int expected = 0;

        int actual = employeeRepository.countByDepartmentAndDegree(
                INVALID_DEPARTMENT, Employee.Degree.PROFESSOR);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Finds average salary in correct department")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAverageSalaryByDepartment_CorrectData_ReturnsCorrectSalary() {
        BigDecimal expected = BigDecimal.valueOf(2200.0);

        BigDecimal actual = employeeRepository.findAverageSalaryByDepartment(VALID_DEPARTMENT);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Finds average salary in incorrect department")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAverageSalaryByDepartment_IncorrectDepartment_ReturnsZero() {
        assertNull(employeeRepository.findAverageSalaryByDepartment(INVALID_DEPARTMENT));
    }

    @Test
    @DisplayName("Finds all by name part")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByNameContains_CorrectData_ReturnsEmployeeList() {
        String namePart = "lice";
        List<Employee> expected = List.of(expectedHead);

        List<Employee> actual = employeeRepository.findAllByNameContains(namePart);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Show count of employee for correct department")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countByDepartment_CorrectData_ReturnsCorrectCount() {
        int expected = 3;

        int actual = employeeRepository.countByDepartment(VALID_DEPARTMENT);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Show count of employee for incorrect department")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countByDepartment_IncorrectDepartment_ReturnsZero() {
        int expected = 0;

        int actual = employeeRepository.countByDepartment(INVALID_DEPARTMENT);

        assertEquals(expected, actual);
    }
}
