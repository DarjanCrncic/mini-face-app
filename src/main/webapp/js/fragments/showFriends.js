$(document).ready(function() {

	$("#select-dropdown_0").select2({
		theme: "bootstrap",
		placeholder: "Select Advanced Filters",
		data: [{ id: "all", text: "All" }, { id: "firstName", text: "First Name" }, { id: "lastName", text: "Last Name" }, { id: "username", text: "Username" }]
	});

	$("#select-logical-operand").select2({
		theme: "bootstrap",
		data: [{ id: "and", text: "AND" }, { id: "or", text: "OR" }]
	});
	
	addFilters([{ id: "all", text: "All" }, { id: "firstName", text: "First Name" }, { id: "lastName", text: "Last Name" }, { id: "username", text: "Username" }]);
	
	///// show all friends in table
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

