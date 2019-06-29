package com.kyriba.schoolclassservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class SchoolClassServiceApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(SchoolClassServiceApplication.class, args);
  }
}
