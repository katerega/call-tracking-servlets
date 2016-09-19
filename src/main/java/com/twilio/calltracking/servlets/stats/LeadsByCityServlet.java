package com.twilio.calltracking.servlets.stats;

import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.WebAppServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LeadsByCityServlet extends WebAppServlet {

    private LeadSourceRepository leadSourceRepository;

    @SuppressWarnings("unused")
    public LeadsByCityServlet() {
        this(new LeadSourceRepository());
    }

    public LeadsByCityServlet(LeadSourceRepository leadSourceRepository) {
        this.leadSourceRepository = leadSourceRepository;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Object> leadsByLeadSource = leadSourceRepository.findLeadsByCity();
        respondJson(response, leadsByLeadSource);
    }
}
