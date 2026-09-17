package com.payroll.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:payroll.db";

    private DatabaseManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initializeDatabase() throws SQLException {

        String query = """
                CREATE TABLE IF NOT EXISTS employees (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    department TEXT NOT NULL,
                    employee_type TEXT NOT NULL,
                    monthly_salary REAL DEFAULT 0,
                    allowance REAL DEFAULT 0,
                    hourly_rate REAL DEFAULT 0,
                    hours_worked REAL DEFAULT 0,
                    created_at TEXT DEFAULT CURRENT_TIMESTAMP
                )
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(query);
        }
    }
}