package tag;

import model.OrderListBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class OrderInfoHandler extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {

        try {
            OrderListBean listStock = (OrderListBean) getJspContext().findAttribute("listStock");
            JspWriter out = getJspContext().getOut();
            for (int i = 0; i < listStock.getOrderList().size(); i++) {
                out.println("<tr><TD align='center'>"
                        + listStock.getOrderList(i).getId() + "</TD>");
//                out.println("<TD align='center'>"
//                        + listStock.getOrderList(i).getCompanyName() + "</TD>");
//                out.println("<TD align='center'>"
//                        + listStock.getOrderList(i).getType() + "</TD>");
                out.println("<TD align='center'>"
                        + listStock.getOrderList(i).getPrice() + "</TD>");
                out.println("<TD align='center'>"
                        + listStock.getOrderList(i).getDate() + "</TD></tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspException(e.getMessage());
        }
    }

}
