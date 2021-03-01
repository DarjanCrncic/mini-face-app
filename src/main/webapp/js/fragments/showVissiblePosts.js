$(document).ready(function() {
	
	////// search configuration, configuring dropdowns on page load plus cofiguring filters and search success function
	
	$("#select-dropdown_0").select2({
		theme: "bootstrap",
		placeholder: "Select Advanced Filters",
		data: [{ id: "all", text: "All" }, { id: "fp.title", text: "Title" }, { id: "fp.body", text: "Body" }, { id: "fu.name+fu.surname", text: "Posted By" }]
	});

	$("#select-logical-operand").select2({
		theme: "bootstrap",
		data: [{ id: "and", text: "AND" }, { id: "or", text: "OR" }]
	});
	
	$("#select-word-position").select2({
		theme: "bootstrap",
		data: [{ id: "both", text: "Every position " }, { id: "front", text: "Starting with" }, { id: "back", text: "Ending with" }]
	});
	
	addFilters([{ id: "all", text: "All" }, { id: "fp.title", text: "Title" }, { id: "fp.body", text: "Body" }, {id: "fu.name+fu.surname", text: "Posted By"}]);
	prepareExecuteSearch(postSuccessFunction, 'ShowPosts');
	
	PostsPageObject.showAllVissiblePosts("all", 0);

	PostsPageObject.createPostScriptInit("user", 0);

	PostsPageObject.editPostScriptInit();
	

});
