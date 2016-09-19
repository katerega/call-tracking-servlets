package com.twilio.calltracking.servlets;

import com.twilio.calltracking.repositories.LeadSourceRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DashboardServlet extends WebAppServlet {

    private LeadSourceRepository leadSourceRepository;

    @SuppressWarnings("unused")
    public DashboardServlet() {
        this(new LeadSourceRepository());
    }

    public DashboardServlet(LeadSourceRepository leadSourceRepository) {
        this.leadSourceRepository = leadSourceRepository;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setAttribute("leadSources", leadSourceRepository.findAll());
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
