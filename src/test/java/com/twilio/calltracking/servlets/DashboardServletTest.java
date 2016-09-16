package com.twilio.calltracking.servlets;

import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadSourceRepository;
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
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private LeadSourceRepository leadSourceRepository;

    @Before public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void getMethodReturnsListOfPhoneNumbers() throws Exception {

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        List<LeadSource> leadSources = new ArrayList<>();
        when(leadSourceRepository.findAll()).thenReturn(leadSources);

        DashboardServlet servlet = new DashboardServlet(leadSourceRepository);
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/dashboard.jsp");
        verify(request).setAttribute("leadSources", leadSources);
    }


}
