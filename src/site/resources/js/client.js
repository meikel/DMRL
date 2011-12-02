var retrieve = function(service, id) {
	var url = 'http://dmrl.meikel.cloudbees.net:80/rest' + service;
	jQuery("#dump").empty();
	jQuery(id).empty().append('<p>Calling service ' + url + '</p>');
	jQuery.getJSON(url + '?callback=?', function(data) {
		// jQuery("#dump").append(jQuery.dump(data));

		var html = '<table border="1" >\n';
		html = html + '<thead>\n';
		html = html + '<tr>';
		html = html + '<th>Position</th>';
		html = html + '<th>Firstname</th>';
		html = html + '<th>Name</th>';
		html = html + '<th>Category</th>';
		html = html + '<th>Association</th>';
		html = html + '<th>National Association</th>';
		html = html + '<th>Ranking list value</th>';
		html = html + '\n</thead>\n';
		html = html + '<tbody>\n';
		jQuery(id).empty().append(html).append('</tbody>\n</table>\n');
		if (!jQuery.isArray(data)) {
			data = [ data ];
		}
		for (index in data) {
			var player = data[index];
			html = html + '<tr>';
			html = html + '<td>'
					+ (player.position == '' ? '&nbsp;' : player.position)
					+ '</td>';
			html = html + '<td>'
					+ (player.vorname == '' ? '&nbsp;' : player.vorname)
					+ '</td>';
			html = html + '<td>'
					+ (player.nachname == '' ? '&nbsp;' : player.nachname)
					+ '</td>';
			html = html + '<td>'
					+ (player.kategorie == '' ? '&nbsp;' : player.kategorie)
					+ '</td>';
			html = html + '<td>'
					+ (player.verein == '' ? '&nbsp;' : player.verein)
					+ '</td>';
			html = html
					+ '<td>'
					+ (player.landesverband == '' ? '&nbsp;'
							: player.landesverband) + '</td>';
			html = html
					+ '<td>'
					+ (player.ranglistenwert == '' ? '&nbsp;'
							: player.ranglistenwert) + '</td>';
			html = html + '</tr>\n';
		}
		html = html + '</tbody>\n';
		html = html + '</table>\n';
		jQuery(id).empty().append(html);
	});
};

$(document).ready(function() {
	jQuery('#warning').empty();

	jQuery('#clear').click(function() {
		jQuery("#dump").empty();
		jQuery('#rangliste').empty();
	});

	jQuery('#sample1').click(function() {
		retrieve('/players/byLicenseId/37225', '#rangliste');
	});

	jQuery('#sample2').click(function() {
		retrieve('/playersByTeam/SG%20Weiterstadt%201886', '#rangliste');
	});

	jQuery('#sample3').click(function() {
		retrieve('/players', '#rangliste');
	});
});
