package botscrew.task.controller;

import static org.awaitility.Awaitility.await;
import static org.springframework.shell.test.ShellAssertions.assertThat;

import botscrew.task.service.impl.EmployeeServiceImpl;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.shell.test.ShellTestClient.InteractiveShellSession;
import org.springframework.shell.test.autoconfigure.ShellTest;
import org.springframework.test.context.jdbc.Sql;

@ShellTest
@Import(EmployeeServiceImpl.class)
class EmployeeControllerTest {
    private static final String ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD =
            "classpath:database/add_three_employees_of_same_department_with_head.sql";
    private static final String REMOVE_ALL_EMPLOYEES =
            "classpath:database/remove_all_employees.sql";

    @Autowired
    private ShellTestClient client;

    @Test
    @DisplayName("Find existing head of correct department")
    @Sql(scripts = {REMOVE_ALL_EMPLOYEES, ADD_THREE_EMPLOYEES_OF_SAME_DEPARTMENT_WITH_HEAD},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = REMOVE_ALL_EMPLOYEES, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void headOfDepartment() {
        InteractiveShellSession session = client
                .interactive()
                .run();

        await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> assertThat(session.screen())
                .containsText("shell"));

        session.write(session.writeSequence().text("Who is head of department Engineering")
                .carriageReturn().build());
        await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> assertThat(session.screen())
                .containsText("Alice Cooper"));
    }
}
