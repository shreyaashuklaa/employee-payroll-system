package com.payroll.model;

public final class FullTimeEmployee extends Employee {

    private final double monthlySalary;
    private final double allowance;

    public FullTimeEmployee(
            Integer id,
            String name,
            String email,
            String department,
            double monthlySalary,
            double allowance
    ) {
        super(id, name, email, department);
        this.monthlySalary = monthlySalary;
        this.allowance = allowance;
    }

    @Override
    public String getEmployeeType() {
        return "FULL_TIME";
    }

    @Override
    public double calculateGrossSalary() {
        return monthlySalary + allowance;
    }

    @Override
    public double calculateTaxDeduction() {
        return TaxCalculator.calculate(calculateGrossSalary());
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public double getAllowance() {
        return allowance;
    }
}