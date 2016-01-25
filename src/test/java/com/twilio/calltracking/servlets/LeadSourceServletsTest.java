package com.twilio.calltracking.servlets;

import com.twilio.calltracking.lib.services.TwilioServices;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadSourceRepository;
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

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LeadSourceServletsTest extends BaseTwilioServletTest{
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
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        when(incomingPhoneNumber.getFriendlyName()).thenReturn("(415) 969-9064");
        when(incomingPhoneNumber.getPhoneNumber()).thenReturn("+14159699064");

        when(twilioServices.purchasePhoneNumber(anyString(), anyString())).thenReturn(incomingPhoneNumber);
        when(leadSourceRepository.find(anyLong())).thenReturn(leadSource);
        when(leadSourceRepository.create(Matchers.any(LeadSource.class))).thenReturn(leadSource);
        when(leadSourceRepository.update(Matchers.any(LeadSource.class))).thenReturn(leadSource);
    }

    @Test
    public void postMethodToCreateServlet_CreatesALeadSource() throws Exception {

        when(request.getParameter("phoneNumber")).thenReturn("+14159699064");

        CreateServlet servlet = new CreateServlet(leadSourceRepository,twilioServices);
        servlet.doPost(request, response);

        verify(leadSourceRepository).create(Matchers.any(LeadSource.class));
    }

    @Test
    public void postMethodToCreateServlet_RedirectsToEditViewOnSuccess() throws Exception {

        when(request.getParameter("phoneNumber")).thenReturn("+14159699064");

        CreateServlet servlet = new CreateServlet(leadSourceRepository,twilioServices);
        servlet.doPost(request, response);

        verifyRedirectTo(response, "leadsources/edit?id=0");
    }

    @Test
    public void postMethodToEditServlet_EditsALeadSource() throws Exception {

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Name");
        when(request.getParameter("incomingNumberNational")).thenReturn("4159699064");
        when(request.getParameter("incomingNumberInternational")).thenReturn("+14159699064");
        when(request.getParameter("forwardingNumber")).thenReturn("+5567788000");

        EditServlet servlet = new EditServlet(leadSourceRepository);
        servlet.doPost(request, response);

        verify(leadSourceRepository).update(Matchers.any(LeadSource.class));
    }

    @Test
    public void postMethodToEditServlet_RedirectsToDashboardOnSuccess() throws Exception {

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Name");
        when(request.getParameter("incomingNumberNational")).thenReturn("4159699064");
        when(request.getParameter("incomingNumberInternational")).thenReturn("+14159699064");
        when(request.getParameter("forwardingNumber")).thenReturn("+5567788000");

        EditServlet servlet = new EditServlet(leadSourceRepository);
        servlet.doPost(request, response);

        verifyRedirectTo(response, "dashboard");
    }
}
