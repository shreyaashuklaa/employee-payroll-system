package com.payroll.model;

public interface Payable {

    double calculateGrossSalary();

    double calculateTaxDeduction();

    default double calculateNetSalary() {
        return calculateGrossSalary() - calculateTaxDeduction();
    }
}