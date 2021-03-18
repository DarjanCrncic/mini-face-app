
$('#proracuniGrupe').bootstrapTable({
	url: 'Proracuni?operation=getGroups',
	dataField: 'data',
	pagination: true,
	pageSize: '10',
	search: true,
	columns: [{
		field: 'ID',
		title: 'ID',
		//visible: false
	}, {
		field: 'NAME',
		title: 'Ime grupe'
	}, {
		field: 'CREATION_TIME',
		title: 'Vrijeme kreiranja'
	}, {
		field: 'SENT',
		title: 'Poslano'
	}, {
		field: 'action',
		title: 'Actions',
		align: 'center',
		formatter: "lockButtonFormatter",
		events: {
			'click .send-button': function(e, value, row) {
				ajaxLockGroup(row);
			}
		}
	}],
	showRefresh: true,
	buttons: [{
		name: "add-group-row",
		title: "Add Group",
		icon: "fa fa-plus",
		event: function() {
			$('#proracuniAddGroupModal').modal('show');
		}
	}],
	detailView: true,
	onExpandRow: function(index, row, $detail) {
		$('#showOrNoInput').val(row.SENT);
		$($detail).addClass('active');
		$detail.html('<table id="tableStavka_' + row.ID + '" style="margin-bottom: 3rem"></table>').find('table').bootstrapTable({
			url: 'Proracuni?operation=getEntries&groupID=' + row.ID,
			dataField: 'data',
			columns: [{
				field: 'ID',
				title: 'ID',
			}, {
				field: 'TYPE_TEXT',
				title: 'Tip pošiljke'
			}, {
				field: 'RETURN',
				title: 'Povratno'
			}, {
				field: 'LOCAL_TEXT',
				title: 'Lokacija isporuke'
			}, {
				field: 'PACKAGE_WEIGHT',
				title: 'Težina'
			}, {
				field: 'CREATION_TIME',
				title: 'Vrijeme kreiranja'
			}, {
				field: 'GROUP_ID',
				title: 'Group ID',
				visible: false
			}, {
				field: 'LOCAL',
				visible: false
			}, {
				field: 'TYPE',
				visible: false
			}, {
				field: 'action',
				title: 'Actions',
				align: 'center', 
				formatter: "editDeleteButtonFormatter",
				events: {
					'click .edit-button': function(e, value, rowi) {
						if (rowi.RETURN === 'true') $('#povratnoCheck').prop("checked", true);
						else $('#povratnoCheck').prop("checked", false);

						$('#selectLocal').val(rowi.LOCAL).trigger('change');

						$('#selectSendingType').val(rowi.TYPE).trigger('change');

						$('#entryWeight').val(rowi.PACKAGE_WEIGHT);

						$('#proracuniAddEditTitle').text("Edit Entry");
						$('#proracuniAddEntryModal').modal('show');
						$('#groupIDInput').val(rowi.GROUP_ID);
						$('#entryIDInput').val(rowi.ID);
						$('#proracuniCreateEntryConfirm').val('edit');
					},

					'click .delete-button': function(e, value, rowi) {
						ajaxDeleteEntry(rowi);
					}

				}
			}],
			showRefresh: true,
			buttons: [{
				name: "add-group-row",
				title: "Add Group",
				icon: "fa fa-plus",
				event: function() {
					if($('#showOrNoInput').val() === 'false'){
						$('#entryWeight').val(1);
						$('#selectSendingType').val();
	
						$('#proracuniAddEditTitle').text("Add Entry");
						$('#proracuniAddEntryModal').modal('show');
						$('#groupIDInput').val(row.ID);
						$('#proracuniCreateEntryConfirm').val('add');
					}else{alert("Cant add more entires, group already sent");}
				}
			}],
		});
	},


});


$('#proracuniCreateGroupConfirm').click(function(e) {
	e.preventDefault();
	let input = {
		name: $('#proracuniNewGroupName').val(),
		operation: "proracuni_createGroup"
	}

	$.ajax({
		type: "POST",
		url: 'Proracuni',
		dataType: 'json',
		data: input,
		success: function(data) {
			if (data.status == 'success') {
				$('#proracuniGrupe').bootstrapTable('refresh');
				$('#proracuniAddGroupModal').modal('hide');
			} else {
				alert(data.message);
			}
		},
		error: function() {
			alert("Something went wrong, try again later");
		}
	});
});


$('#proracuniCreateEntryConfirm').click(function(e) {
	e.preventDefault();
	if ($('#proracuniCreateEntryConfirm').val() === 'add')
		ajaxEntry("proracuni_addEntry");
	if ($('#proracuniCreateEntryConfirm').val() === 'edit')
		ajaxEntry("proracuni_editEntry");
});


function ajaxEntry(operation) {
	let returnTrip;
	if ($('#povratnoCheck').is(':checked'))
		returnTrip = 'true';
	else
		returnTrip = 'false';

	let input = {
		id: $('#entryIDInput').val(),
		type: $('#selectSendingType').val(),
		returnTrip: returnTrip,
		local: $('#selectLocal').val(),
		weight: $('#entryWeight').val(),
		operation: operation,
		groupID: $('#groupIDInput').val()
	}

	$.ajax({
		type: "POST",
		url: 'Proracuni',
		dataType: 'json',
		data: input,
		success: function(data) {
			if (data.status == 'success') {
				$('#tableStavka_' + input.groupID).bootstrapTable('refresh');
				$('#proracuniAddEntryModal').modal('hide');
			} else {
				alert(data.message);
			}
		},
		error: function() {
			alert("Something went wrong, try again later");
		}
	});
}

////// ajax call for edit/delete entry

function editDeleteButtonFormatter(value, row) {
	if($('#showOrNoInput').val() === 'false')
		return '<button class="fas fa-edit edit-button"></button>\
			<button class="far fa-trash-alt  delete-button"></button>'
	else
		return '';
		
}


function ajaxDeleteEntry(row) {
	let input = {
		id: row.ID,
		operation: "proracuni_deleteEntry"
	}

	$.ajax({
		type: "POST",
		url: 'Proracuni',
		dataType: 'json',
		data: input,
		success: function(data) {
			if (data.status == 'success') {
				$('#tableStavka_' + row.GROUP_ID).bootstrapTable('refresh');
			} else {
				alert(data.message);
			}
		},
		error: function() {
			alert("Something went wrong, try again later");
		}
	});
}

////////  ajax call for locking group

function lockButtonFormatter(value, row) {
	if (row.SENT === 'false')
		return '<button class="far fa-envelope send-button"></button>';
	else
		return '';
}

function ajaxLockGroup(row) {
	let input = {
		id: row.ID,
		operation: "proracuni_lockGroup"
	}

	$.ajax({
		type: "POST",
		url: 'Proracuni',
		dataType: 'json',
		data: input,
		success: function(data) {
			if (data.status == 'success') {
				$('#proracuniGrupe').bootstrapTable('refresh');
			} else {
				alert(data.message);
			}
		},
		error: function() {
			alert("Something went wrong, try again later");
		}
	});
}

///// init selects for edit/add entries module

$('#selectSendingType').select2({
	theme: 'bootstrap'
});

$.ajax({
	url: 'Proracuni?operation=getTypes',
	dataType: 'json',
	success: function(data) {
		if (data.status == 'success') {
			data.data.forEach(function(type) {
				let option = new Option(type.TEXT, type.ID, true, true);
				$("#selectSendingType").append(option);
			})
		} else {
			alert(data.message);
		}
	},
	error: function() {
		alert("Something went wrong, try again later");
	}
});

$('#selectLocal').select2({
	theme: 'bootstrap',
	data: [{ id: 1, text: "Tuzemno" }, { id: 2, text: "Inozemno" }]
});

