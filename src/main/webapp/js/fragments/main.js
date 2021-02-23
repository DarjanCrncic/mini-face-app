
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
	},

	////////////////////// delete and edit post on click function
	addDeleteEditPostButtonListener: function(data) {
		$('#' + data.ID).click(function() {

			let input = {
				title: data.TITLE,
				body: data.BODY,
				operation: "delete",
				postId: data.ID
			}

			$.ajax({
				type: "POST",
				url: 'CRUDPost',
				dataType: 'json',
				data: input,
				success: function(data) {
					if (data.status == 'success') {
						$("#postDiv_" + data.data.ID).empty();
						$("#postDiv_" + data.data.ID).remove();
					} else {
						alert(data.message);
					}
				},
				error: function(data) {
					alert("Something went wrong, try again later");
				}
			})
		});

		// configure modal for designated post
		$('#editPost_' + data.ID).click(function() {
			$("#editPostModal").modal('show');
			$("#editModalPostBody").val(data.BODY);
			$("#editModalPostTitle").val(data.TITLE);
			$("#editPostModalForm").val(data.ID);
		});
	},

	//////////////////// create html element for post item, created by current user
	createPostHtml: function(data) {
		return ('<div id="postDiv_' + data.ID + '"><span><h3 class="post-title-username" id="title_' + data.ID + '">' + data.TITLE + ' (' + data.NAME + ')</h3>\
				<button class="far fa-trash-alt fa-lg delete-button" id=' + data.ID + '></button>\
				<button class="fas fa-edit fa-lg edit-button" id="editPost_'+ data.ID + '"></button>\
				</span><p id="body_'+ data.ID + '">' + data.BODY + '</p></div>');
	}
}

