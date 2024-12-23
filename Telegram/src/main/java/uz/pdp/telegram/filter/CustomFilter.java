package uz.pdp.telegram.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.telegram.entity.Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomFilter extends OncePerRequestFilter {
    List<String> openPages = new ArrayList<>(List.of(
            "/register",
            "/login"
    ));
    List<String> hasSession = new ArrayList<>(List.of(
            "/main1",
            "/main2",
            "/inputMessage"
    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (openPages.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        if (hasSession.contains(request.getRequestURI())) {
            HttpSession session = request.getSession();
            if (session.getAttribute("user") != null && session.getAttribute("user") instanceof Users) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.sendRedirect("/login");
                return;
            }
        }
        if ("/inputMessage".startsWith(request.getRequestURI()) && request.getMethod().equalsIgnoreCase("Get")) {
            response.sendRedirect("/login");
            return;
        }

        if ("/file/get".startsWith(request.getRequestURI())) {
            String audioId = request.getParameter("audioId");
            String jpgId = request.getParameter("jpgId");
            if (audioId == null && jpgId == null) {
                response.sendRedirect("/login");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
    }
}
