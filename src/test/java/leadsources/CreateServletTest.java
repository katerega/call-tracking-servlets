package leadsources;

import com.twilio.calltracking.lib.services.TwilioServices;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.BaseTwilioServletTest;
import com.twilio.calltracking.servlets.leadsources.CreateServlet;
import com.twilio.calltracking.servlets.leadsources.EditServlet;
import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;
import com.twilio.type.PhoneNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Local.class)
public class CreateServletTest extends BaseTwilioServletTest {

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

    Local incomingPhoneNumber;

    @Mock
    LeadSource leadSource;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        incomingPhoneNumber = PowerMockito.mock(Local.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        when(incomingPhoneNumber.getFriendlyName()).thenReturn("(415) 969-9064");
        when(incomingPhoneNumber.getPhoneNumber()).thenReturn(new PhoneNumber("+14159699064"));

        when(twilioServices.purchasePhoneNumber(anyString(), anyString()))
                .thenReturn(incomingPhoneNumber);

        when(leadSourceRepository.find(anyLong())).thenReturn(leadSource);
        when(leadSourceRepository.create(any(LeadSource.class))).thenReturn(leadSource);
        when(leadSourceRepository.update(any(LeadSource.class))).thenReturn(leadSource);
    }

    @Test
    public void postMethodToCreateServlet_CreatesALeadSource() throws Exception {
        // Given
        when(request.getParameter("phoneNumber")).thenReturn("+14159699064");

        // When
        CreateServlet servlet = new CreateServlet(leadSourceRepository, twilioServices);
        servlet.doPost(request, response);

        // Then
        verify(leadSourceRepository).create(any(LeadSource.class));
    }

    @Test
    public void postMethodToCreateServlet_RedirectsToEditViewOnSuccess() throws Exception {

        when(request.getParameter("phoneNumber")).thenReturn("+14159699064");

        CreateServlet servlet = new CreateServlet(leadSourceRepository, twilioServices);
        servlet.doPost(request, response);

        verifyRedirectTo(response, "leadsources/edit?id=0");
    }
}
