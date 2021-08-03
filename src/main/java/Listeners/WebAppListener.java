package Listeners;

import Constants.SharedConstants;
import Service.AllServices;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebAppListener implements ServletContextListener {
    private AllServices allServices;
    public WebAppListener(){
        allServices = new AllServices();
    }
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute(SharedConstants.ATTRIBUTE,allServices);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().removeAttribute(SharedConstants.ATTRIBUTE);
    }
}
