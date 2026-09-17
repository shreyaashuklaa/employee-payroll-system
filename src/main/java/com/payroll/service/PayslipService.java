package com.payroll.service;

import com.payroll.model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public final class PayslipService {

    private static final Path PAYSLIP_FOLDER = Path.of("payslips");

    public Path generatePayslip(Employee employee) throws IOException {

        Files.createDirectories(PAYSLIP_FOLDER);

        String currentMonth = YearMonth.now()
                .format(DateTimeFormatter.ofPattern("MMMM yyyy"));

        String payslipContent = """
                ==================================================
                            EMPLOYEE PAYSLIP
                ==================================================
                Pay Period    : %s
                Employee ID   : %d
                Employee Name : %s
                Email         : %s
                Department    : %s
                Employee Type : %s
                --------------------------------------------------
                Gross Salary  : Rs. %,.2f
                Tax Deduction : Rs. %,.2f
                --------------------------------------------------
                Net Salary    : Rs. %,.2f
                ==================================================
                """.formatted(
                currentMonth,
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getEmployeeType(),
                employee.calculateGrossSalary(),
                employee.calculateTaxDeduction(),
                employee.calculateNetSalary()
        );

        String safeName = employee.getName().replaceAll("[^a-zA-Z0-9]", "_");

        Path filePath = PAYSLIP_FOLDER.resolve(
                "payslip_" + employee.getId() + "_" + safeName + ".txt"
        );

        Files.writeString(filePath, payslipContent);

        return filePath.toAbsolutePath();
    }
}