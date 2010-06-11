/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.management.monitor.Monitor;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CLEngine;
import model.TranscoderCOntrollerThread;
import model.Video;
import aws.AutoScaler;
import aws.S3Connector;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class Animator.
 */
public class Animator extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
      
	
    /**
     * Instantiates a new animator.
     *
     * @see HttpServlet#HttpServlet()
     */
    public Animator() {
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
		PrintWriter out = response.getWriter();
		
		
		
		out.println(BasicServlet.DOC_TYPE +
                "<HTML>\n" +
                "<HEAD><TITLE>Hello WWW</TITLE></HEAD>\n" +
                "<BODY>\n" +
                "<H1> Animator Servlet 5</H1>\n" +
             
                "</BODY></HTML>");
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
		
    //to get the content type information from Request Header
	String contentType = request.getContentType();
	
	/*here we are checking the content type is not equal to Null and
	 as well as the passed data from mulitpart/form-data is greater than or
	 equal to 0
	*/

	
	if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
		DataInputStream in = new DataInputStream(request.getInputStream());
		//we are taking the length of Content type data
		int formDataLength = request.getContentLength();
		
		
		if(formDataLength < 400 ){
			RequestDispatcher view = request.getRequestDispatcher(BasicServlet.FIRST_JSP);
			view.forward(request, response);
			return;
		}
		
		byte dataBytes[] = new byte[formDataLength];
		int byteRead = 0;
		int totalBytesRead = 0;
		//this loop converting the uploaded file into byte code
		while (totalBytesRead < formDataLength) {
			byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
			totalBytesRead += byteRead;
			}

		String file = new String(dataBytes);
		//for saving the file name
		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\")
				+ 1,saveFile.indexOf("\""));
		int pos;
		//extracting the index of file 
		pos = file.indexOf("filename=\"");
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		int startPos = ((file.substring(0, pos)).getBytes()).length;

		
		
		CLEngine.shellRunner("pwd");
		// creating a new file with the same name and writing the content in new file
		File savedFile = new File(Video.ABSOLUTE_PATH+saveFile);
		savedFile.createNewFile();
		
		
		FileOutputStream fileOut = new FileOutputStream(savedFile);
		
		fileOut.write(dataBytes, startPos, (formDataLength - startPos));
		fileOut.flush();
		fileOut.close();
		
		
		/*
		 * Add the Video to Videos S3 Bucket
		 * */
		
		S3Connector.getS3Connector().uploadFile(Video.VIDEOS_BUCKET,
				saveFile, savedFile);
		
		
		HttpSession session = request.getSession(false);	
		
		if(session == null || session.getAttribute(Video.VIDEO_COUNTER) == null ){	
			session = request.getSession(true);
			Integer videoCounter = new Integer(0);
			session.setAttribute(Video.VIDEO_COUNTER, videoCounter);
		}
		
		Video newVideo = new Video();
		newVideo.setOrginalFile(saveFile);
		newVideo.generateGif();
		
		Integer  vc = (Integer)session.getAttribute(Video.VIDEO_COUNTER) ; 
		session.setAttribute(Video.VIDEO_COUNTER, new Integer(vc+1));
		session.setAttribute("v"+(vc+1),newVideo);
		
		AutoScaler.setNumberOfInstances(vc+1);
			
		PrintWriter out = response.getWriter();
		out.println(BasicServlet.DOC_TYPE +
	                "<HTML>\n" +
	                "<HEAD><TITLE>Hello WWW</TITLE></HEAD>\n" +
	                "<BODY>\n" +
	                "<H1> Network HW 5</H1>\n" +
	                "<p><a href=\"http://localhost:8080/Network5/RegisterSaleHtml.html\" >Register Sale</a></p> \n"
		);	
		
		
		savedFile.delete();
			
		Video v;
		for (int i = 1 ;i <= (Integer)session.getAttribute(Video.VIDEO_COUNTER); i++ ) {
			
			v = (Video)session.getAttribute("v"+i);
			if (v != null){
				out.println("<p> Orginal FIle: " +v.getOrginalFile() +" </p> \n");
				out.println("<p> Gif FIle: " +v.getGifFile() +" </p> \n");
			}
			
		}
						
		out.println("</BODY></HTML>");
		
		
		RequestDispatcher view = request.getRequestDispatcher("/First.jsp");
		view.forward(request, response);
		
	}
		
	}	
 }

