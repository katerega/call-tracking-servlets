package com.twilio.calltracking.servlets.calltracking;

import com.twilio.calltracking.lib.services.TwilioServices;
import com.twilio.calltracking.models.Lead;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadRepository;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.BaseTwilioServletTest;
import org.hamcrest.CoreMatchers;
import org.jdom2.Document;
import org.jdom2.Element;
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

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ForwardCallServletTest extends BaseTwilioServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    TwilioServices twilioServices;

    @Mock
    LeadRepository leadRepository;

    @Mock
    LeadSourceRepository leadSourceRepository;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);

        String forwardingNumber = "+55555";
        LeadSource leadSource = new LeadSource("test", "", "", forwardingNumber);
        Lead lead = new Lead("+123456789", "City", "State", leadSource);
        when(leadSourceRepository.findByIncomingNumberInternational(anyString())).thenReturn(leadSource);
        when(leadRepository.create(Matchers.any(Lead.class))).thenReturn(lead);

        when(request.getParameter("called")).thenReturn("+88888888");
        when(request.getParameter("caller")).thenReturn("+99999999");
        when(request.getParameter("fromCity")).thenReturn("City");
        when(request.getParameter("fromState")).thenReturn("State");

    }

    @Test
    public void postMethod_findsTheLeadSourceAndCreatesALeadForTheGivenCalled() throws Exception {

        ForwardCallServlet servlet = new ForwardCallServlet(leadSourceRepository, leadRepository);
        servlet.doPost(request, response);

        verify(leadRepository).create(Matchers.any(Lead.class));
    }
}


