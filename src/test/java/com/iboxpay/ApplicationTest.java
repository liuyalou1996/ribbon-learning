package com.iboxpay;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.junit.Test;

import com.iboxpay.ribbon.ping.MyPing;
import com.iboxpay.ribbon.rule.MyRule;
import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.client.http.RestClient;

public class ApplicationTest {

  @Test
  public void loadBalancerTest() {
    BaseLoadBalancer balancer = new BaseLoadBalancer();
    balancer.setRule(new MyRule(balancer));
    balancer.addServer(new Server("localhost", 8080));
    balancer.addServer(new Server("localhost", 8081));
    for (int i = 0; i < 6; i++) {
      System.out.println(balancer.chooseServer());
    }
  }

  @SuppressWarnings({ "unchecked", "deprecation" })
  @Test
  public void defaultRuleTest() throws Exception {
    ConfigurationManager.getConfigInstance().setProperty("my-client.ribbon.listOfServers",
        "localhost:8080,localhost:8081");
    RestClient client = (RestClient) ClientFactory.getNamedClient("my-client");
    HttpRequest request = HttpRequest.newBuilder().uri("/userInfo").build();
    for (int i = 0; i < 6; i++) {
      HttpResponse response = client.executeWithLoadBalancer(request);
      System.err.println(response.getEntity(String.class));
    }
  }

  @SuppressWarnings("deprecation")
  @Test
  public void customizeRuleTest() throws Exception {
    Configuration config = ConfigurationManager.getConfigInstance();
    config.setProperty("my-client.ribbon.listOfServers", "localhost:8080,localhost:8081");
    // 配置规则处理类
    config.setProperty("my-client.ribbon.NFLoadBalancerRuleClassName", MyRule.class.getCanonicalName());
    // 获取Rest客户端
    RestClient client = (RestClient) ClientFactory.getNamedClient("my-client");
    // 创建请求
    HttpRequest request = HttpRequest.newBuilder().uri("/userInfo").build();
    for (int i = 0; i < 6; i++) {
      HttpResponse response = client.executeWithLoadBalancer(request);
      System.err.println(response.getEntity(String.class));
    }
  }

  @Test
  public void pingTest() throws InterruptedException {
    BaseLoadBalancer lb = new BaseLoadBalancer();
    List<Server> list = new ArrayList<>();
    list.add(new Server("localhost", 8080));
    list.add(new Server("localhost", 8081));
    lb.addServers(list);
    // 设置ping实现类
    lb.setPing(new PingUrl());
    // 设置ping时间间隔为2秒
    lb.setPingInterval(2);
    Thread.sleep(6000);
    lb.getAllServers().forEach(server -> System.err.println(server.getHostPort() + " 状态：" + server.isAlive()));
  }

  @SuppressWarnings("deprecation")
  @Test
  public void customizePingTest() throws InterruptedException {
    Configuration config = ConfigurationManager.getConfigInstance();
    config.setProperty("my-client.ribbon.listOfServers", "localhost:8080,localhost:8081");
    // 设置ping实现类
    config.setProperty("my-client.ribbon.NFLoadBalancerPingClassName", MyPing.class.getCanonicalName());
    // 配置Ping时间间隔
    config.setProperty("my-client.ribbon.NFLoadBalancerPingInterval", 2);
    RestClient client = (RestClient) ClientFactory.getNamedClient("my-client");
    Thread.sleep(6000);
    client.getLoadBalancer().getAllServers()
        .forEach(server -> System.err.println(server.getHostPort() + " 状态：" + server.isAlive()));
  }

}
