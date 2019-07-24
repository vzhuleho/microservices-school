package com.kyriba.school.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author M-ABO
 */
@RestController
@RequestMapping("/service")
public class GatewayServiceController {

  @GetMapping(value = "/test")
  @ResponseBody
  public String test() {
    return "OK";
  }
}
