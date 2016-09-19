package com.twilio.calltracking.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.twilio.calltracking.lib.utilities.Lazy;
import com.twilio.calltracking.lib.web.request.validators.RequestParametersValidator;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("checkstyle:VisibilityModifierCheck")
public class WebAppServlet extends HttpServlet {

    private Lazy<RequestParametersValidator> requestValidator;

    protected WebAppServlet() {
        requestValidator = new Lazy<>(RequestParametersValidator::new);
    }

    protected void respondTwiML(HttpServletResponse response, VoiceResponse voiceResponse)
        throws IOException {
        response.setContentType("text/xml");
        try {
            response.getWriter().write(voiceResponse.toXml());
        } catch (TwiMLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void respondJson(HttpServletResponse response, Object object) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);

        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        requestValidator.get().setRequest(request);
    }


    protected boolean isValidRequest() {
        return isValidRequest(requestValidator.get());
    }

    protected boolean isValidRequest(RequestParametersValidator validator) {
        return true;
    }
}
