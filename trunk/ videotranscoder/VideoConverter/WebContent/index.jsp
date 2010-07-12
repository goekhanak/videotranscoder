<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="servlet.Animator"%>
<%@page import="model.Video"%><html>
<%@page import="java.util.Hashtable"%>
<%@page import="javazoom.upload.MultipartFormDataRequest"%>





<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Cloud Video Transcoder</title>
	<link type="text/css" rel="stylesheet" href="style.css">

</head>
<body>

<div id="wrapper">
	<div id="headercontainer">
		<div id="header">
			<h1><center><a href="index.jsp" class="title">Cloud Video Transcoder</a></center></h1>
		</div>
		
		
		<div id="nav2">
        	<ul>
        		<span class="">Show:</span>
                <li><a href="index.jsp?view=images" class="link">Home</a></li> |
                <li><a href="index.jsp?view=videos" class="link">Upload</a></li> |
                <li><a href="index.jsp?view=videos" class="link">Gallery</a></li> |
                <li><a href="index.jsp?view=videos" class="link">Transcoded</a></li> |
                <li><a href="index.jsp?view=videos" class="link">Monitor</a></li> |
        	</ul>
        </div>		
		
	 </div>	
	 
<br/>
<br/>
<br/>	 
<h3>Auto scales to the number transcoding jobs</h3>





<form METHOD=post ACTION="/VideoConverter/Animator.html"
	ENCTYPE="multipart/form-data" onsubmit="return check();"
	>Please select a video file: <input TYPE="file"
	NAME="fname" size="50" accept="image/gif,image/jpeg"  > <br />
<input type="submit" name="button" value="upload" /></form>



<script type="text/javascript" language="JavaScript">
function check() {
  var ext = document.f.pic.value;
  ext = ext.substring(ext.length-3,ext.length);
  ext = ext.toLowerCase();
  if(ext != 'jpg') {
    alert('You selected a .'+ext+
          ' file; please select a .jpg file instead!');
    return false; }
  else
    return true; }
</script>



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
			
			%> <img src=<%=v.getGifFile() %> width="260" height="180"
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


</div>

</body>
</html>