/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Video;
import model.VideoController;
import ws.TranscoderClient;
import aws.AutoScaler;
import aws.EC2Connector;

import com.amazonaws.services.ec2.model.Instance;

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
			
		RequestDispatcher view = request.getRequestDispatcher(BasicServlet.FIRST_JSP);
		view.forward(request, response);
		return;
	}
		
	
	Integer  vc = (Integer)session.getAttribute(Video.VIDEO_COUNTER) ; 
		
		
	PrintWriter out = response.getWriter();
	out.println(BasicServlet.DOC_TYPE +
                "<HTML>\n" +
                "<HEAD><TITLE>Hello WWW</TITLE></HEAD>\n" +
                "<BODY>\n" +
                "<H1> Network HW 5</H1>\n" +
                "<p><a href=\"http://localhost:8080/Network5/RegisterSaleHtml.html\" >Link</a></p> \n"
	);	
		
	AutoScaler.setNumberOfInstances(vc);
	
	VideoController videoController =  new VideoController();
	Video v;
	
	EC2Connector ec2Connector = EC2Connector.getEC2Connector();
	
	
	for (int i = 1 ;i <= vc; i++ ) {
		
		v = (Video)session.getAttribute("v"+i);
		if (v != null){
			videoController.addVideo(v);
			out.println("<p> Orginal FIle: " +v.getOrginalFile() +" </p> \n");
			out.println("<p> Gif FIle: " +v.getGifFile() +" </p> \n");
			out.println("<p> MobileVideo FIle: " +v.getMobileVideo() +" </p> \n");
			out.println("<p> StreaminVideo FIle: " +v.getStreamingVideo() +" </p> \n");
		}
		
	}
	
	TranscoderClient transcoderClient = null;
	
	
	while(videoController.existsInitialVideo()){
		
		Instance instance = ec2Connector.getAvailableInstance();
		
		System.out.println("videoController.existsInitialVideo() is more");
		
		if(instance != null){
			out.println("<p> Available Instance: " + instance.getPublicDnsName() +" </p> \n");
			System.out.println("AvailabelDNS "+instance.getPublicDnsName());
			
			//transcoderClient = new TranscoderClient("ec2-79-125-28-154.eu-west-1.compute.amazonaws.com", videos.get(0).getOrginalFile(),videos);
			//transcoderClient = new TranscoderClient("localhost", videos.get(0).getOrginalFile(),videos);
			
			
			transcoderClient = new TranscoderClient(instance.getPublicDnsName(), videoController.getFirstInitialVideoName(),videoController);
			
			
			
			transcoderClient.executeService();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	while(!videoController.isAllVideosTranscoded()){
		System.out.println("videoController.isAllVideosTranscoded is more");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//AutoScaler.setNumberOfInstances(0);
	
Vector<Video> videos = new Vector<Video>();	
	
for (int i = 1 ;i <= vc; i++ ) {
		
		v = (Video)session.getAttribute("v"+i);
		if (v != null){
			videos.add(v);
			String orginalVideo = v.getOrginalFile();
			v.setMobileVideo(orginalVideo.substring(0,orginalVideo.lastIndexOf("."))+"-mobile.mp4");
			v.setStreamingVideo(orginalVideo.substring(0,orginalVideo.lastIndexOf("."))+"-streaming.mp4");
			session.setAttribute("v"+i,v);
			
			out.println("<p> Orginal FIle: " +v.getOrginalFile() +" </p> \n");
			out.println("<p> Gif FIle: " +v.getGifFile() +" </p> \n");
			out.println("<p> MobileVideo FIle: " +v.getMobileVideo() +" </p> \n");
			out.println("<p> StreaminVideo FIle: " +v.getStreamingVideo() +" </p> \n");
		}
		
	}
	
	out.println("</BODY></HTML>");
	
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
