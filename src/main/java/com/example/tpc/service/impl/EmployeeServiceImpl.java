package com.example.tpc.service.impl;

import com.example.tpc.model.Employee;
import com.example.tpc.repository.EmployeeRepository;
import com.example.tpc.service.EmployeeService;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
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
    jmsTemplate.convertAndSend(queueName, employee.getName());
    employeeRepository.save(employee);
    if(fail) throw new Exception("Exception occurred. Transaction Rollback should happen");
  }
}
