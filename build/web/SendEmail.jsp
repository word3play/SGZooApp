<%-- 
    Document   : SendEmail
    Created on : Mar 11, 2016, 12:55:02 PM
    Author     : Gu Yanlong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% com.konakart.al.KKAppEng kkEng = (com.konakart.al.KKAppEng) session.getAttribute("konakartKey");  %>

<s:set scope="request" var="contactUsContent" value="contactUsContent"/> 
<%String contactUsContent = (String) (request.getAttribute("contactUsContent"));%>
<h1 id="page-title"><kk:msg  key="header.contact.us"/></h1>			
<div class="content-area rounded-corners">
    <div id="about-us">
        <h3>Thank you!!!</h3>
        <div class="form-buttons-wide">
            <a href="Welcome.action" id="continue-button" class="button small-rounded-corners"><span><kk:msg  key="common.close"/></span></a>
        </div>
    </div>
</div>
