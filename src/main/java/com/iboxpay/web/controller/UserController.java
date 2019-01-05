package com.iboxpay.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @RequestMapping("/userInfo")
  public Map<String, Object> userInfo(HttpServletRequest request) {
    Map<String, Object> userInfo = new HashMap<String, Object>();
    userInfo.put("name", "lyl");
    userInfo.put("age", 20);
    userInfo.put("url", request.getRequestURL().toString());
    return userInfo;
  }

  @RequestMapping("/")
  @ResponseBody
  public Map<String, Object> healthCheck() {
    Map<String, Object> message = new HashMap<String, Object>();
    message.put("returnCode", "0");
    message.put("msg", "ok");
    return message;
  }
}
