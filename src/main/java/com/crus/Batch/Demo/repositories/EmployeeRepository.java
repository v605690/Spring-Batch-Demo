package com.crus.Batch.Demo.repositories;

import com.crus.Batch.Demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
