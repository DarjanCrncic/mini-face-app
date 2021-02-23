$(document).ready(function() {
	
	////// search configuration, configuring dropdowns on page load plus cofiguring filters and search success function
	
	$("#select-dropdown_0").select2({
		theme: "bootstrap",
		placeholder: "Select Advanced Filters",
		data: [{ id: "all", text: "All" }, { id: "title", text: "Title" }, { id: "body", text: "Body" }, { id: "name", text: "Posted By" }]
	});

	$("#select-logical-operand").select2({
		theme: "bootstrap",
		data: [{ id: "and", text: "AND" }, { id: "or", text: "OR" }]
	});
	
	addFilters([{ id: "all", text: "All" }, { id: "title", text: "Title" }, { id: "body", text: "Body" }, {id: "name", text: "Posted By"}]);
	prepareExecuteSearch(postSuccessFunction);
	
	/////////////////////// show all vissible posts on page load
	$.ajax({
		url: 'ShowPosts',
		dataType: 'json',
		type: "GET",
		success: function(data) {
			if (data.status == 'success') {
				for (var i = 0; i < data.data.length; i++) {
					if (userID == data.data[i].CREATOR_ID && data.data[i].TYPE == "user") {
						
						$('#ajaxShowVissiblePosts').append(MainObject.createPostHtml(data.data[i]));
						MainObject.addDeleteEditPostButtonListener(data.data[i]);
						
					} else {
						$('#ajaxShowVissiblePosts').append('<h3>' + data.data[i].TITLE + ' (' + data.data[i].NAME + ')</h3><p>' + data.data[i].BODY + '</p>');
					}
				}
			} else {
				alert(data.message);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('Something went wrong, try again later');
		}
	});

	//////////////////// create post script	
	$("#newPostButton").on("click", function() {

		let input = {
			title: $("#newPostTitleInput").val(),
			body: $("#newPostBodyInput").val(),
			operation: "add",
			postId: ""
		}

		$.ajax({
			type: "POST",
			url: 'CRUDPost',
			dataType: 'json',
			data: input,
			success: function(data) {
				if (data.status == 'success') {

					$('#ajaxShowVissiblePosts').prepend(MainObject.createPostHtml(data.data));

					MainObject.addDeleteEditPostButtonListener(data.data);

					$("#newPostTitleInput").val('');
					$("#newPostBodyInput").val('');
				} else {
					alert(data.message);
				}
			},
			error: function(data) {
				alert("Something went wrong, try again later");
			}
		});
	});



	//////////////////////// on click modal function for confirming post edit
	$('#confirmEditButton').click(function(e) {
		e.preventDefault();

		let input = {
			title: $("#editModalPostTitle").val(),
			body: $("#editModalPostBody").val(),
			operation: "edit",
			postId: $("#editPostModalForm").val()
		}

		$("#editPostModal").modal('hide');

		$.ajax({
			type: "POST",
			url: 'CRUDPost',
			dataType: 'json',
			data: input,
			success: function(data) {
				if (data.status == 'success') {

					$("#title_" + data.data.ID).text(data.data.TITLE + ' (' + data.data.NAME + ')');
					$("#body_" + data.data.ID).text(data.data.BODY);

				} else {
					alert(data.message);
				}
			},
			error: function(data) {
				alert("Something went wrong, try again later");
			}
		});

	});


});
