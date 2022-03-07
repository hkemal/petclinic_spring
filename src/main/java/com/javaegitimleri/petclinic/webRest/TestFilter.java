package com.javaegitimleri.petclinic.webRest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "TestFilter", urlPatterns = "/TestServlet")
public class TestFilter implements Filter {

    public TestFilter() {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.getWriter().write("before...");
        chain.doFilter(request, response);
        response.getWriter().write("after...");
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
