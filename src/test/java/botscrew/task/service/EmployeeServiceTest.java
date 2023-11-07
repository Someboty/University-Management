package botscrew.task.service;

import static botscrew.task.res.TestResources.ASSISTANTS_COUNT;
import static botscrew.task.res.TestResources.ASSOCIATE_PROFESSORS_COUNT;
import static botscrew.task.res.TestResources.DEPARTMENT_COUNT;
import static botscrew.task.res.TestResources.EXPECTED_AVERAGE_SALARY;
import static botscrew.task.res.TestResources.INVALID_DEPARTMENT;
import static botscrew.task.res.TestResources.INVALID_PART;
import static botscrew.task.res.TestResources.NOT_SPECIFIC_PART;
import static botscrew.task.res.TestResources.PROFESSORS_COUNT;
import static botscrew.task.res.TestResources.SPECIFIC_PART;
import static botscrew.task.res.TestResources.VALID_DEPARTMENT;
import static botscrew.task.res.TestResources.expectedHead;
import static botscrew.task.res.TestResources.secondEmployee;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import botscrew.task.model.Employee;
import botscrew.task.repository.DepartmentRepository;
import botscrew.task.repository.EmployeeRepository;
import botscrew.task.res.Messages;
import botscrew.task.service.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    @DisplayName("Find existing head of correct department")
    public void headOfDepartment_CorrectData_ReturnsString() {
        Employee expectedEmployee = expectedHead;
        Optional<Employee> optionalEmployee = Optional.of(expectedEmployee);
        String expected = String.format(Messages.HEAD_OF_DEPARTMENT_MESSAGE,
                VALID_DEPARTMENT, expectedEmployee.getName());

        when(departmentRepository.existsByName(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.findByDepartmentAndIsHead(VALID_DEPARTMENT))
                .thenReturn(optionalEmployee);

        String actual = employeeService.headOfDepartment(VALID_DEPARTMENT);

        assertEquals(expected, actual);
        verify(departmentRepository).existsByName(VALID_DEPARTMENT);
        verifyNoMoreInteractions(departmentRepository);
        verify(employeeRepository).findByDepartmentAndIsHead(VALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Try to find head of incorrect department")
    public void headOfDepartment_IncorrectDepartment_ExceptionThrown() {
        String expected = Messages.CANT_FIND_DEPARTMENT_MESSAGE + INVALID_DEPARTMENT;

        when(departmentRepository.existsByName(INVALID_DEPARTMENT)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.headOfDepartment(INVALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(departmentRepository).existsByName(INVALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Try to find non-existing head of correct department")
    public void headOfDepartment_NonExistingHead_ExceptionThrown() {
        String expected = Messages.CANT_FIND_HEAD_OF_DEPARTMENT_MESSAGE + VALID_DEPARTMENT;

        when(departmentRepository.existsByName(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.findByDepartmentAndIsHead(VALID_DEPARTMENT))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.headOfDepartment(VALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(departmentRepository).existsByName(VALID_DEPARTMENT);
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    @DisplayName("Show existing department statistics")
    public void showStatistics_CorrectData_ReturnsString() {

        when(departmentRepository.existsByName(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.countByDepartmentAndDegree(
                VALID_DEPARTMENT, Employee.Degree.ASSISTANT)).thenReturn(ASSISTANTS_COUNT);
        when(employeeRepository.countByDepartmentAndDegree(
                VALID_DEPARTMENT, Employee.Degree.ASSOCIATE_PROFESSOR))
                .thenReturn(ASSOCIATE_PROFESSORS_COUNT);
        when(employeeRepository.countByDepartmentAndDegree(
                VALID_DEPARTMENT, Employee.Degree.PROFESSOR)).thenReturn(PROFESSORS_COUNT);

        String actual = employeeService.showStatistics(VALID_DEPARTMENT);

        String expected = String.format(Messages.STATISTIC_MESSAGE, ASSISTANTS_COUNT,
                ASSOCIATE_PROFESSORS_COUNT, PROFESSORS_COUNT);
        assertEquals(expected, actual);
        verify(departmentRepository).existsByName(VALID_DEPARTMENT);
        verifyNoMoreInteractions(departmentRepository);
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
        String expected = Messages.CANT_FIND_DEPARTMENT_MESSAGE + INVALID_DEPARTMENT;

        when(departmentRepository.existsByName(INVALID_DEPARTMENT)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.showStatistics(INVALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(departmentRepository).existsByName(INVALID_DEPARTMENT);
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    @DisplayName("Show average salary of correct department")
    public void showAverageSalary_CorrectData_ReturnsString() {
        String expected = String.format(Messages.AVERAGE_SALARY_MESSAGE,
                VALID_DEPARTMENT, EXPECTED_AVERAGE_SALARY);

        when(departmentRepository.existsByName(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.findAverageSalaryByDepartment(VALID_DEPARTMENT))
                .thenReturn(EXPECTED_AVERAGE_SALARY);

        String actual = employeeService.showAverageSalary(VALID_DEPARTMENT);

        assertEquals(expected, actual);
        verify(departmentRepository).existsByName(VALID_DEPARTMENT);
        verifyNoMoreInteractions(departmentRepository);
        verify(employeeRepository).findAverageSalaryByDepartment(VALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Try to show average salary of incorrect department")
    public void showAverageSalary_IncorrectDepartment_ExceptionThrown() {
        String expected = Messages.CANT_FIND_DEPARTMENT_MESSAGE + INVALID_DEPARTMENT;

        when(departmentRepository.existsByName(INVALID_DEPARTMENT)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.showAverageSalary(INVALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(departmentRepository).existsByName(INVALID_DEPARTMENT);
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    @DisplayName("Show count of correct department")
    public void showCountForDepartment_CorrectData_ReturnsString() {
        String expected = String.valueOf(DEPARTMENT_COUNT);

        when(departmentRepository.existsByName(VALID_DEPARTMENT)).thenReturn(true);
        when(employeeRepository.countByDepartment(VALID_DEPARTMENT)).thenReturn(DEPARTMENT_COUNT);

        String actual = employeeService.showCountForDepartment(VALID_DEPARTMENT);

        assertEquals(expected, actual);
        verify(departmentRepository).existsByName(VALID_DEPARTMENT);
        verifyNoMoreInteractions(departmentRepository);
        verify(employeeRepository).countByDepartment(VALID_DEPARTMENT);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Try to show count of incorrect department")
    public void showCountForDepartment_IncorrectDepartment_ExceptionThrown() {
        String expected = Messages.CANT_FIND_DEPARTMENT_MESSAGE + INVALID_DEPARTMENT;

        when(departmentRepository.existsByName(INVALID_DEPARTMENT)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.showCountForDepartment(INVALID_DEPARTMENT));

        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(departmentRepository).existsByName(INVALID_DEPARTMENT);
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
        Employee expectedEmployee = expectedHead;
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
        Employee first = expectedHead;
        Employee second = secondEmployee;
        String expected = first.getName() + ", " + second.getName();

        when(employeeRepository.findAllByNameContains(NOT_SPECIFIC_PART))
                .thenReturn(List.of(first, second));

        String actual = employeeService.globalSearchBy(NOT_SPECIFIC_PART);

        assertEquals(expected, actual);
        verify(employeeRepository).findAllByNameContains(NOT_SPECIFIC_PART);
        verifyNoMoreInteractions(employeeRepository);
    }
}
