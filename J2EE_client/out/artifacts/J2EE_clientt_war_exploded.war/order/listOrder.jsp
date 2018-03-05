<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="order" uri="/WEB-INF/tlds/orderInfo.tld" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Order List</title>
</head>

<BODY>
<order:checkSession response="<%=response%>"/>

<img src='<%=request.getContextPath() + "/image/top.jpg"%>' style="width: 30%; float: left; margin: 2% 5%">
<p>Welcome, <i>Customer <%=request.getAttribute("login")%>
</i> !</p>

<jsp:useBean id="orderList"
             type="model.OrderListBean"
             scope="session"/>

<table width="50%" border="0" cellpadding="0" cellspacing="1">
    <TBODY>
    <TR>
        <TH width="20%">DATE</TH>
        <TH width="20%">ARTICLE</TH>
        <TH width="20%">NUMBER</TH>
        <TH width="20%">PRICE</TH>
        <TH width="20%">STATE</TH>
    </TR>
    <order:orderInfo/>
    </TBODY>
</table>

<%
    int pageNow = (Integer) session.getAttribute("pageNow");
    int pageCount = (Integer) session.getAttribute("pageCount");
    if (pageNow != 1) {
%>
<a href='<%=response.encodeURL(request.getContextPath())%>/ShowOrderServlet?pageNow=<%=pageNow - 1%>'>last page</a>
<%}%>
<%if (pageNow != pageCount) {%>
<a href='<%=response.encodeURL(request.getContextPath())%>/ShowOrderServlet?pageNow=<%=pageNow + 1%>'>next page</a>
<%}%>

&nbsp;&nbsp;current page: <%=pageNow%> / total page: <%=pageCount%>
<br>

<jsp:include page="numberStatistics.jsp"/>
<form method='GET' action='<%=response.encodeURL(request.getContextPath())%>/Login'>
    <input type='submit' name='logout' value='logout'>
</form>

</BODY>
</html>


