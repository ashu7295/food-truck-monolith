package com.foodtruck.repository;

import com.foodtruck.entity.SalesExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesExpenseRepository extends JpaRepository<SalesExpense, Long> {
}
