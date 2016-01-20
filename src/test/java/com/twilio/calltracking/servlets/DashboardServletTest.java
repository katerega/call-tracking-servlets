package com.twilio.calltracking.servlets;

import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.phonenumbers.AvailableServlet;
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

public class DashboardServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    LeadSourceRepository leadSourceRepository;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMethod_ReturnsListOfPhoneNumbers() throws Exception {

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        List<LeadSource> leadSources = new ArrayList<>();
        when(leadSourceRepository.findAll()).thenReturn(leadSources);

        DashboardServlet servlet = new DashboardServlet(leadSourceRepository);
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/dashboard.jsp");
        verify(request).setAttribute("leadSources", leadSources);
    }


}