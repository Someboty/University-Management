package botscrew.task.res;

public record Messages() {
    public static final String HEAD_OF_DEPARTMENT_MESSAGE = "Head of %s department is %s";
    public static final String STATISTIC_MESSAGE =
            "assistants - %d%nassociate professors - %d%nprofessors - %d";
    public static final String AVERAGE_SALARY_MESSAGE = "The average salary of %s is %s";
    public static final String CANT_FIND_DEPARTMENT_MESSAGE = "Can't find department ";
    public static final String CANT_FIND_HEAD_OF_DEPARTMENT_MESSAGE =
            "Can't find head of department ";
}
