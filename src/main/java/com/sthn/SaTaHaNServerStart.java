package com.sthn;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan
public class SaTaHaNServerStart {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SaTaHaNServerStart.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        CamelSpringBootApplicationController configurableApplicationContextBean =
                ctx.getBean(CamelSpringBootApplicationController.class);
        //configurableApplicationContextBean.blockMainThread();
    }
}
