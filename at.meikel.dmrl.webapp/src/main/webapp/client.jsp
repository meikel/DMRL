<html>
<head>
  <title>jQuery-basierter DMRL Client</title>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js" ></script>
  <script type="text/javascript" src="http://plugins.jquery.com/files/jquery.dump.js.txt" ></script>
	<script type="text/javascript">
		$(document).ready(function() {
			// alert('doc.ready begin');
			// var url = 'http://dmrl.meikel.cloudbees.net/rest/players';
			// var url ='http://localhost:8080/rest/players';
			// var url ='http://dmrl.meikel.cloudbees.net/rest/players';
			var url = "<%=request.getScheme() %>://<%=request.getServerName() %>:<%=request.getServerPort() %>/rest/players";
			$('#url').empty().append('<span id="url">' + url + '</span>');

			var html = '<table border="1" >\n';
			html = html + '<thead>\n';
			html = html + '<tr>';
			html = html + '<th>Passnummer</th>';
			html = html + '<th>Vorname</th>';
			html = html + '<th>Nachname</th>';
			html = html + '<th>Kategorie</th>';
			html = html + '<th>Verein</th>';
			html = html + '<th>Landesverband</th>';
			html = html + '<th>Ranglistenwert</th>';
			html = html + '\n</thead>\n';
			html = html + '<tbody>\n';
			$('#tabelle').empty().append(html).append('</tbody>\n</table>\n');
			$.ajax({
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				url: url,
				success: function(data, textStatus, jqXHR) {
					// alert('SUCCESS: ' + textStatus);
					// alert('dump = ' + $.dump);
					$("#dump").append($.dump(data));
					// alert('dumped.');
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
					html = html + '</tbody>\n';
					html = html + '</table>\n';
					// alert(html);
					$('#tabelle').empty().append(html);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('ERROR: ' + textStatus + "," + errorThrown);
				} 
			});
			// alert('doc.ready end');
		});
	</script>
</head>
<body>
<h1>Dies ist ein auf jQuery-basierender DMRL Client.</h1>
<p><a href="/index.jsp">Zur&uuml;ck</a>&nbsp;<a href="/client.jsp">Aktualisieren</a></p>

<h1>Tabelle</h1>
<p>URL: <span id="url"></span></p>
<div id="tabelle"></div>

<h1>Dump</h1>
<pre id="dump"></pre>
</body>
</html>