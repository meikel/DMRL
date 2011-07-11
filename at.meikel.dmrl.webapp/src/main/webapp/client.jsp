<html>
<head>
  <title>jQuery-basierter DMRL Client</title>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js" ></script>
  <script type="text/javascript" src="http://plugins.jquery.com/files/jquery.dump.js.txt" ></script>
  	<%-- TODO: this code is a peace of shit! Needs to be thrown away. This is just to have something that works. --%>
	<script type="text/javascript">
	var laden = function() {
		$("#dump").empty();
		var url = "<%=request.getScheme() %>://<%=request.getServerName() %>:<%=request.getServerPort() %>/rest/playersByTeam/SG%20Weiterstadt%201886?callback=?";
		// alert('url = ' + url);
		$.getJSON(url, 
			function(data) {
			    $("#dump").append($.dump(data));
	
				$('#url').empty().append('<span id="url">' + url + '</span>');
	
				var html = '<table border="1" >\n';
				html = html + '<thead>\n';
				html = html + '<tr>';
				html = html + '<th>Position</th>';
				// html = html + '<th>Passnummer</th>';
				html = html + '<th>Vorname</th>';
				html = html + '<th>Nachname</th>';
				html = html + '<th>Kategorie</th>';
				html = html + '<th>Verein</th>';
				// html = html + '<th>Landesverband</th>';
				html = html + '<th>Ranglistenwert</th>';
				html = html + '\n</thead>\n';
				html = html + '<tbody>\n';
				$('#tabelle').empty().append(html).append('</tbody>\n</table>\n');
				for (index in data) {
					var player = data[index];
					html = html + '<tr>';
					html = html + '<td>' + (player.position == '' ? '&nbsp;' : player.position) + '</td>';
					// html = html + '<td>' + (player.passnummer == '' ? '&nbsp;' : player.passnummer) + '</td>';
					html = html + '<td>' + (player.vorname == '' ? '&nbsp;' : player.vorname) + '</td>';
					html = html + '<td>' + (player.nachname == '' ? '&nbsp;' : player.nachname) + '</td>';
					html = html + '<td>' + (player.kategorie == '' ? '&nbsp;' : player.kategorie) + '</td>';
					html = html + '<td>' + (player.verein == '' ? '&nbsp;' : player.verein) + '</td>';
					// html = html + '<td>' + (player.landesverband == '' ? '&nbsp;' : player.landesverband) + '</td>';
					html = html + '<td>' + (player.ranglistenwert == '' ? '&nbsp;' : player.ranglistenwert) + '</td>';
					html = html + '</tr>\n';
				}
				html = html + '</tbody>\n';
				html = html + '</table>\n';
				$('#tabelle').empty().append(html);
			}
		);
	};

	$(document).ready(function() {
		$('#exec').click(laden);
	});	
	</script>
</head>
<body>
<h1>Dies ist ein auf jQuery-basierender DMRL Client.</h1>
<p><a href="/index.jsp">Zur&uuml;ck</a>&nbsp;<a href="/client.jsp">Aktualisieren</a>&nbsp;<span id="exec">Laden</span></p>

<h1>Tabelle</h1>
<p>URL: <span id="url"></span></p>
<div id="tabelle"></div>

<h1>Dump</h1>
<pre id="dump"></pre>
</body>
</html>