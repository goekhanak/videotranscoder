<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="servlet.Animator"%>
<%@page import="model.Video"%>
<%@page import="servlet.Monitoring"%><html>
<%@page import="java.util.Hashtable"%>
<%@page import="javazoom.upload.MultipartFormDataRequest"%>



<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Video Encoder Informatik5</title>
</head>
<body>

<h1>Video Transcoder Informatik5</h1>
<h2>Transcoded videos</h2>


<br />


<table>
	<tr>

		<td>
		<form METHOD=get ACTION="/VideoConverter/Logout"><input
			type="submit" name="button" value="reset" /></form>
		</td>


		<td>
		<form METHOD=get ACTION="/VideoConverter/First.jsp"><input
			type="submit" name="button" value="back" /></form>
		</td>

	</tr>

</table>





<br />


<%

if(session == null){
	%>Session is null<%
}


Integer vc = (Integer) session.getAttribute("videoCounter");

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
	
%>



<table>
	<%
	%><tr>
		<%
	
	for (int i = 1 ;i <= vc; i++ ) {
		
		v = (Video)session.getAttribute("v"+i);
		if (v != null){
			
			if((i%5) == 0){
				%><tr>
			<%	
				;
			}
			
			%><td>
			<%
			
			%> <img src=<%=  v.getGifFile() %> width="260" height="180"
				border="0" title=<%= v.getOrginalFile()%>
				name=<%= v.getOrginalFile()%> alt=<%= v.getOrginalFile()%>>

			<p><a href=<%= Video.getS3Url(v.getMobileVideo())%>
				target=“_blank”> Mobile Video</a></p>

			<p><a href=<%= Video.getS3Url(v.getMobileVideo())%>
				target=“_blank”> Streaming Video</a></p>

			<%
			
			%>
			</td>
			<%
			
			
			if((i%4) == 0){
				%>
		</tr>
		<%	
				;
			}
		
		}
		
		
			 
	}
	%>
	</tr>
	<%
	%>
</table>



<%
	
	
}


%>





<a href="/home/goekhan/Videos/himym3.jpg"><img
	src="/home/goekhan/Videos/himym3_thumbnail.png" width="160"
	height="120" border="0" alt=""></a>

<script type="text/javascript">
var path = document.getElementById('pathID').value;
document.write(document.location.pathname).getElementById('pathID');
</script>




<br>


</body>
</html>