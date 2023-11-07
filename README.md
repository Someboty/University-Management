<h3>Welcome to the University Management Console App!</h3> It's a simple Spring Boot Java project that allows you to manage university departments and lectors. This app provides a console interface for interacting with the university's data, including information about departments, lectors, and their respective roles and salaries. All data is stored in a relational database.

## Table of Contents

* [❓ Project Overview](#project-overview)
* [👨‍💻 Tech stack](#tech-stack)
* [⚡️ Getting Started](#getting-started)
* [📝 Commands <a name="commands"></a>](#commands)

## ❓Project Overview

The University Management Console App is a simple Spring Boot java project with the console interface for university, which consists of departments and lectors. The lectors could work in more than one department. A lector could have one degree (assistant, associate professor, professor).
All data is stored in the relational database.


## 👨‍💻Tech stack

Here's a brief high-level overview of the tech stack the **University Management** uses:

- [Spring Boot](https://spring.io/projects/spring-boot): provides a set of pre-built templates and conventions for creating stand-alone, production-grade Spring-based applications.
- [Spring Shell](https://spring.io/projects/spring-shell): provides a way to easily build a full-featured shell ( aka command line) application by depending on the Spring Shell jars and adding their own commands (which come as methods on spring beans).
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/): provides a higher-level abstraction for working with databases and includes support for JPA (Java Persistence API).
- [Hibernate](https://hibernate.org/): simplifies the interaction between Java applications and databases by mapping Java objects to database tables and vice versa.
- [Lombok](https://projectlombok.org/): helps reduce boilerplate code by automatically generating common code constructs (like getters, setters, constructors, etc.) during compile time.
- [Liquibase](https://www.liquibase.org/): helps manage database schema changes over time, making it easier to track and deploy database updates.

## ⚡️Getting Started

First, let's download a [repository](https://github.com/Someboty/bookShop).
Via IDE:
- Open IntelliJ IDEA.
- Select "File" -> "New Project from Version Control."
- Paste the link: https://github.com/Someboty/test-task.git

Via git console command:

*I'll use "d:\Projects" as example of project's location. You can replace it with another folder on your device*

```bash
cd d:\Projects
git clone https://github.com/Someboty/test-task.git
```
Now we are ready to start!

## 📝Commands

After running the app, you can interact with it through the console interface. The following section outlines the available commands and their usage.

- **Who is head of department {department_name}**

        Command: Who is head of department {department_name}
        Example: Who is head of department Mathematics
        Answer: Head of Mathematics department is John Doe

- **Show {department_name} statistics**

        Command: Show {department_name} statistics
        Example: Show Mathematics statistics
        Answer:
            Assistants - 5
            Associate Professors - 3
            Professors - 2

- **Show the average salary for the department {department_name}**

        Command: Show the average salary for the department {department_name}
        Example: Show the average salary for the department Computer Science
        Answer: The average salary of Computer Science is $50,000

- **Show count of employees for {department_name}**

        Command: Show count of employees for {department_name}
        Example: Show count of employees for Mathematics
        Answer: Total employees in Mathematics: 10

- **Global search by {template}**

        Command: Global search by {template}
        Example: Global search by van
        Answer: Search results: Ivan Petrov, Peter Ivanov


**Thank you for considering my Online BookShelf project. I hope it serves as a valuable learning resource and proof of my skills for potential interviewers. Happy coding!**
