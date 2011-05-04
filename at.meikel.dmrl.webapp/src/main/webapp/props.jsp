<html>
<body>
<h2>Properties</h2>
<p><%=new java.util.Date()%></p>
<br/>
<%
java.util.Properties prop = new java.util.Properties();
prop.load(getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
// prop.list((java.io.PrintWriter) out);
java.util.Enumeration e = prop.propertyNames(); 
while (e.hasMoreElements()) {
	String key = (String) e.nextElement();
	String value = prop.getProperty(key);
	out.println("<p>" + key + " = " + value + "</p>");
}
%>
</body>
</html>