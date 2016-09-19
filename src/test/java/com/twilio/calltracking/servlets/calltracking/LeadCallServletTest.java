package com.twilio.calltracking.servlets.calltracking;

import com.twilio.calltracking.lib.services.TwilioServices;
import com.twilio.calltracking.models.Lead;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadRepository;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.BaseTwilioServletTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LeadCallServletTest extends BaseTwilioServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private TwilioServices twilioServices;

    @Mock
    private LeadRepository leadRepository;

    @Mock
    private LeadSourceRepository leadSourceRepository;

    @Before public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);

        LeadSource leadSource = new LeadSource("test", "", "");
        Lead lead = new Lead("+123456789", "City", "State", leadSource);
        when(leadSourceRepository.findByIncomingNumberInternational(anyString()))
            .thenReturn(leadSource);
        when(leadRepository.create(Matchers.any(Lead.class))).thenReturn(lead);

        when(request.getParameter("Called")).thenReturn("+88888888");
        when(request.getParameter("Caller")).thenReturn("+99999999");
        when(request.getParameter("FromCity")).thenReturn("City");
        when(request.getParameter("FromState")).thenReturn("State");

    }

    @Test public void postMethodFindsTheLeadSourceAndCreatesALeadForTheGivenCalled()
        throws Exception {

        LeadCallServlet servlet = new LeadCallServlet(leadSourceRepository, leadRepository);
        servlet.doPost(request, response);

        verify(leadRepository).create(Matchers.any(Lead.class));
    }
}


