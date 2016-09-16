package com.twilio.calltracking.servlets.leadsources;

import com.twilio.calltracking.lib.web.request.validators.RequestParametersValidator;
import com.twilio.calltracking.models.LeadSource;
import com.twilio.calltracking.repositories.LeadSourceRepository;
import com.twilio.calltracking.servlets.WebAppServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class EditServlet extends WebAppServlet {

    private LeadSourceRepository leadSourceRepository;


    @SuppressWarnings("unused")
    public EditServlet() {
        this(new LeadSourceRepository());
    }

    public EditServlet(LeadSourceRepository leadSourceRepository) {
        this.leadSourceRepository = leadSourceRepository;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        if (id == null || Objects.equals(id, "")) {
            response.sendError(400, "Missed resource params.");
        } else {
            LeadSource leadSource = leadSourceRepository.find(Long.valueOf(id));

            if (leadSource == null) {
                response.sendError(404, "Resource not found");
            } else {
                request.setAttribute("leadSource", leadSource);
                request.getRequestDispatcher("/lead-source-edit.jsp").forward(request, response);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doPost(request, response);

        String id = null;
        String name = null;
        String incomingNumberNational = null;
        String incomingNumberInternational = null;
        String forwardingNumber = null;
        LeadSource leadSource = null;


        if (isValidRequest()) {

            id = request.getParameter("id");
            name = request.getParameter("name");
            incomingNumberNational = request.getParameter("incomingNumberNational");
            incomingNumberInternational = request.getParameter("incomingNumberInternational");
            forwardingNumber = request.getParameter("forwardingNumber");

            leadSource = leadSourceRepository.find(Long.valueOf(id));

            if (leadSource == null) {
                response.sendError(404, "Resource not found");
            } else {
                leadSource.setName(name);
                leadSource.setIncomingNumberNational(incomingNumberNational);
                leadSource.setIncomingNumberInternational(incomingNumberInternational);
                leadSource.setForwardingNumber(forwardingNumber);

                leadSourceRepository.update(leadSource);

                response.sendRedirect("/dashboard");
            }
        }

        preserveStatusRequest(
                request,
                id,
                name,
                incomingNumberNational,
                incomingNumberInternational,
                forwardingNumber,
                leadSource);

        request.getRequestDispatcher("/lead-source-edit.jsp").forward(request, response);
    }

    @Override
    protected boolean isValidRequest(RequestParametersValidator validator) {

        return validator.validatePresence(
                "id",
                "name",
                "incomingNumberNational",
                "incomingNumberInternational",
                "forwardingNumber");
    }

    private void preserveStatusRequest(
            HttpServletRequest request,
            String id,
            String name,
            String incomingNumberNational,
            String incomingNumberInternational,
            String forwardingNumber,
            LeadSource leadSource) {
        request.setAttribute("id", id);
        request.setAttribute("leadSource", name);
        request.setAttribute("incomingNumberNational", incomingNumberNational);
        request.setAttribute("incomingNumberInternational", incomingNumberInternational);
        request.setAttribute("forwardingNumber", forwardingNumber);
        request.setAttribute("leadSource", leadSource);
    }
}
