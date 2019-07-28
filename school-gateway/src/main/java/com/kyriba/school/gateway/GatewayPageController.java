package com.kyriba.school.gateway;

import com.kyriba.school.gateway.user.SystemUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author M-ABO
 */
@Controller
public class GatewayPageController {

  @RequestMapping("/")
  public String getIndexPage() {
    return SystemUser.isAuthorized() ? "index" : "public/index";
  }

  @RequestMapping("/login")
  public String getLoginPage(HttpServletRequest request) {
    return "redirect:/";
  }

  @RequestMapping("/logout")
  public String getLogoutPage(HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }

  @RequestMapping("/error/403")
  public String getErrorPage403() {
    return "error/403";
  }

  @RequestMapping("/error/404")
  public String getErrorPage404() {
    return "error/404";
  }

  @RequestMapping("/error/500")
  public String getErrorPage500() {
    return "error/500";
  }
}
