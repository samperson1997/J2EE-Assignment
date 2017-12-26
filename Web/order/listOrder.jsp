<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="order" uri="/WEB-INF/tlds/orderInfo.tld" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Order List</title>
</head>

<BODY>
<img src='<%=request.getContextPath() + "/image/top.jpg"%>' style="width: 30%; float: left; margin: 2% 5%">
<p>Welcome, <i>Customer <%=request.getAttribute("login")%>
</i> !</p>

<jsp:useBean id="orderList"
             type="model.OrderListBean"
             scope="session"/>
<jsp:useBean id="item" class="model.Order"/>

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

<jsp:include page="numberStatistics.jsp" />
<form method='GET' action='<%=response.encodeURL(request.getContextPath())%>/Login'>
    <input type='submit' name='logout' value='logout'>
</form>

</BODY>
</html>


