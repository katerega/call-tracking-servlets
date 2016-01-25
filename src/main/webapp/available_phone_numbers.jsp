<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>

<layout:extends name="base">
    <layout:put block="content" type="REPLACE">
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <div class="page-header">
                    <h1>Purchase a new phone number</h1>
                </div>
                <p>
                    The number you choose will be used to create a Lead Source. On the next page, you will set a name and forwarding number for this lead source.
                </p>

                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Phone Number</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <core:forEach var="number" items="${phoneNumbers}">
                         <tr>
                            <td>${number.friendlyName}</td>
                            <td>
                                <form action="/leadsources/create" method="POST" id="${number.phoneNumber}" style="display:none;">
                                    <input type="hidden" name="phoneNumber" value="${number.phoneNumber}">
                                </form>
                                <a href="javascript:document.getElementById('${number.phoneNumber}').submit()" class="btn btn-primary btn-xs" id="createLink">Purchase</a></li>
                            </td>
                        </tr>
                    </core:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </layout:put>
</layout:extends>
