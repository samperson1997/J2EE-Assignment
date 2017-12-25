<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="stock" uri="/WEB-INF/tlds/orderInfo.tld" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Watch List</title>
</head>

<BODY>
<table width="650" border="0">
    <tr>
        <td width="650" height="80"
            background="<%=request.getContextPath() + "/image/top.jpg"%>">&nbsp;
        </td>
    </tr>
</table>
<H1>Online Stock.</H1>
<H2>
    <BR>
    <jsp:useBean id="listOrder"
                 type="model.OrderListBean"
                 scope="session"></jsp:useBean>
    <jsp:useBean id="item" class="model.Order"
                 scope="page"></jsp:useBean>


    There are all Stocks.
</H2>
<H4>
    <BR>
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
        <TBODY>
        <TR>
            <TH width="20%">id</TH>
            <TH width="20%">companyName</TH>
            <TH width="20%">type</TH>
            <TH width="20%">price</TH>
            <TH width="20%">date</TH>
        </TR>
        <order:orderInfo/>
        </TBODY>
    </TABLE>
</H4>
</BODY>
</html>


