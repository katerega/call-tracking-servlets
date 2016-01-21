package com.twilio.calltracking.servlets;

import com.twilio.calltracking.lib.services.TwilioServices;
import com.twilio.calltracking.models.Lead;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadRepository;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.calltracking.ForwardCallServlet;
import com.twilio.sdk.resource.instance.IncomingPhoneNumber;
import com.twilio.calltracking.servlets.leadsources.*;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LeadSourceServletsTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    TwilioServices twilioServices;

    @Mock
    LeadSourceRepository leadSourceRepository;

    @Mock
    IncomingPhoneNumber incomingPhoneNumber;

    @Mock
    LeadSource leadSource;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);

        when(incomingPhoneNumber.getFriendlyName()).thenReturn("(415) 969-9064");
        when(incomingPhoneNumber.getPhoneNumber()).thenReturn("+14159699064");

        when(twilioServices.purchasePhoneNumber(anyString(), anyString())).thenReturn(incomingPhoneNumber);
        when(leadSourceRepository.create(Matchers.any(LeadSource.class))).thenReturn(leadSource);

        when(request.getParameter("phoneNumber")).thenReturn("+1 555 555 55555");
    }

    @Test
    public void postMethodToCreateServlet_CreatesALeadSource() throws Exception {

        CreateServlet servlet = new CreateServlet(leadSourceRepository,twilioServices);
        servlet.doPost(request, response);

        verify(leadSourceRepository).create(Matchers.any(LeadSource.class));
    }
}