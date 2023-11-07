package botscrew.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import botscrew.task.model.Employee;
import botscrew.task.repository.EmployeeRepository;
import botscrew.task.service.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    private static final int PROFESSORS_COUNT = 1;
    private static final int ASSOCIATE_PROFESSORS_COUNT = 2;
    private static final int ASSISTANTS_COUNT = 3;
    private static final int DEPARTMENT_COUNT = 6;
    private static final BigDecimal EXPECTED_AVERAGE_SALARY = BigDecimal.valueOf(2200);
    private static final String VALID_DEPARTMENT = "Engineering";
    private static final String INVALID_DEPARTMENT = "Dancing";
    private static final String INVALID_PART = "sdgdfgsdfg";
    private static final String SPECIFIC_PART = "Alice";
    private static final String NOT_SPECIFIC_PART = "a";

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    @DisplayName("Find existing head of correct department")
    public void headOfDepartment_CorrectData_ReturnsString() {
        Employee expectedEmployee = generateCorrectHead();
        Optional<Employee> optionalEmployee = Optional.of(expectedEmployee);
        String expected = "Head of " + VALID_DEPARTMENT + " department is "
                + expectedEmployee.getName();

        when(employeeRepository.existsByDepartment(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.findByDepartmentAndIsHead(VALID_DEPARTMENT, true))
                .thenReturn(optionalEmployee);

        String actual = employeeService.headOfDepartment(VALID_DEPARTMENT);

        assertEquals(expected, actual);
        verify(employeeRepository).existsByDepartment(VALID_DEPARTMENT);
        verify(employeeRepository).findByDepartmentAndIsHead(VALID_DEPARTMENT, true);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Try to find head of incorrect department")
    public void headOfDepartment_IncorrectDepartment_ExceptionThrown() {
        String expected = "Can't find department " + INVALID_DEPARTMENT;

        when(employeeRepository.existsByDepartment(INVALID_DEPARTMENT)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.headOfDepartment(INVALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(employeeRepository).existsByDepartment(INVALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Try to find non-existing head of correct department")
    public void headOfDepartment_NonExistingHead_ExceptionThrown() {
        String expected = "Can't find head of department " + VALID_DEPARTMENT;

        when(employeeRepository.existsByDepartment(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.findByDepartmentAndIsHead(VALID_DEPARTMENT, true))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.headOfDepartment(VALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(employeeRepository).existsByDepartment(VALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Show existing department statistics")
    public void showStatistics_CorrectData_ReturnsString() {

        when(employeeRepository.existsByDepartment(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.countByDepartmentAndDegree(
                VALID_DEPARTMENT, Employee.Degree.ASSISTANT)).thenReturn(ASSISTANTS_COUNT);
        when(employeeRepository.countByDepartmentAndDegree(
                VALID_DEPARTMENT, Employee.Degree.ASSOCIATE_PROFESSOR))
                .thenReturn(ASSOCIATE_PROFESSORS_COUNT);
        when(employeeRepository.countByDepartmentAndDegree(
                VALID_DEPARTMENT, Employee.Degree.PROFESSOR)).thenReturn(PROFESSORS_COUNT);

        String actual = employeeService.showStatistics(VALID_DEPARTMENT);

        String expected = String.format("assistants - " + ASSISTANTS_COUNT + System.lineSeparator()
                + "associate professors - " + ASSOCIATE_PROFESSORS_COUNT + System.lineSeparator()
                + " professors - " + PROFESSORS_COUNT);
        assertEquals(expected, actual);
        verify(employeeRepository).existsByDepartment(VALID_DEPARTMENT);
        verify(employeeRepository)
                .countByDepartmentAndDegree(VALID_DEPARTMENT, Employee.Degree.ASSISTANT);
        verify(employeeRepository)
                .countByDepartmentAndDegree(VALID_DEPARTMENT, Employee.Degree.ASSOCIATE_PROFESSOR);
        verify(employeeRepository)
                .countByDepartmentAndDegree(VALID_DEPARTMENT, Employee.Degree.PROFESSOR);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Try to show statistics of incorrect department")
    public void showStatistics_IncorrectDepartment_ExceptionThrown() {
        String expected = "Can't find department " + INVALID_DEPARTMENT;

        when(employeeRepository.existsByDepartment(INVALID_DEPARTMENT)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.showStatistics(INVALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(employeeRepository).existsByDepartment(INVALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Show average salary of correct department")
    public void showAverageSalary_CorrectData_ReturnsString() {
        String expected = "The average salary of " + VALID_DEPARTMENT + " is "
                + EXPECTED_AVERAGE_SALARY;

        when(employeeRepository.existsByDepartment(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.findAverageSalaryByDepartment(VALID_DEPARTMENT))
                .thenReturn(EXPECTED_AVERAGE_SALARY);

        String actual = employeeService.showAverageSalary(VALID_DEPARTMENT);

        assertEquals(expected, actual);
        verify(employeeRepository).existsByDepartment(VALID_DEPARTMENT);
        verify(employeeRepository).findAverageSalaryByDepartment(VALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Try to show average salary of incorrect department")
    public void showAverageSalary_IncorrectDepartment_ExceptionThrown() {
        String expected = "Can't find department " + INVALID_DEPARTMENT;

        when(employeeRepository.existsByDepartment(INVALID_DEPARTMENT)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.showAverageSalary(INVALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(employeeRepository).existsByDepartment(INVALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Show count of correct department")
    public void showCountForDepartment_CorrectData_ReturnsString() {
        String expected = String.valueOf(DEPARTMENT_COUNT);

        when(employeeRepository.existsByDepartment(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.countByDepartment(VALID_DEPARTMENT)).thenReturn(DEPARTMENT_COUNT);

        String actual = employeeService.showCountForDepartment(VALID_DEPARTMENT);

        assertEquals(expected, actual);
        verify(employeeRepository).existsByDepartment(VALID_DEPARTMENT);
        verify(employeeRepository).countByDepartment(VALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Try to show count of incorrect department")
    public void showCountForDepartment_IncorrectDepartment_ExceptionThrown() {
        String expected = "Can't find department " + INVALID_DEPARTMENT;

        when(employeeRepository.existsByDepartment(INVALID_DEPARTMENT)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.showCountForDepartment(INVALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(employeeRepository).existsByDepartment(INVALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Find nothing in global search")
    public void globalSearchBy_NoEmployeesFound_ReturnsEmptyString() {
        String expected = "";

        when(employeeRepository.findAllByNameContains(INVALID_PART))
                .thenReturn(Collections.emptyList());

        String actual = employeeService.globalSearchBy(INVALID_PART);

        assertEquals(expected, actual);
        verify(employeeRepository).findAllByNameContains(INVALID_PART);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Find one employee in global search")
    public void globalSearchBy_OneEmployeeFound_ReturnsCorrectString() {
        Employee expectedEmployee = generateCorrectHead();
        String expected = expectedEmployee.getName();

        when(employeeRepository.findAllByNameContains(SPECIFIC_PART))
                .thenReturn(List.of(expectedEmployee));

        String actual = employeeService.globalSearchBy(SPECIFIC_PART);

        assertEquals(expected, actual);
        verify(employeeRepository).findAllByNameContains(SPECIFIC_PART);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Find one employee in global search")
    public void globalSearchBy_TwoEmployeesFound_ReturnsCorrectString() {
        Employee firstEmployee = generateCorrectHead();
        Employee secondEmployee = generateSecondEmployee();
        String expected = firstEmployee.getName() + ", " + secondEmployee.getName();

        when(employeeRepository.findAllByNameContains(NOT_SPECIFIC_PART))
                .thenReturn(List.of(firstEmployee, secondEmployee));

        String actual = employeeService.globalSearchBy(NOT_SPECIFIC_PART);

        assertEquals(expected, actual);
        verify(employeeRepository).findAllByNameContains(NOT_SPECIFIC_PART);
        verifyNoMoreInteractions(employeeRepository);
    }

    private Employee generateCorrectHead() {
        Employee expectedHead = new Employee();
        expectedHead.setName("Alice Cooper");
        expectedHead.setDegree(Employee.Degree.PROFESSOR);
        expectedHead.setDepartment(VALID_DEPARTMENT);
        expectedHead.setSalary(BigDecimal.valueOf(3600.0).setScale(2, RoundingMode.HALF_UP));
        expectedHead.setHead(true);
        return expectedHead;
    }

    private Employee generateSecondEmployee() {
        Employee secondEmployee = new Employee();
        secondEmployee.setName("Bob Marley");
        secondEmployee.setDegree(Employee.Degree.ASSISTANT);
        secondEmployee.setDepartment(VALID_DEPARTMENT);
        secondEmployee.setSalary(BigDecimal.valueOf(1000.0).setScale(2, RoundingMode.HALF_UP));
        secondEmployee.setHead(false);
        return secondEmployee;
    }
}
