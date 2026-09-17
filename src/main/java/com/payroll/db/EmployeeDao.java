package com.payroll.db;

import com.payroll.model.ContractEmployee;
import com.payroll.model.Employee;
import com.payroll.model.FullTimeEmployee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class EmployeeDao {

    public Employee addEmployee(Employee employee) throws SQLException {

        String query = """
                INSERT INTO employees (
                    name, email, department, employee_type,
                    monthly_salary, allowance, hourly_rate, hours_worked
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmail());
            statement.setString(3, employee.getDepartment());
            statement.setString(4, employee.getEmployeeType());

            if (employee instanceof FullTimeEmployee fullTimeEmployee) {
                statement.setDouble(5, fullTimeEmployee.getMonthlySalary());
                statement.setDouble(6, fullTimeEmployee.getAllowance());
                statement.setDouble(7, 0);
                statement.setDouble(8, 0);
            } else if (employee instanceof ContractEmployee contractEmployee) {
                statement.setDouble(5, 0);
                statement.setDouble(6, 0);
                statement.setDouble(7, contractEmployee.getHourlyRate());
                statement.setDouble(8, contractEmployee.getHoursWorked());
            }

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getInt(1));
                }
            }

            return employee;
        }
    }

    public Optional<Employee> findEmployeeById(int id) throws SQLException {

        String query = "SELECT * FROM employees WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapEmployee(resultSet));
                }
            }
        }

        return Optional.empty();
    }

    public List<Employee> getAllEmployees() throws SQLException {

        List<Employee> employees = new ArrayList<>();

        String query = "SELECT * FROM employees ORDER BY id";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                employees.add(mapEmployee(resultSet));
            }
        }

        return employees;
    }

    private Employee mapEmployee(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String department = resultSet.getString("department");
        String employeeType = resultSet.getString("employee_type");

        if ("FULL_TIME".equals(employeeType)) {
            return new FullTimeEmployee(
                    id,
                    name,
                    email,
                    department,
                    resultSet.getDouble("monthly_salary"),
                    resultSet.getDouble("allowance")
            );
        }

        return new ContractEmployee(
                id,
                name,
                email,
                department,
                resultSet.getDouble("hourly_rate"),
                resultSet.getDouble("hours_worked")
        );
    }
}