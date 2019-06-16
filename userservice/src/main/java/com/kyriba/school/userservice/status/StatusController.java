package com.kyriba.school.userservice.status;

import java.util.Collections;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

  @GetMapping
  public Map<String, Object> get() {
    return Collections.singletonMap("status", "OK");
  }

}
