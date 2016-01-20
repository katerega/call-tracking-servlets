package com.twilio.calltracking.servlets.phonenumbers;

import com.twilio.calltracking.lib.services.TwilioServices;
import com.twilio.sdk.resource.instance.AvailablePhoneNumber;
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
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    TwilioServices twilioServices;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMethod_ReturnsListOfPhoneNumbers() throws Exception {

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        List<AvailablePhoneNumber> phoneNumbers = new ArrayList<>();
        when(twilioServices.searchPhoneNumbers(anyString())).thenReturn(phoneNumbers);

        AvailableServlet servlet = new AvailableServlet(twilioServices);
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/available_phone_numbers.jsp");
        verify(request).setAttribute("phoneNumbers", phoneNumbers);
    }
}