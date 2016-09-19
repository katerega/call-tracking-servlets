package com.twilio.calltracking.servlets.phonenumbers;

import com.twilio.calltracking.lib.services.TwilioServices;
import com.twilio.rest.api.v2010.account.availablephonenumbercountry.Local;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AvailableServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private TwilioServices twilioServices;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMethodReturnsListOfPhoneNumbers() throws Exception {

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("areaCode")).thenReturn("412");

        List<Local> phoneNumbers = new ArrayList<>();
        when(twilioServices.searchPhoneNumbers(anyString())).thenReturn(phoneNumbers);

        AvailableServlet servlet = new AvailableServlet(twilioServices);
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/available_phone_numbers.jsp");
        verify(request).setAttribute("phoneNumbers", phoneNumbers);
    }
}
