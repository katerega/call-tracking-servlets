package com.twilio.calltracking.servlets;

import com.twilio.calltracking.lib.utilities.Lazy;
import com.twilio.calltracking.lib.web.request.validators.RequestParametersValidator;
import com.twilio.sdk.verbs.TwiMLResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebAppServlet extends HttpServlet {

    protected Lazy<RequestParametersValidator> requestValidator;


    protected WebAppServlet() {
        requestValidator = new Lazy<>(RequestParametersValidator::new);
    }

    protected void respondTwiML(HttpServletResponse response, TwiMLResponse twiMLResponse)
        throws IOException {
        response.setContentType("text/xml");
        response.getWriter().write(twiMLResponse.toXML());
    }

    protected void respondJson(HttpServletResponse response, Object object) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);

        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    protected void respondContent(HttpServletResponse response, String content) throws IOException {
        response.getWriter().write(content);
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
