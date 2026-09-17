
### `statement.md`

```md
# Project Statement

## Project Title

Employee Payroll System

## Problem Statement

In many small organizations, employee salary details are handled manually. This can lead to calculation mistakes, missing employee records, incorrect tax deductions, and difficulty in preparing payslips.

The Employee Payroll System is developed to solve this problem. It provides a Java-based console application where an administrator can add employee information, calculate salaries, calculate tax deductions, store data in a database, and generate payslips.

## Scope of the Project

The project focuses on basic payroll management for full-time and contract employees.

The system includes:

- Adding employee details
- Supporting full-time employees
- Supporting contract employees
- Storing employee records in SQLite database
- Calculating gross salary
- Calculating tax deduction
- Calculating net salary
- Viewing all saved employees
- Generating payslip text files
- Validating user input

The current version does not include:

- Login or authentication system
- Attendance management
- Bank account integration
- Email sending
- PDF payslip export
- Web or mobile interface

## Target Users

The intended users of this project are:

- HR staff
- Payroll operators
- Small business owners
- Office administrators
- Students learning Java and JDBC

## High-Level Features

### Employee Management

The system allows the user to add full-time and contract employee records. Each record contains employee name, email, department, employee type, and salary-related information.

### Salary Calculation

The application calculates salary based on employee type.

For a full-time employee:

```text
Gross Salary = Monthly Salary + Allowance
