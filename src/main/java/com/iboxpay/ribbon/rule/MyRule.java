package com.iboxpay.ribbon.rule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

public class MyRule implements IRule {

  private ILoadBalancer balancer;

  public MyRule() {
  }

  public MyRule(ILoadBalancer balancer) {
    this.balancer = balancer;
  }

  @Override
  public Server choose(Object key) {
    return balancer.getAllServers().get(0);
  }

  @Override
  public void setLoadBalancer(ILoadBalancer lb) {
    this.balancer = lb;
  }

  @Override
  public ILoadBalancer getLoadBalancer() {
    return balancer;
  }

}
