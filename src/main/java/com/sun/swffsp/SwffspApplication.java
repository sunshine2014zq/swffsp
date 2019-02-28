package com.sun.swffsp;

import com.sun.swffsp.jpa.base.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class SwffspApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwffspApplication.class, args);
    }

}
