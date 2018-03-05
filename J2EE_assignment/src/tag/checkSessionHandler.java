package tag;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class checkSessionHandler extends BodyTagSupport {
    private HttpServletResponse response;

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public int doEndTag() throws JspException {
        int res = EVAL_PAGE;
        if (null == pageContext.findAttribute("login")) {
            res = SKIP_PAGE;
            try {
                response.sendRedirect("/Login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
