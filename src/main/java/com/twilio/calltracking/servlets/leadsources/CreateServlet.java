package com.twilio.calltracking.servlets.leadsources;

import com.twilio.calltracking.lib.Config;
import com.twilio.calltracking.lib.services.TwilioServices;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.WebAppServlet;
import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class CreateServlet extends WebAppServlet {

    private LeadSourceRepository leadSourceRepository;
    private TwilioServices twilioServices;

    @SuppressWarnings("unused")
    public CreateServlet() {
        this(new LeadSourceRepository(), new TwilioServices());
    }

    public CreateServlet(LeadSourceRepository leadSourceRepository, TwilioServices twilioServices) {
        this.leadSourceRepository = leadSourceRepository;
        this.twilioServices = twilioServices;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phoneNumber = request.getParameter("phoneNumber");

        String twimlApplicationSid = Config.getTwimlApplicationSid();
        if (Objects.equals(twimlApplicationSid, "") || (twimlApplicationSid == null)) {
            twimlApplicationSid = twilioServices.getApplicationSid();
        }

        Local twilioNumber = twilioServices.purchasePhoneNumber(phoneNumber, twimlApplicationSid);

        LeadSource leadSource = leadSourceRepository.create(new LeadSource(
                twilioNumber.getFriendlyName(),
                twilioNumber.getPhoneNumber().toString()));

        response.sendRedirect(String.format("/leadsources/edit?id=%s", leadSource.getId()));
    }
}
