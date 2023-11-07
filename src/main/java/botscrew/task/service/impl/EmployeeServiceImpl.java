package botscrew.task.service.impl;

import botscrew.task.model.Employee;
import botscrew.task.repository.EmployeeRepository;
import botscrew.task.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public String headOfDepartment(String departmentName) {
        checkIfDepartmentExists(departmentName);
        return String.format(
                "Head of " + departmentName + " department is "
                        + findHeadOfDepartment(departmentName).getName());
    }

    @Override
    public String showStatistics(String departmentName) {
        checkIfDepartmentExists(departmentName);
        int assistantsCount = employeeRepository.countByDepartmentAndDegree(
                departmentName, Employee.Degree.ASSISTANT);
        int associateProfessorsCount = employeeRepository.countByDepartmentAndDegree(
                departmentName, Employee.Degree.ASSOCIATE_PROFESSOR);
        int professorsCount = employeeRepository.countByDepartmentAndDegree(
                departmentName, Employee.Degree.PROFESSOR);
        return String.format("assistants - " + assistantsCount + System.lineSeparator()
                + "associate professors - " + associateProfessorsCount + System.lineSeparator()
                + " professors - " + professorsCount);
    }

    @Override
    public String showAverageSalary(String departmentName) {
        checkIfDepartmentExists(departmentName);
        return String.format("The average salary of " + departmentName + " is "
                + employeeRepository.findAverageSalaryByDepartment(departmentName));
    }

    @Override
    public String showCountForDepartment(String departmentName) {
        checkIfDepartmentExists(departmentName);
        return String.valueOf(employeeRepository.countByDepartment(departmentName));
    }

    @Override
    public String globalSearchBy(String namePart) {
        return employeeRepository.findAllByNameContains(namePart).stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", "));
    }

    private void checkIfDepartmentExists(String departmentName) {
        if (!employeeRepository.existsByDepartment(departmentName)) {
            throw new EntityNotFoundException("Can't find department " + departmentName);
        }
    }

    private Employee findHeadOfDepartment(String departmentName) {
        return employeeRepository.findByDepartmentAndIsHead(departmentName, true).orElseThrow(
                () -> new EntityNotFoundException("Can't find head of department " + departmentName)
        );
    }
}
