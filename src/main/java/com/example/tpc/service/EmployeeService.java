package com.example.tpc.service;

import com.example.tpc.model.Employee;

public interface EmployeeService {
  void save(final Employee employee, final boolean fail) throws Exception;
}
