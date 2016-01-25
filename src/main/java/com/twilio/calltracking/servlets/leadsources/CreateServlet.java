package com.twilio.calltracking.servlets.leadsources;

import com.twilio.calltracking.lib.Config;
import com.twilio.calltracking.lib.services.TwilioServices;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.WebAppServlet;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.IncomingPhoneNumber;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateServlet extends WebAppServlet {

    private LeadSourceRepository leadSourceRepository;
    private TwilioServices twilioServices;

    public CreateServlet() {
        this(new LeadSourceRepository(), new TwilioServices() );
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
            try {
                twimlApplicationSid = twilioServices.getApplicationSid();
            } catch (TwilioRestException e) {
                e.printStackTrace();
            }
        }

        IncomingPhoneNumber twilioNumber = twilioServices.purchasePhoneNumber(phoneNumber,twimlApplicationSid);

        LeadSource leadSource = new LeadSource(twilioNumber.getFriendlyName(), twilioNumber.getPhoneNumber());
        leadSource = leadSourceRepository.create(leadSource);

        response.sendRedirect(String.format("/leadsources/edit?id=%s", leadSource.getId()));
    }
}
