package com.sxbang.friday.security.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RestAuthenticationAccessDeniedHandler  implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("utf-8");
      //  PrintWriter writer = response.getWriter();
       // System.out.println(accessDeniedException.getMessage()+","+response.getCharacterEncoding());
        //writer.println(accessDeniedException.getMessage());
        response.sendRedirect("/403.html");
    }

}
