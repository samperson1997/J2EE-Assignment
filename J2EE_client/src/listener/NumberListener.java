package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class NumberListener implements HttpSessionListener, HttpSessionAttributeListener, ServletContextListener {
    private ServletContext context;
    private Integer visitorCount;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        context.setAttribute("OnlineCount", 0);
        context.setAttribute("VisitorCount", 0);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        System.out.println("创建session，人数加1");
        // 获取当前人数
        visitorCount = (Integer) context.getAttribute("VisitorCount");

        visitorCount++;

        // 改变人数
        context.setAttribute("VisitorCount", visitorCount);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        System.out.println("销毁session，人数减1");
        visitorCount = (Integer) context.getAttribute("VisitorCount");
        visitorCount--;

        context.setAttribute("VisitorCount", visitorCount);
    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        if (se.getName().equals("login")) {
            System.out.println("增加attribute，人数加1");
            Integer onlineCount = (Integer) context.getAttribute("OnlineCount");
            onlineCount++;
            visitorCount--;
            context.setAttribute("VisitorCount", visitorCount);
            context.setAttribute("OnlineCount", onlineCount);
        }

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        if (se.getName().equals("login")) {
            Integer onlineCount = (Integer) context.getAttribute("OnlineCount");
            onlineCount--;
            visitorCount++;
            context.setAttribute("VisitorCount", visitorCount);
            context.setAttribute("OnlineCount", onlineCount);
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
