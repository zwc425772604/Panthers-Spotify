package com.config;  
  
import java.util.Properties;

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.ComponentScan;  
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;  
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;  
import org.springframework.web.servlet.view.UrlBasedViewResolver; 
  
@Configuration
@ComponentScan("com")
@EnableWebMvc   
@EnableTransactionManagement
@PropertySource("classpath:app.properties")
public class Config extends WebMvcConfigurerAdapter {  
      
    @Bean  
    public UrlBasedViewResolver setupViewResolver() { 
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();  
        resolver.setPrefix("/jsp/");  
        resolver.setSuffix(".jsp");  
        resolver.setViewClass(JstlView.class);  
        return resolver;  
    }  
    @Bean
    public LocalEntityManagerFactoryBean geEntityManagerFactoryBean() {
       LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
       factoryBean.setPersistenceUnitName("pan");
       return factoryBean;
    }

    @Bean
    public JpaTransactionManager geJpaTransactionManager() {
       JpaTransactionManager transactionManager = new JpaTransactionManager();
       transactionManager.setEntityManagerFactory(geEntityManagerFactoryBean().getObject());
       return transactionManager;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSender mailSender = new JavaMailSenderImpl();
        ((JavaMailSenderImpl) mailSender).setHost("smtp.gmail.com");
        ((JavaMailSenderImpl) mailSender).setPort(587);
         
        ((JavaMailSenderImpl) mailSender).setUsername("panthersSpotify@gmail.com");
        ((JavaMailSenderImpl) mailSender).setPassword("12345678Aa");
         
        Properties props = ((JavaMailSenderImpl) mailSender).getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
         
        return mailSender;
    }
    
}  
