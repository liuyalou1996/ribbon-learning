package com.iboxpay;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Application.class);
  }

  public static void main(String[] args) {
    System.out.println("请输入端口号：");
    Scanner scanner = new Scanner(System.in);
    int port = scanner.nextInt();
    new SpringApplicationBuilder(Application.class).properties("server.port=" + port).run(args);
  }

}
