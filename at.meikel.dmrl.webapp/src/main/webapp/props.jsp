<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="properties"
	uri="http://www.meikel.at/taglib/properties"%>

<%@page import="java.util.Enumeration"%><html>
<body>
<h1>Properties</h1>
<p><c:out value="<%=new java.util.Date()%>"></c:out></p>
<h1>&lt;properties:list&gt;</h1>
<h2>All</h2>
<properties:list path="/META-INF/MANIFEST.MF"></properties:list>
<h2>Some attributes</h2>
<properties:list path="/META-INF/MANIFEST.MF"
	keys=" Manifest-Version , , key-does-not-exist ,    Manifest-Version"></properties:list>
<h1>&lt;properties:value&gt;</h1>
<p>/META-INF/MANIFEST.MF&nbsp;::&nbsp;Manifest-Version=<properties:value
	path="/META-INF/MANIFEST.MF" key=" Manifest-Version "></properties:value></p>
<p>/META-INF/MANIFEST.MF&nbsp;::&nbsp;key-does-not-exist=<properties:value
	path="/META-INF/MANIFEST.MF" key=" key-does-not-exist "></properties:value></p>
</body>
</html>