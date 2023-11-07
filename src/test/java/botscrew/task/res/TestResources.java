package botscrew.task.res;

import botscrew.task.model.Department;
import botscrew.task.model.Employee;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public record TestResources() {
    public static final String ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD =
            "classpath:database/add_three_employees_of_same_department_with_head.sql";
    public static final String REMOVE_ALL_EMPLOYEES =
            "classpath:database/remove_all_employees.sql";
    public static final String VALID_DEPARTMENT = "Engineering";
    public static final String INVALID_DEPARTMENT = "Dancing";
    public static final int PROFESSORS_COUNT = 1;
    public static final int ASSOCIATE_PROFESSORS_COUNT = 2;
    public static final int ASSISTANTS_COUNT = 3;
    public static final int DEPARTMENT_COUNT = 6;
    public static final BigDecimal EXPECTED_AVERAGE_SALARY = BigDecimal.valueOf(2200);
    public static final String INVALID_PART = "sdgdfgsdfg";
    public static final String SPECIFIC_PART = "Alice";
    public static final String NOT_SPECIFIC_PART = "a";
    public static final Employee expectedHead = new Employee();
    public static final Employee secondEmployee = new Employee();

    static {
        expectedHead.setId(2L);
        expectedHead.setName("Alice Cooper");
        expectedHead.setDegree(Employee.Degree.PROFESSOR);
        Department department = new Department();
        department.setName(VALID_DEPARTMENT);
        expectedHead.setDepartments(Set.of(department));
        expectedHead.setSalary(BigDecimal.valueOf(3600.0).setScale(2, RoundingMode.HALF_UP));

        secondEmployee.setName("Bob Marley");
        secondEmployee.setDegree(Employee.Degree.ASSISTANT);
        department.setName(VALID_DEPARTMENT);
        secondEmployee.setDepartments(Set.of(department));
        secondEmployee.setSalary(BigDecimal.valueOf(1000.0).setScale(2, RoundingMode.HALF_UP));
    }
}
