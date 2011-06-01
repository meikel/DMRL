<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="properties" uri="http://www.meikel.at/jsp/dmrl/properties" %>
<html>
<body>
<h2>Properties</h2>
<p><%=new java.util.Date()%></p>
<p><c:out value="tralala" ></c:out></p>
<br/>
<properties:calendar var="temp.str2" ></properties:calendar>
<br/>
<br/>
<hr />
<br/>
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