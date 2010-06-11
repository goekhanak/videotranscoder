<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="servlet.Animator"%>
<%@page import="model.Video"%><html>
<%@page import="java.util.Hashtable"%>
<%@page import="javazoom.upload.MultipartFormDataRequest"%>



<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Video Encoder Informatik5</title>
</head>
<body>

<h1>Video Transcoder Informatik5</h1>
<h3>Auto scales to the number transcoding jobs</h3>

<br />



<form METHOD=post ACTION="/VideoConverter/Animator.html"
	ENCTYPE="multipart/form-data">Select a file: <input TYPE="file"
	NAME="fname" size="50"> <br />
<input type="submit" name="button" value="upload" /></form>


<br />


<table>
	<tr>

		<td>
		<form METHOD=get ACTION="/VideoConverter/Logout"><input
			type="submit" name="button" value="reset" /></form>
		</td>


		<td>
		<form METHOD=get ACTION="/VideoConverter/Converter"><input
			type="submit" name="button" value="convert" /></form>
		</td>

	</tr>

</table>


<br />



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
	%><table>
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

			<p><%= v.getOrginalFile()%></p>
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


<br>


</body>
</html>