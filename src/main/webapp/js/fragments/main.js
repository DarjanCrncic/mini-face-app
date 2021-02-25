
const MainObject = {

	loadPrimary: function(HTMLName, unloadSecondary, callback) {
		$("#primary").load(HTMLName, function() {
			if (callback) callback();
		});
		if (unloadSecondary) {
			$("#secondary").empty();
		}
	},

	loadSecondary: function(HTMLName, unloadPrimary, callback) {
		$("#secondary").load(HTMLName, function() {
			if (callback) callback();
		});
		if (unloadPrimary) {
			$("#primary").empty();
		}
	},

	loadTertiary: function(HTMLName, unloadSecondary, callback) {
		$("#tertiary").load(HTMLName, function() {
			if (callback) callback();
		});
		if (unloadSecondary) {
			$("#secondary").empty();
		}

	},

	unloadPrimary: function() {
		$("#primary").empty();
	},

	unloadSecondary: function(showPrimary, callback) {
		$("#secondary").empty();
		if (showPrimary) {
			$("#primary").show();
		}
		callback();
	},

	unloadTertiary: function(showSecondary, callback) {
		$("#tertiary").empty();
		if (showSecondary) {
			$("#secondary").show();
		}
		callback();
	},

	navigationPage: function(id) {
		MainObject.loadPrimary("html/fragments/" + id + ".html", false);
	},

	findMaxIdInClass: function(className) {
		let max = 0;
		$("." + className).each(function(index, object) {
			const currentID = this.id.replace(/[^0-9]/g, '');
			if (currentID > max) {
				max = currentID;
			}
		});
		return parseInt(max);
	}
	
}

