$(document).ready(function() {

		$.ajax({
			url: 'ShowFriends',
			dataType: 'json',
			success: function(data) {
				
				for (var i = 0; i < data.data.length; i++) {
					var row = $('<tr><td>' + data.data[i].NAME + '</td><td>' + data.data[i].SURNAME + '</td><td>' + data.data[i].USERNAME + '</td></tr>');
					$('#friendsTable').append(row);
				}
				
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('Error: ' + textStatus + ' - ' + errorThrown);
			}
		});

});

