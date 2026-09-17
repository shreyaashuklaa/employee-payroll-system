package com.payroll.model;

public final class ContractEmployee extends Employee {

    private final double hourlyRate;
    private final double hoursWorked;

    public ContractEmployee(
            Integer id,
            String name,
            String email,
            String department,
            double hourlyRate,
            double hoursWorked
    ) {
        super(id, name, email, department);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String getEmployeeType() {
        return "CONTRACT";
    }

    @Override
    public double calculateGrossSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public double calculateTaxDeduction() {
        return TaxCalculator.calculate(calculateGrossSalary());
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }
}