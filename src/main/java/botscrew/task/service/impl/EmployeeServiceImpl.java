package botscrew.task.service.impl;

import botscrew.task.model.Employee;
import botscrew.task.repository.DepartmentRepository;
import botscrew.task.repository.EmployeeRepository;
import botscrew.task.res.Messages;
import botscrew.task.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public String headOfDepartment(String departmentName) {
        checkIfDepartmentExists(departmentName);
        return String.format(Messages.HEAD_OF_DEPARTMENT_MESSAGE, departmentName,
                findHeadOfDepartment(departmentName).getName());
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
        return String.format(Messages.STATISTIC_MESSAGE, assistantsCount,
                associateProfessorsCount, professorsCount);
    }

    @Override
    public String showAverageSalary(String departmentName) {
        checkIfDepartmentExists(departmentName);
        return String.format(Messages.AVERAGE_SALARY_MESSAGE, departmentName,
                employeeRepository.findAverageSalaryByDepartment(departmentName));
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
                .sorted()
                .collect(Collectors.joining(", "));
    }

    private void checkIfDepartmentExists(String departmentName) {
        if (!departmentRepository.existsByName(departmentName)) {
            throw new EntityNotFoundException(Messages.CANT_FIND_DEPARTMENT_MESSAGE
                    + departmentName);
        }
    }

    private Employee findHeadOfDepartment(String departmentName) {
        return employeeRepository.findByDepartmentAndIsHead(departmentName).orElseThrow(
                () -> new EntityNotFoundException(Messages.CANT_FIND_HEAD_OF_DEPARTMENT_MESSAGE
                        + departmentName)
        );
    }
}
