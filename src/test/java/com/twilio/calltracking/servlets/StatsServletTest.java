package com.twilio.calltracking.servlets;

import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.repositories.QueryHelper;
import com.twilio.calltracking.servlets.stats.LeadsByCityServlet;
import com.twilio.calltracking.servlets.stats.LeadsBySourceServlet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class StatsServletTest {

    @Mock HttpServletRequest request;

    @Mock HttpServletResponse response;

    @Mock RequestDispatcher requestDispatcher;

    @Mock LeadSourceRepository leadSourceRepository;

    @Before public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        List<Object[]> leadsByCities = new ArrayList<>();
        leadsByCities.add(new Object[] {"FL", Long.valueOf(1)});
        leadsByCities.add(new Object[] {"CA", Long.valueOf(1)});
        leadsByCities.add(new Object[] {"NY", Long.valueOf(2)});

        List<Object[]> leadsBySources = new ArrayList<>();
        leadsBySources.add(new Object[] {"second", Long.valueOf(2)});
        leadsBySources.add(new Object[] {"first", Long.valueOf(2)});

        List<Object> resultsbyCities = QueryHelper.mapResults(leadsByCities);
        List<Object> resultsbySources = QueryHelper.mapResults(leadsBySources);

        when(leadSourceRepository.findLeadsByCity()).thenReturn(resultsbyCities);
        when(leadSourceRepository.findLeadsByLeadSource()).thenReturn(resultsbySources);
    }

    @Test public void getMethodToLeadsByClientServlet_ReturnsLeadsGroupedByCity() throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);

        LeadsByCityServlet servlet = new LeadsByCityServlet(leadSourceRepository);
        servlet.doGet(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        assertEquals(content, "[ {\n" +
            "  \"label\" : \"FL\",\n" +
            "  \"value\" : 1\n" +
            "}, {\n" +
            "  \"label\" : \"CA\",\n" +
            "  \"value\" : 1\n" +
            "}, {\n" +
            "  \"label\" : \"NY\",\n" +
            "  \"value\" : 2\n" +
            "} ]");
    }

    @Test public void getMethodToLeadsBySourcetServlet_ReturnsLeadsGroupedBySource()
        throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);

        LeadsBySourceServlet servlet = new LeadsBySourceServlet(leadSourceRepository);
        servlet.doGet(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        assertEquals(content, "[ {\n" +
            "  \"label\" : \"second\",\n" +
            "  \"value\" : 2\n" +
            "}, {\n" +
            "  \"label\" : \"first\",\n" +
            "  \"value\" : 2\n" +
            "} ]");
    }
}
