package com.example.tpc.service.impl;

import com.example.tpc.model.Employee;
import com.example.tpc.repository.EmployeeRepository;
import com.example.tpc.service.EmployeeService;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Log
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private JmsTemplate jmsTemplate;

  @Value("${solace.queueName}")
  private String queueName;

  @Autowired
  private EmployeeRepository employeeRepository;

  @PostConstruct
  private void init() {

  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void save(final Employee employee, final boolean fail) throws Exception {
    log.info("Sending JMS message to Solace Message Broker..." + employee.getName());
    jmsTemplate.convertAndSend(queueName, employee.getName());

    log.info("Persisting employee data to H2 database...");
    employeeRepository.save(employee);

    if(fail) {
      log.info("Validating distributed transaction rollback...");
      throw new Exception("Exception occurred. Transaction Rollback should happen");
    }
  }
}
