/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MonitoringThread;
import model.TranscoderCOntrollerThread;
import model.Video;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class Converter.
 */
public class Converter extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
	
    /**
     * Instantiates a new converter.
     *
     * @see HttpServlet#HttpServlet()
     */
    public Converter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	HttpSession session = request.getSession(false);	
		
	if(session == null || session.getAttribute(Video.VIDEO_COUNTER) == null ){
			
		RequestDispatcher view = request.getRequestDispatcher(BasicServlet.INDEX_JSP);
		view.forward(request, response);
		return;
	}
		
	TranscoderCOntrollerThread transcoderCOntrollerThread = new TranscoderCOntrollerThread(session);
	transcoderCOntrollerThread.start();
	
	
	MonitoringThread monitoringThread = new MonitoringThread(session);
	monitoringThread.start();
	
	RequestDispatcher view = request.getRequestDispatcher(BasicServlet.MONITORING_JSP);
	view.forward(request, response);
	
	}	
		
	
	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
