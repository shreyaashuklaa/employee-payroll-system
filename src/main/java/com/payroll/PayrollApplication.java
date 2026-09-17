package com.payroll;

import com.payroll.db.DatabaseManager;
import com.payroll.db.EmployeeDao;
import com.payroll.model.ContractEmployee;
import com.payroll.model.Employee;
import com.payroll.model.FullTimeEmployee;
import com.payroll.service.PayslipService;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PayrollApplication {

    private final Scanner scanner = new Scanner(System.in);
    private final EmployeeDao employeeDao = new EmployeeDao();
    private final PayslipService payslipService = new PayslipService();

    public static void main(String[] args) {

        try {
            DatabaseManager.initializeDatabase();

            PayrollApplication application = new PayrollApplication();
            application.start();

        } catch (SQLException exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    private void start() {

        boolean running = true;

        System.out.println("\n=================================");
        System.out.println("   EMPLOYEE PAYROLL SYSTEM");
        System.out.println("=================================");

        while (running) {

            showMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> displayAllEmployees();
                case 3 -> calculateSalary();
                case 4 -> displayTaxDeduction();
                case 5 -> generatePayslip();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for using Payroll System.");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {

        System.out.println("""
                
                1. Add Employee
                2. View All Employees
                3. Calculate Salary
                4. View Tax Deduction
                5. Generate Payslip
                0. Exit
                """);
    }

    private void addEmployee() {

        System.out.println("\n1. Full Time Employee");
        System.out.println("2. Contract Employee");

        int type = readInt("Select employee type: ");

        if (type != 1 && type != 2) {
            System.out.println("Invalid employee type.");
            return;
        }

        String name = readNonEmptyString("Enter name: ");
        String email = readNonEmptyString("Enter email: ");
        String department = readNonEmptyString("Enter department: ");

        Employee employee;

        if (type == 1) {

            double monthlySalary = readPositiveDouble("Enter monthly salary: ");
            double allowance = readNonNegativeDouble("Enter allowance: ");

            employee = new FullTimeEmployee(
                    null,
                    name,
                    email,
                    department,
                    monthlySalary,
                    allowance
            );

        } else {

            double hourlyRate = readPositiveDouble("Enter hourly rate: ");
            double hoursWorked = readPositiveDouble("Enter hours worked: ");

            employee = new ContractEmployee(
                    null,
                    name,
                    email,
                    department,
                    hourlyRate,
                    hoursWorked
            );
        }

        try {
            employeeDao.addEmployee(employee);

            System.out.println("\nEmployee added successfully.");
            System.out.println("Employee ID: " + employee.getId());

        } catch (SQLException exception) {
            System.out.println("Employee could not be added.");
            System.out.println("Reason: Email must be unique.");
        }
    }

    private void displayAllEmployees() {

        try {

            List<Employee> employees = employeeDao.getAllEmployees();

            if (employees.isEmpty()) {
                System.out.println("\nNo employees found.");
                return;
            }

            System.out.println("\n------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-15s %-20s %-18s%n",
                    "ID", "NAME", "TYPE", "DEPARTMENT", "NET SALARY");
            System.out.println("------------------------------------------------------------------------------------------------");

            for (Employee employee : employees) {
                System.out.printf(
                        "%-5d %-20s %-15s %-20s Rs. %,.2f%n",
                        employee.getId(),
                        employee.getName(),
                        employee.getEmployeeType(),
                        employee.getDepartment(),
                        employee.calculateNetSalary()
                );
            }

            System.out.println("------------------------------------------------------------------------------------------------");

        } catch (SQLException exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    private void calculateSalary() {

        Optional<Employee> optionalEmployee =
                findEmployee("Enter employee ID: ");

        if (optionalEmployee.isEmpty()) {
            return;
        }

        Employee employee = optionalEmployee.get();

        System.out.println("\n========== SALARY DETAILS ==========");
        System.out.println("Employee Name : " + employee.getName());
        System.out.printf("Gross Salary  : Rs. %,.2f%n",
                employee.calculateGrossSalary());
        System.out.printf("Tax Deduction : Rs. %,.2f%n",
                employee.calculateTaxDeduction());
        System.out.printf("Net Salary    : Rs. %,.2f%n",
                employee.calculateNetSalary());
        System.out.println("====================================");
    }

    private void displayTaxDeduction() {

        Optional<Employee> optionalEmployee =
                findEmployee("Enter employee ID: ");

        if (optionalEmployee.isEmpty()) {
            return;
        }

        Employee employee = optionalEmployee.get();

        System.out.printf(
                "%nTax deduction for %s: Rs. %,.2f%n",
                employee.getName(),
                employee.calculateTaxDeduction()
        );
    }

    private void generatePayslip() {

        Optional<Employee> optionalEmployee =
                findEmployee("Enter employee ID: ");

        if (optionalEmployee.isEmpty()) {
            return;
        }

        try {

            Path payslipPath = payslipService
                    .generatePayslip(optionalEmployee.get());

            System.out.println("\nPayslip generated successfully.");
            System.out.println("File location: " + payslipPath);

        } catch (Exception exception) {
            System.out.println("Could not generate payslip.");
            System.out.println("Reason: " + exception.getMessage());
        }
    }

    private Optional<Employee> findEmployee(String message) {

        int id = readInt(message);

        try {

            Optional<Employee> employee = employeeDao.findEmployeeById(id);

            if (employee.isEmpty()) {
                System.out.println("Employee not found.");
            }

            return employee;

        } catch (SQLException exception) {
            System.out.println("Database error: " + exception.getMessage());
            return Optional.empty();
        }
    }

    private int readInt(String message) {

        while (true) {

            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private double readPositiveDouble(String message) {

        while (true) {

            try {
                System.out.print(message);

                double value = Double.parseDouble(scanner.nextLine().trim());

                if (value > 0) {
                    return value;
                }

                System.out.println("Please enter a positive number.");

            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private double readNonNegativeDouble(String message) {

        while (true) {

            try {
                System.out.print(message);

                double value = Double.parseDouble(scanner.nextLine().trim());

                if (value >= 0) {
                    return value;
                }

                System.out.println("Please enter zero or a positive number.");

            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readNonEmptyString(String message) {

        while (true) {

            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }
}