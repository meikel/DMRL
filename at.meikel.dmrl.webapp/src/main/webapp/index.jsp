<html>
<body>
<h1>DMRL Startseite</h1>
<p><%=new java.util.Date()%></p>
<p><%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/</p>
<h2>DMRL Clients</h2>
<p><a href="/client.jsp">jQuery-basierter Client</a></p>

<h2>Verschiedenes</h2>
<p><a href="/props.jsp">Verwendung der Taglib</a></p>
<p><a href="/sample.jsp">/sample.jsp</a></p>
<p><a href="/sample.xml">/sample.xml</a></p>
<p><a href="/sample.json">/sample.json</a></p>
<p><a href="/sample.jsonp">/sample.jsonp</a></p>

<h2>API</h2>
<h3>Admin</h3>
<p><a href="/rest/admin/retrieveData">rest/admin/retrieveData</a></p>
<p><a href="/rest/admin/reloadData">rest/admin/reloadData</a></p>
<p><a href="/rest/admin/getCurrentData">rest/admin/getCurrentData</a></p>
<p><a href="/rest/admin/listAllData">rest/admin/listAllData</a></p>
<h3>Players</h3>
<p><a href="/rest/players">rest/players</a></p>
<p><a href="/rest/players.jsonp">rest/players.jsonp</a></p>
<p><a href="/rest/players/byLicenseId/37225.0">rest/players/byLicenseId/37225.0</a></p>
<p><a href="/rest/players/byLicenseId/4711.jsonp">rest/players/byLicenseId/4711.jsonp</a></p>
</body>
</html>