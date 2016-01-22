<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<layout:extends name="base">
    <layout:put block="content" type="REPLACE">
        <div class="row">
            <div class="page-header">
                <h1>Edit lead source for ${leadSource.incomingNumberNational}</h1>
            </div>
            <div class="col-md-9">
                <form action="/leadsources-edit" class="form-horizontal" method="GET" role="form">
                    <div class="form-group">
                        <input type="hidden" id="id" name="id" type="text" value="${leadSource.id}"/>
                        <input type="hidden" id="incomingNumberNational" name="incomingNumberNational" type="text" value="${leadSource.incomingNumberNational}"/>
                        <input type="hidden" id="incomingNumberInternational" name="incomingNumberInternational" type="text" value="${leadSource.incomingNumberInternational}"/>

                        <label class="control-label col-md-2" for="name">Name</label>
                        <div class="col-md-10">
                            <input id="name" name="name" type="text" class="form-control" value="${leadSource.name}"/>
                            <p class="help-block">E.g. "Downtown billboard"</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-2" for="forwardingNumber">Forwarding Number</label>
                        <div class="col-md-10">
                            <input id="forwardingNumber" name="forwardingNumber" type="text" class="form-control" value="${leadSource.forwardingNumber}"/>
                            <p class="help-block">People who call this lead source will be connected with this phone number. Must include international prefix - e.g. +1 555 555 55555</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" value="Update lead source" class="btn btn-primary" />
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
