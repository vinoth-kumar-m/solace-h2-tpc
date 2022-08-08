package com.example.tpc.controller;

import com.example.tpc.model.Employee;
import com.example.tpc.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

  @Autowired
  private EmployeeService employeeService;

  @PostMapping
  public String save(@RequestBody Employee employee, @RequestParam(required = false) boolean fail) throws Exception {
    logger.info("Saving Employee Details...{}, Fail Status: {}", employee, fail);
    employeeService.save(employee, fail);
    return "success";
  }
}
