/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package servlet;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

// TODO: Auto-generated Javadoc
/**
 * Application Lifecycle Listener implementation class SessionListener.
 *
 * @see SessionEvent
 */
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * Attribute removed.
	 *
	 * @param arg0 the arg0
	 * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
	 */
    public void attributeRemoved(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
	 * Attribute added.
	 *
	 * @param arg0 the arg0
	 * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
	 */
    public void attributeAdded(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
	 * Value unbound.
	 *
	 * @param arg0 the arg0
	 * @see HttpSessionBindingListener#valueUnbound(HttpSessionBindingEvent)
	 */
    public void valueUnbound(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
	 * Session did activate.
	 *
	 * @param arg0 the arg0
	 * @see HttpSessionActivationListener#sessionDidActivate(HttpSessionEvent)
	 */
    public void sessionDidActivate(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
	 * Session will passivate.
	 *
	 * @param arg0 the arg0
	 * @see HttpSessionActivationListener#sessionWillPassivate(HttpSessionEvent)
	 */
    public void sessionWillPassivate(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
	 * Value bound.
	 *
	 * @param arg0 the arg0
	 * @see HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)
	 */
    public void valueBound(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
	 * Attribute replaced.
	 *
	 * @param arg0 the arg0
	 * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
	 */
    public void attributeReplaced(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
    	/*
    	String source = arg0.getSource().getClass().getName();
        String message = new StringBuffer("Attribute replaced in session  ")
            .append(source).toString();
        System.out.println(message);
        */
    }

	/**
	 * Invoked when session is created.
	 *
	 * @param arg0 the arg0
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
    public void sessionCreated(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
	 * Session destroyed.
	 *
	 * @param arg0 the arg0
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
