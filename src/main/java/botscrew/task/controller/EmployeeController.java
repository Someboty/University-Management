package botscrew.task.controller;

import botscrew.task.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @ShellMethod(key = "Who is head of department")
    public String headOfDepartment(@ShellOption({"department_name"}) String departmentName) {
        return employeeService.headOfDepartment(departmentName);
    }

    @ShellMethod(key = "Show", value = "Show {department_name} statistics")
    public String showStatistics(String departmentName) {
        return employeeService.showStatistics(departmentName);
    }

    @ShellMethod(key = "Show the average salary for the department ")
    public String showAverageSalary(@ShellOption({"department_name"}) String departmentName) {
        return employeeService.showAverageSalary(departmentName);
    }

    @ShellMethod(key = "Show count of employee for")
    public String showCountForDepartment(@ShellOption({"department_name"}) String departmentName) {
        return employeeService.showCountForDepartment(departmentName);
    }

    @ShellMethod(key = "Global search by ")
    public String globalSearchBy(@ShellOption({"namePart"}) String namePart) {
        return employeeService.globalSearchBy(namePart);
    }
}
