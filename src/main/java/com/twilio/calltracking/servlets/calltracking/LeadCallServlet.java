package com.twilio.calltracking.servlets.calltracking;

import com.twilio.calltracking.models.Lead;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadRepository;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.WebAppServlet;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Number;
import com.twilio.twiml.VoiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LeadCallServlet extends WebAppServlet {

    private LeadSourceRepository leadSourceRepository;
    private LeadRepository leadRepository;

    @SuppressWarnings("unused")
    public LeadCallServlet() {
        this(new LeadSourceRepository(), new LeadRepository());
    }

    public LeadCallServlet(LeadSourceRepository leadSourceRepository,
        LeadRepository leadRepository) {
        this.leadSourceRepository = leadSourceRepository;
        this.leadRepository = leadRepository;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String called = request.getParameter("Called");
        String caller = request.getParameter("Caller");
        String city = request.getParameter("FromCity");
        String state = request.getParameter("FromState");

        LeadSource ls = leadSourceRepository.findByIncomingNumberInternational(called);
        leadRepository.create(new Lead(caller,  city, state, ls));

        Number number = new Number.Builder(ls.getForwardingNumber()).build();
        VoiceResponse voiceResponse = new VoiceResponse.Builder()
                .dial(new Dial.Builder().number(number).build())
                .build();

        respondTwiML(response, voiceResponse);
    }
}
