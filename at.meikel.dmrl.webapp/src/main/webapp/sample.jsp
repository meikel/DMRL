<html>
<head>
  <title>AJAX Sample</title>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js" ></script>
  <script type="text/javascript" src="http://plugins.jquery.com/files/jquery.dump.js.txt" ></script>
	<script type="text/javascript">
		var url1 = "<%=request.getScheme() %>://<%=request.getServerName() %>:<%=request.getServerPort() %>/sample.json";
		var url2 = "<%=request.getScheme() %>://<%=request.getServerName() %>:<%=request.getServerPort() %>/sample.jsonp";
		var url3 = "http://dmrl.meikel.cloudbees.net/sample.json";
		var url4 = "http://dmrl.meikel.cloudbees.net/sample.jsonp";
		var url5 = "<%=request.getScheme() %>://localhost:<%=request.getServerPort() %>/sample.json";
		var url6 = "<%=request.getScheme() %>://localhost:<%=request.getServerPort() %>/sample.jsonp";
		var url7 = "<%=request.getScheme() %>://127.0.0.1:<%=request.getServerPort() %>/sample.json";
		var url8 = "<%=request.getScheme() %>://127.0.0.1:<%=request.getServerPort() %>/sample.jsonp";

		var jsonp = function() {
			alert('This is the JSONP callback.');
		};
		
		var jsonpcallback = function() {
			alert('This is jsonpcallback.');
		};
		
		var parsejson = function(data, html) {
			alert('parsejson start');
			for (index in data) {
				var player = data[index];
				html = html + '<tr>';
				html = html + '<td>' + (player.passnummer == '' ? '&nbsp;' : player.passnummer) + '</td>';
				html = html + '<td>' + (player.vorname == '' ? '&nbsp;' : player.vorname) + '</td>';
				html = html + '<td>' + (player.nachname == '' ? '&nbsp;' : player.nachname) + '</td>';
				html = html + '<td>' + (player.kategorie == '' ? '&nbsp;' : player.kategorie) + '</td>';
				html = html + '<td>' + (player.verein == '' ? '&nbsp;' : player.verein) + '</td>';
				html = html + '<td>' + (player.landesverband == '' ? '&nbsp;' : player.landesverband) + '</td>';
				html = html + '<td>' + (player.ranglistenwert == '' ? '&nbsp;' : player.ranglistenwert) + '</td>';
				html = html + '</tr>\n';
			}
			alert('parsejson end');
			return html;
		};

		var parsexml = function(data) {
			alert('parsexml');
			data = '<rss version="2.0"><Player><title>RSS Title</title></Player></rss>';
			xmlDoc = $.parseXML( data );
			$("#dump").append($.dump(xmlDoc));
			$xml = $( xmlDoc );
		    $title = $xml.find( 'Player' );
		    alert('title = ' + title);
		};
		
		var ajaxjson = function(url) {
			alert('ajaxjson: ' + url);
			$("#dump").empty();
			// $('#tabelle').empty().append(html).append('</tbody>\n</table>\n');
			$.ajax({
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				url: url,
				success: function(data, textStatus, jqXHR) {
					alert('SUCCESS: ' + textStatus);
					// alert('dump = ' + $.dump);
					$("#dump").append($.dump(data));
					// alert('dumped.');
					// html = parsejson(data, html);
					// alert(html);
					// $('#tabelle').empty().append(html);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('ERROR: ' + textStatus + "," + errorThrown);
				}
			});
			// alert('doc.ready end');
		};
		
		var ajaxjsonp = function(url) {
			alert('ajaxjsonp: ' + url);
			$("#dump").empty();
			// $('#tabelle').empty().append(html).append('</tbody>\n</table>\n');
			$.ajax({
				contentType: "application/json; charset=utf-8",
				dataType: "jsonp",
				jsonpCallback: "jsonp",
				url: url,
				success: function(data, textStatus, jqXHR) {
					alert('SUCCESS: ' + textStatus);
					// alert('dump = ' + $.dump);
					$("#dump").append($.dump(data));
					// alert('dumped.');
					// html = parsejson(data, html);
					// alert(html);
					// $('#tabelle').empty().append(html);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('ERROR: ' + textStatus + "," + errorThrown);
				}
			});
			// alert('doc.ready end');
		};
		
		$(document).ready(function() {
			alert('doc.ready');

			$('#url1').empty().append('<span id="url1">' + url1 + '</span>');
			$('#url2').empty().append('<span id="url2">' + url2 + '</span>');
			$('#url3').empty().append('<span id="url3">' + url3 + '</span>');
			$('#url4').empty().append('<span id="url4">' + url4 + '</span>');
			$('#url5').empty().append('<span id="url5">' + url5 + '</span>');
			$('#url6').empty().append('<span id="url6">' + url6 + '</span>');
			$('#url7').empty().append('<span id="url7">' + url7 + '</span>');
			$('#url8').empty().append('<span id="url8">' + url8 + '</span>');

			$("#exec1").click(function() {
				ajaxjson(url1);
			});

			$("#exec2").click(function() {
				ajaxjsonp(url2);
			});

			$("#exec3").click(function() {
				ajaxjson(url3);
			});

			$("#exec4").click(function() {
				ajaxjsonp(url4);
			});

			$("#exec5").click(function() {
				ajaxjson(url5);
			});

			$("#exec6").click(function() {
				ajaxjsonp(url6);
			});

			$("#exec7").click(function() {
				ajaxjson(url7);
			});

			$("#exec8").click(function() {
				ajaxjsonp(url8);
			});
		});
	</script>
</head>
<body>
<h1>Dies ist ein auf jQuery-basierender DMRL Client.</h1>
<p><a href="/index.jsp">Zur&uuml;ck</a>&nbsp;<a href="/sample.jsp">Aktualisieren</a></p>

<h1 id="exec1">URL 1: <span id="url1"></span></h1>

<h1 id="exec2">URL 2: <span id="url2"></span></h1>

<h1 id="exec3">URL 3: <span id="url3"></span></h1>

<h1 id="exec4">URL 4: <span id="url4"></span></h1>

<h1 id="exec5">URL 5: <span id="url5"></span></h1>

<h1 id="exec6">URL 6: <span id="url6"></span></h1>

<h1 id="exec7">URL 5: <span id="url7"></span></h1>

<h1 id="exec8">URL 6: <span id="url8"></span></h1>

<h1>Dump</h1>
<pre id="dump"></pre>
</body>
</html>