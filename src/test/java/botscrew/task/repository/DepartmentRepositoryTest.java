package botscrew.task.repository;

import static botscrew.task.res.TestResources.ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD;
import static botscrew.task.res.TestResources.INVALID_DEPARTMENT;
import static botscrew.task.res.TestResources.REMOVE_ALL_EMPLOYEES;
import static botscrew.task.res.TestResources.VALID_DEPARTMENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("Confirm employee with correct department existing")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void existsByDepartment_CorrectData_ReturnsTrue() {
        assertTrue(departmentRepository.existsByName(VALID_DEPARTMENT));
    }

    @Test
    @DisplayName("Confirm employee with incorrect department not existing")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void existsByDepartment_IncorrectDepartment_ReturnsFalse() {
        assertFalse(departmentRepository.existsByName(INVALID_DEPARTMENT));
    }

}
