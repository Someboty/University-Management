package botscrew.task.service;

public interface EmployeeService {
    String headOfDepartment(String departmentName);

    String showStatistics(String departmentName);

    String showAverageSalary(String departmentName);

    String showCountForDepartment(String departmentName);

    String globalSearchBy(String namePart);
}
