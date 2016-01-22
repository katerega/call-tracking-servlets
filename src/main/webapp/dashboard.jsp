<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<layout:extends name="base">
    <layout:put block="content" type="REPLACE">

        <h1>Call Tracking with Twilio</h1>
        <p>This app shows you how to implement a sample call tracking application with Twilio. Add a new phone number below to get started.</p>
        <div class="row">
            <div class="col-md-5">
                <h2>Add a new number</h2>
                <p>Create a new lead source by purchasing a new phone number. Area code is optional.</p>

                <form action="/phone-numbers" class="form-inline" method="GET" role="form">
                    <div class="form-group">
                        <label class="sr-only control-label" for="areaCode">Area code</label>
                        <input id="areaCode" name="areaCode" type="text" class="form-control" value="415" maxlength="3"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Search</button>
                </form>

                <h2>Add a new number</h2>
                <p>Create a new lead source by purchasing a new phone number. Area code is optional.</p>

                <core:choose>
                    <core:when test="${fn:length(leadSources) > 0 }">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Incoming Number</th>
                                <th>Forwarding Number</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <core:forEach var="leadSource" items="${leadSources}">
                                <tr>
                                    <td>${leadSource.name}</td>
                                    <td>${leadSource.incomingNumberNational}</td>
                                    <td>${leadSource.forwardingNumber}</td>
                                    <td>
                                        <a href="/leadsources/edit?id=${leadsource.id}" class="btn btn-default btn-xs">Edit</a>
                                    </td>
                                </tr>
                            </core:forEach>
                            </tbody>
                        </table>
                    </core:when>
                    <core:otherwise>
                        <p><b>Add a new number to populate this list</b></p>
                    </core:otherwise>
                </core:choose>
            </div>
            <div class="col-md-7">
                <h2>Charts</h2>
                <p>The latest statistics about how the lead sources are performing.</p>

                <div class="row">
                    <div class="col-md-6">
                        <h3>Calls by lead source</h3>
                        <p>The number of incoming calls each lead source has received.</p>
                        <canvas id="leads-by-source"></canvas>
                    </div>

                    <div class="col-md-6">
                        <h3>Calls by city</h3>
                        <p>The number of calls from different cities, based on Twilio call data</p>
                        <canvas id="leads-by-city"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </layout:put>
    <layout:put block="scripts" type="REPLACE">
        <script src="scripts/chart.js"></script>
        <script src="scripts/call-tracking.js"></script>
    </layout:put>
</layout:extends>
