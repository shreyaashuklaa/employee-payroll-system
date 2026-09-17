package com.payroll.model;

public final class TaxCalculator {

    private TaxCalculator() {
    }

    public static double calculate(double grossMonthlySalary) {

        if (grossMonthlySalary <= 25000) {
            return 0;
        }

        if (grossMonthlySalary <= 50000) {
            return (grossMonthlySalary - 25000) * 0.05;
        }

        if (grossMonthlySalary <= 100000) {
            return 1250 + (grossMonthlySalary - 50000) * 0.15;
        }

        return 8750 + (grossMonthlySalary - 100000) * 0.25;
    }
}