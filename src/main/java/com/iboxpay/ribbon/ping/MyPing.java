package com.iboxpay.ribbon.ping;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

public class MyPing implements IPing {

  @Override
  public boolean isAlive(Server server) {
    return true;
  }

}
