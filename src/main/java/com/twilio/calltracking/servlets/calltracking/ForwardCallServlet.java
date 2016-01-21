package com.twilio.calltracking.servlets.calltracking;

import com.twilio.calltracking.models.Lead;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadRepository;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.WebAppServlet;
import com.twilio.sdk.verbs.Dial;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwardCallServlet extends WebAppServlet {

    private LeadSourceRepository leadSourceRepository;
    private LeadRepository leadRepository;

    public ForwardCallServlet() {
        this(new LeadSourceRepository(), new LeadRepository());
    }

    public ForwardCallServlet(LeadSourceRepository leadSourceRepository, LeadRepository leadRepository) {
        this.leadSourceRepository = leadSourceRepository;
        this.leadRepository = leadRepository;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String called = request.getParameter("called");
        String caller = request.getParameter("caller");
        String city = request.getParameter("fromCity");
        String state = request.getParameter("fromState");

        LeadSource ls = leadSourceRepository.findByIncomingNumberInternational(called);
        leadRepository.create(new Lead(caller,  city, state, ls));

        TwiMLResponse twiMLResponse = new TwiMLResponse();
        try {
            twiMLResponse.append(new Dial(ls.getForwardingNumber()));
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        respondTwiML(response, twiMLResponse);
    }
}
