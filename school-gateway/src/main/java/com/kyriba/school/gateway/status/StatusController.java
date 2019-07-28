package com.kyriba.school.gateway.status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/status")
public class StatusController {

  @GetMapping
  public Map<String, Object> get() {
    return Collections.singletonMap("status", "OK");
  }

}
