package tag;

import model.OrderListBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class OrderInfoHandler extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {

        try {
            boolean hasUnavailable = false;
            OrderListBean listOrder = (OrderListBean) getJspContext().findAttribute("orderList");
            JspWriter out = getJspContext().getOut();
            for (int i = 0; i < listOrder.getOrderList().size(); i++) {
                if (listOrder.getOrderList(i).getIsAvailable() == 0) {
                    hasUnavailable = true;
                }
                out.println("<tr><TD align='center'>"
                        + listOrder.getOrderList(i).getDate() + "</TD>");
                out.println("<TD align='center'>"
                        + listOrder.getOrderList(i).getArticleName() + "</TD>");
                out.println("<TD align='center'>"
                        + listOrder.getOrderList(i).getArticleNum() + "</TD>");
                out.println("<TD align='center'>"
                        + listOrder.getOrderList(i).getPrice() + "</TD>");
                out.println("<TD align='center'>"
                        + (listOrder.getOrderList(i).getIsAvailable() == 0 ? "<i style=\"color: red\">out of stock</i>" : "normal") + "</TD></tr>");
            }
            if (hasUnavailable) {
                out.println("<i style=\"color: red\">ATTENTION: Some articles in your Order List are out of stock!</i>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspException(e.getMessage());
        }
    }

}
