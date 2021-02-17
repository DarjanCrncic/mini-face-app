/**
 * 
 */

$(document).ready(function() {

		$.ajax({
			url: 'HomePage',
			dataType: 'json',
			success: function(data) {
				$('#ajaxShowGreeting').append('<h1>Hey there ' + data.data.name + ' ' + data.data.surname + '</h1>');		
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('Error: ' + textStatus + ' - ' + errorThrown);
			}
		});

});
