package botscrew.task.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import botscrew.task.model.Employee;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private static final String ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD =
            "classpath:database/add_three_employees_of_same_department_with_head.sql";
    private static final String ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITHOUT_HEAD =
            "classpath:database/add_three_employees_of_same_department_without_head.sql";
    private static final String REMOVE_ALL_EMPLOYEES =
            "classpath:database/remove_all_employees.sql";
    private static final String VALID_DEPARTMENT = "Engineering";
    private static final String INVALID_DEPARTMENT = "Dancing";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Find existing head of correct department")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentAndIsHead_CorrectData_ReturnsString() {
        Employee expectedHead = generateCorrectHead();
        Optional<Employee> expected = Optional.of(expectedHead);

        Optional<Employee> actual = employeeRepository
                .findByDepartmentAndIsHead(VALID_DEPARTMENT, true);

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
                .findByDepartmentAndIsHead(INVALID_DEPARTMENT, true);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find non-existing head of correct department")
    @Sql(scripts = ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITHOUT_HEAD,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentAndIsHead_NonExistingHead_ReturnsEmpty() {
        Optional<Employee> expected = Optional.empty();

        Optional<Employee> actual = employeeRepository
                .findByDepartmentAndIsHead(VALID_DEPARTMENT, true);

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
    @DisplayName("Confirm employee with correct department existing")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void existsByDepartment_CorrectData_ReturnsTrue() {
        assertTrue(employeeRepository.existsByDepartment(VALID_DEPARTMENT));
    }

    @Test
    @DisplayName("Confirm employee with incorrect department not existing")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void existsByDepartment_IncorrectDepartment_ReturnsFalse() {
        assertFalse(employeeRepository.existsByDepartment(INVALID_DEPARTMENT));
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
    @DisplayName("Finds a salary in incorrect department")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByNameContains() {
        String namePart = "lice";
        List<Employee> expected = List.of(generateCorrectHead());

        List<Employee> actual = employeeRepository.findAllByNameContains(namePart);

        assertEquals(expected, actual);
    }

    private Employee generateCorrectHead() {
        Employee expectedHead = new Employee();
        expectedHead.setId(2L);
        expectedHead.setName("Alice Cooper");
        expectedHead.setDegree(Employee.Degree.PROFESSOR);
        expectedHead.setDepartment(VALID_DEPARTMENT);
        expectedHead.setSalary(BigDecimal.valueOf(3600.0).setScale(2, RoundingMode.HALF_UP));
        expectedHead.setHead(true);
        return expectedHead;
    }
}
