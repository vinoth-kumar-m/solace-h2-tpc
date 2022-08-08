package com.example.tpc.config;

import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.solacesystems.jms.SolJmsUtility;
import com.solacesystems.jms.SolXAConnectionFactory;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Log
public class Config {

  @Bean
  public SolXAConnectionFactory solXAConnectionFactory() throws Exception {
    log.info("Initializing Solace XA Connection Factory...");
    final SolXAConnectionFactory solXAConnectionFactory = SolJmsUtility.createXAConnectionFactory();
    solXAConnectionFactory.setHost("smf://localhost:55554");
    solXAConnectionFactory.setVPN("default");
    solXAConnectionFactory.setUsername("admin");
    solXAConnectionFactory.setPassword("admin");
    return solXAConnectionFactory;
  }

  @Bean(initMethod = "init", destroyMethod = "close")
  public AtomikosConnectionFactoryBean atomikosJmsConnectionFactory() throws Exception {
    log.info("Initializing Atomikos JMS Connection Factory...");
    final AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
    atomikosConnectionFactoryBean.setUniqueResourceName("Solace XA Resource");
    atomikosConnectionFactoryBean.setXaConnectionFactory(solXAConnectionFactory());
    atomikosConnectionFactoryBean.setPoolSize(1);
    return atomikosConnectionFactoryBean;
  }

  @Bean
  public JmsTemplate jmsTemplate() throws Exception {
    log.info("Initializing JMS Template...");
    final JmsTemplate jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(atomikosJmsConnectionFactory());
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }
}
