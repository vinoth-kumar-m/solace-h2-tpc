package com.example.tpc.controller;

import com.example.tpc.model.Employee;
import com.example.tpc.service.EmployeeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Log
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @PostMapping
  public String save(@RequestBody Employee employee, @RequestParam(required = false) boolean fail) throws Exception {
    log.info("Saving Employee Details..." + employee + ", Fail Status: " + fail);
    employeeService.save(employee, fail);
    return "success";
  }
}
