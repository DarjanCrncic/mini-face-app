$('.datepickerRacuni').datepicker({
	format: 'dd/mm/yyyy',
	startDate: '01/02/2021',
	todayHighlight: true,
	autoclose: true,
});

$('#datepickerEndRacuni').datepicker("setDate", new Date());
$('#datepickerStartRacuni').datepicker("setDate", "01/02/2021");

$("#chooseEntryGroup").select2({
	theme: "bootstrap"
});

$("#multiSelectTypes").select2({
	theme: "bootstrap",
});

$.ajax({
	url: 'Proracuni?operation=getTypes',
	dataType: 'json',
	success: function(data) {
		if (data.status == 'success') {
			data.data.forEach(function(type) {
				let option = new Option(type.TEXT, type.ID, false, false);
				$("#multiSelectTypes").append(option);
			})
			$("#multiSelectTypes").prepend(new Option("All", "all", true, true));
		} else {
			alert(data.message);
		}
	},
	error: function() {
		alert("Something went wrong, try again later");
	}
});

$.ajax({
	url: 'Proracuni?operation=getGroups',
	dataType: 'json',
	success: function(data) {
		if (data.status == 'success') {
			data.data.forEach(function(group) {
				let option = new Option(group.NAME, group.ID, true, true);
				$("#chooseEntryGroup").append(option);
			})
			$("#chooseEntryGroup").prepend(new Option("All", "all", true, true));
		} else {
			alert(data.message);
		}
	},
	error: function() {
		alert("Something went wrong, try again later");
	}
});

$('#createPreview').click(function() {

	let group = $("#chooseEntryGroup").val();

	let types = $("#multiSelectTypes").val();
	if (types.includes("all") || types.length === 0) types = ["all"];

	let sent = false;
	if ($('#sentCheck').is(':checked')) sent = true;

	let input = {
		group: group,
		types: types,
		sent: sent,
		startDate: $('#datepickerStartRacuni').datepicker("getDate").toLocaleDateString("en-US"),
		endDate: $('#datepickerEndRacuni').datepicker("getDate").toLocaleDateString("en-US"),
		operation: "preview"
	}

	$.ajax({
		type: "POST",
		url: 'Proracuni',
		dataType: 'json',
		data: input,
		success: function(data) {
			if (data.status == 'success') {
				showPricingPreview(data.data);
			} else {
				alert(data.message);
			}
		},
		error: function() {
			alert("Something went wrong, try again later");
		}
	});
});

function showPricingPreview(data) {
	$('#previewDiv').empty();
	let coveredTypes = [];
	let localTuzemno = [];
	let localInozemno = [];

	data.forEach(function(item) {
		if (!coveredTypes.includes(item.TYPE)) {
			coveredTypes.push(item.TYPE);
			$('#previewDiv').append('<div id="previewType_' + item.TYPE + '"></div><hr>');
			$('#previewType_' + item.TYPE + '').append('<p class="cjenik-title">' + item.TYPE_TEXT + ": ukupno "+ item.COUNTPERTYPE+
			" kom., ukupna gramaža "+item.WEIGHTPERTYPE+"g, ukupna vrijednost "+item.PRICEPERTYPE +'kn</p>');
		}
		if (!localTuzemno.includes(item.TYPE) && item.LOCAL === 1) {
			$('#previewType_' + item.TYPE + '').append('<div id="previewTypeTuzemni_' + item.TYPE + '"></div>');
			$('#previewTypeTuzemni_' + item.TYPE + '').append('<p class="cjenik-subtitle">Tuzemni: ukupno '+ item.COUNTPERTYPELOCAL+
			" kom., ukupna gramaža "+item.WEIGHTPERTYPELOCAL+"g, ukupna vrijednost "+item.PRICEPERTYPELOCAL +'kn</p>');
			localTuzemno.push(item.TYPE);
		}

		if (!localInozemno.includes(item.TYPE) && item.LOCAL === 2) {
			$('#previewType_' + item.TYPE + '').append('<div id="previewTypeInozemni_' + item.TYPE + '"></div>');
			$('#previewTypeInozemni_' + item.TYPE + '').append('<p class="cjenik-subtitle">Inozemni: ukupno '+ item.COUNTPERTYPELOCAL+
			" kom., ukupna gramaža "+item.WEIGHTPERTYPELOCAL+"g, ukupna vrijednost "+item.PRICEPERTYPELOCAL +'kn</p>');
			localTuzemno.push(item.TYPE);
		}

		if (item.LOCAL === 1) {
			$('#previewTypeTuzemni_' + item.TYPE + '').append('<div class="cjenik-item"><span>1kom ' + item.PACKAGE_WEIGHT + 'g ' + item.PRICE.toFixed(2) + "kn " + '</span></div>');
		} else {
			$('#previewTypeInozemni_' + item.TYPE + '').append('<div class="cjenik-item"><span>1kom ' + item.PACKAGE_WEIGHT + 'g ' + item.PRICE.toFixed(2) + "kn " + '</span></div>');
		}
	});
}



///////////////////// price section ////////////////////////////////////////////////////

$('#toggleCjenikTable').click(function() {
	$('#forToggleDivCjenik').toggle();
	if ($("#forToggleDivCjenik").is(":visible")) $('#toggleCjenikTable').text("Sakrij Cjenik");
	else $('#toggleCjenikTable').text("Prikazi Cjenik");
});

fillCjenik();

function fillCjenik() {
	$.ajax({
		type: "GET",
		url: 'Proracuni?operation=getPrice',
		dataType: 'json',
		success: function(data) {
			if (data.status == 'success') {
				createPriceMenu(data.data);
			} else {
				alert(data.message);
			}
		},
		error: function() {
			alert("Something went wrong, try again later");
		}
	});
}

function createPriceMenu(data) {
	let coveredTypes = [];
	$('#cjenik').append('<hr>');

	data.forEach(function(item) {
		if (!coveredTypes.includes(item.TYPE)) {
			coveredTypes.push(item.TYPE);
			$('#cjenik').append('<div id="cjenikType_' + item.TYPE + '"></div><hr>');


			$('#cjenikType_' + item.TYPE + '').append('<p class="cjenik-title">' + item.TYPE_TEXT + '</p>');

			$('#cjenikType_' + item.TYPE + '').append('<div id="cjenikTypeTuzemni_' + item.TYPE + '"></div>');
			$('#cjenikTypeTuzemni_' + item.TYPE + '').append('<p class="cjenik-subtitle">Tuzemni</p>');

			$('#cjenikType_' + item.TYPE + '').append('<div id="cjenikTypeInozemni_' + item.TYPE + '"></div>');
			$('#cjenikTypeInozemni_' + item.TYPE + '').append('<p class="cjenik-subtitle">Inozemni</p>');
		}

		if (item.LOCAL === 1) {
			$('#cjenikTypeTuzemni_' + item.TYPE + '').append('<div class="cjenik-item"><span>' + " Od: " + item.LOWER + "g, Do: " + item.UPPER +
				"g, Cijena: " + item.PRICE.toFixed(2) + "kn " + '</span><input type="number" min="0" step="0.01" value="' + item.PRICE.toFixed(2) + '"\
			 class="form-control newPriceInput" id="proracuniNewPrice_'+ item.ID + '"/><button class="btn btn-outline-secondary updatePriceButtons"\
			 id="updatePrice_'+ item.ID + '">Update</button></div>');
		} else {
			$('#cjenikTypeInozemni_' + item.TYPE + '').append('<div class="cjenik-item"><span>' + " Od: " + item.LOWER + "g, Do: " + item.UPPER +
				"g, Cijena: " + item.PRICE.toFixed(2) + "kn " + '</span><input type="number" min="0" step="0.01" value="' + item.PRICE.toFixed(2) + '"\
			 class="form-control newPriceInput" id="proracuniNewPrice_'+ item.ID + '"/><button class="btn btn-outline-secondary updatePriceButtons"\
			 id="updatePrice_'+ item.ID + '">Update</button></div>');
		}
		createListenerUpdatePriceButton(item.ID);
	});
}

function createListenerUpdatePriceButton(ID) {
	$('#updatePrice_' + ID).on("click", function() {
		let input = {
			price: $('#proracuniNewPrice_' + ID).val(),
			id: ID,
			operation: "proracuni_updatePrice"
		}

		$.ajax({
			type: "POST",
			url: 'Proracuni',
			dataType: 'json',
			data: input,
			success: function(data) {
				if (data.status == 'success') {
					$('#cjenik').empty();
					fillCjenik();
				} else {
					alert(data.message);
				}
			},
			error: function() {
				alert("Something went wrong, try again later");
			}
		});
	});
}





