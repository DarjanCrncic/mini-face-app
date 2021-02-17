$(document).ready(function() {

	$.ajax({
		url: 'ShowPosts',
		dataType: 'json',
		type: "GET",
		success: function(data) {
			for (var i = 0; i < data.data.length; i++) {
				$('#ajaxShowVissiblePosts').append('<h3>' + data.data[i].TITLE + '</h3><p>' + data.data[i].BODY + '</p>');
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('Error: ' + textStatus + ' - ' + errorThrown);
		}
	});
});
