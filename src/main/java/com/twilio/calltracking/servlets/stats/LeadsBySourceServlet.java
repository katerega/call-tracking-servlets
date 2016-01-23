package com.twilio.calltracking.servlets.stats;

import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.WebAppServlet;
import javafx.beans.binding.ObjectExpression;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LeadsBySourceServlet extends WebAppServlet{
    private LeadSourceRepository leadSourceRepository;

    public LeadsBySourceServlet() {
        this(new LeadSourceRepository());
    }

    public LeadsBySourceServlet(LeadSourceRepository leadSourceRepository) {
        this.leadSourceRepository = leadSourceRepository;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<Object> leadsByLeadSource = leadSourceRepository.findLeadsByLeadSource();
        respondJson(response, leadsByLeadSource);
    }

}
