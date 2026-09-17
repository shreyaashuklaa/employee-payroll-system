# Employee Payroll System

## Project Title

Employee Payroll System

## Overview

Employee Payroll System is a Java console-based application developed to manage employee salary records. It supports full-time and contract employees, stores employee details in an SQLite database, calculates salary and tax deductions, and generates payslips.

## Features

- Add full-time employees
- Add contract employees
- View all employee records
- Calculate gross salary
- Calculate tax deduction
- Calculate net salary
- Store employee data using SQLite database
- Generate payslips as text files
- Validate user input
- Prevent duplicate employee email addresses
- Handle invalid employee IDs

## Technologies and Tools Used

- Java
- Maven
- JDBC
- SQLite
- Java File Handling
- IntelliJ IDEA
- Git and GitHub

## Steps to Install and Run

1. Clone the repository.

```bash
git clone https://github.com/shreyaashuklaa/employee-payroll-system.git

2. Open the project folder.

```bash
cd employee-payroll-system
```

3. Make sure Java JDK 17 or above and Maven are installed.

4. Run the project from the terminal.

```bash
mvn compile exec:java
```

5. The Employee Payroll System menu will appear in the terminal.

## Instructions for Testing

Test the following cases:

1. Add a full-time employee.
2. Add a contract employee.
3. View all employees.
4. Calculate salary using a valid employee ID.
5. View tax deduction for an employee.
6. Generate a payslip.
7. Enter an invalid employee ID.
8. Enter negative salary or hours.
9. Enter text instead of a number.
10. Try adding an employee with an already existing email address.

## Output

The application creates an SQLite database file:

```text
payroll.db
```

Generated payslips are saved in:

```text
payslips/
```