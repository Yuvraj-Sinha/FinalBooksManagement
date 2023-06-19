/*
 * package com.trueid.gateway;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpSession;
 * 
 * import org.springframework.security.core.context.SecurityContextHolder;
 * import org.springframework.web.bind.annotation.GetMapping;
 * 
 * @org.springframework.web.bind.annotation.RestController
 * 
 * public class RestController {
 * 
 * @GetMapping("/after") public String getMsg(HttpServletRequest req) {
 * Authentication authentication = (Authentication) req.getUserPrincipal();
 * System.out.println(authentication.getName()); return "logout"; }
 * 
 * @GetMapping public String sessioncreated(HttpServletRequest req) {
 * Authentication authentication =
 * SecurityContextHolder.getContext().getAuthentication(); HttpSession session =
 * req.getSession(); session.setAttribute("authentication", authentication);
 * return "done";
 * 
 * } }
 */