<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="servlet.Animator"%>
<%@page import="model.Video"%>
<%@page import="model.MonitoringThread"%><html>
<%@page import="java.util.Hashtable"%>
<%@page import="javazoom.upload.MultipartFormDataRequest"%>



<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Video Encoder Informatik5</title>
</head>
<body>

<h1>Video Transcoder Informatik5</h1>


<br />

<%

if(session == null){
	%>Session is null<%
}
Integer vc = (Integer) session.getAttribute("videoCounter");

if(vc != null){

	
}	

Integer ready =  new Integer(0); 

if(session.getAttribute(Video.IS_READY_SESSION_KEY) != null ){
	ready = (Integer) session.getAttribute(Video.IS_READY_SESSION_KEY);	
	if(ready.equals(new Integer(0)) ){
		%>
			<b>
			<font size="5" color="#FFFF00">Transcoding is still going on</font>
			</b>
		<%
	}else{
		%>
			<b>
			<font size="5" color="#00FF00">Transcoding is completed click see the results</font>
			</b>
		<%
	}
}


%>


<br />


<table>
	<tr>

		<td>
		<form METHOD=get ACTION="/VideoConverter/Logout"><input
			type="submit" name="button" value="Reset" /></form>
		</td>


		<td>
		<form METHOD=get ACTION="/VideoConverter/Monitoring.jsp"><input
			type="submit" name="button" value="Refresh" /></form>
		</td>
		
		<%
			if(ready.equals(new Integer(1))){
			%>	
				<td>
				<form METHOD=get ACTION="/VideoConverter/Encoded.jsp"><input
					type="submit" name="button" value="See the Results" /></form>
				</td>
			<%
			}
		%> 
		
		
		
		
	</tr>

</table>





<br />



<br />


<%

if(session == null){
	%>Session is null<%
}


if(vc != null){

	

	%>
<!--	
	vc is:
<%= vc%>
 -->
<%
	Video v = null;
	
	
	%>
<br />

<%
	
String statistics = "";

if( session.getAttribute(MonitoringThread.STATISTICS_SESSION_KEY) != null){
	statistics = (String)session.getAttribute(MonitoringThread.STATISTICS_SESSION_KEY);
}

%>





<b>Instance Statistics</b>
<br />
<textarea rows="25" cols="140" readonly="readonly">
	
<%=statistics%>
</textarea>

<%
	
	
}


%>





<br>


</body>
</html>