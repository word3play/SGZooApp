<%--
//
// (c) 2012 DS Data Systems UK Ltd, All rights reserved.
//
// DS Data Systems and KonaKart and their respective logos, are 
// trademarks of DS Data Systems UK Ltd. All rights reserved.
//
// The information in this document is free software; you can redistribute 
// it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This software is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
--%>
<%@include file="Taglibs.jsp" %>

<%@page import="java.math.BigDecimal"%>

<% com.konakart.al.KKAppEng kkEng = (com.konakart.al.KKAppEng) session.getAttribute("konakartKey");  %>
<% com.konakart.al.PunchOut poDetails = kkEng.getPunchoutDetails(); %>

<%if (poDetails != null){ %>
	<html>
		<head>	
		</head>
		<body onload="document.forms[0].submit();">
			<table border="0" width="100%" cellspacing="0" cellpadding="2" class="body-content-tab">
				<tr>
					<td><b><kk:msg  key="punchout.body.message"/></b></td>
				</tr>
			</table>
			<form  action="<%=poDetails.getReturnURL()%>" method="POST" target="<%=poDetails.getReturnTarget()%>">
				<% for (int i = 0; i < poDetails.getParmArray().length; i++){ %>
					<% com.konakart.appif.NameValueIf parm = poDetails.getParmArray()[i];%>
					<input type="hidden" name="<%=parm.getName()%>" value="<%=parm.getValue()%>">
				<% } %>
			</form>
		</body>
	</html>
<%}%>













