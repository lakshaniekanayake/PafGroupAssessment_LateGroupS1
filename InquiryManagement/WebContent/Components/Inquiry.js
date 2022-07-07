$(document).ready(function() {

	$("#alertSuccess").hide();

	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "InquiryAPI",
			type: type,
			data: $("#formItem").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onItemSaveComplete(response.responseText, status);
			}
		});
});


function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidItemIDSave").val($(this).data("inqid"));
	$("#CustomerName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#Address").val($(this).closest("tr").find('td:eq(1)').text());
	$("#Date").val($(this).closest("tr").find('td:eq(2)').text());
	$("#Reason").val($(this).closest("tr").find('td:eq(3)').text());
});





//Delete=============================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "InquiryAPI",
			type: "DELETE",
			data: "inqID=" + $(this).data("inqid"),
			dataType: "text",
			complete: function(response, status) {
				onItemDeleteComplete(response.responseText, status);
			}
		});
});



function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}




// CLIENT-MODEL================================================================
function validateItemForm() {
	// Name
	if ($("#CustomerName").val().trim() == "") {
		return "Insert Name.";
	}
	// Address
	if ($("#Address").val().trim() == "") {
		return "Insert Address.";
	}
	// Date-------------------------------
	if ($("#Date").val().trim() == "") {
		return "Insert Date.";
	}

	// Reason------------------------
	if ($("#Reason").val().trim() == "") {
		return "Insert Reason";
	}

	return true;
}
