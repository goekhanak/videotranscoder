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

<h1>Video Encoder Informatik5</h1>

<%java.text.DateFormat df = new java.text.SimpleDateFormat("dd/mm/yyyy"); %>
<h2>Current Date: <%= df.format(new java.util.Date()) %></h2>



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
		<form METHOD=get ACTION="/VideoConverter/Logout"><input
			type="submit" name="button" value="convert" /></form>
		</td>

	</tr>

</table>





<br />


<!-- 

<img src="himym.gif" width="260" height="180" border="0" title="try title"  name="tryname" alt="1 ">
<img src="himym.s05e02.gif" width="260" height="180" border="0" title="try title"  name="smname" alt="2 ">
<img src="himym0.jpg" width="260" height="180" border="0" title="try title"  name="tryname2" alt="3 ">
<img src="himym3.jpg" width="260" height="180" border="0" title="try title"  name="tryname21" alt="stg">

 -->


<br />


<%

if(session == null){
	%>Session is null<%
}


Integer vc = (Integer) session.getAttribute("videoCounter");

if(vc != null){
	
	%>vc is:
<%= vc%>
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

			<p><a href=<%= Video.getS3Url(v.getMobileVideo())%>  target=“_blank”>Download
			Mobile Video</a></p>
			<p><a href=<%= Video.getS3Url(v.getStreamingVideo())%> target=“_blank”>Stream
			Video</a></p>
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





<!--  

<img src="/home/goekhan/Videos/himym.gif" width="320" height="240" border="0" alt="himym.gif">

-->



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