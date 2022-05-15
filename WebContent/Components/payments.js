//Hide the alerts on page load
$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	
	$("#alertError").hide();
});
// SAVE
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide();
	 
	// Form validation
	var status = validatePaymentForm();
	if (status != true)
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
	 }	
	// If valid
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
		{
			url : "PaymentsAPI",
			type : type,
			data : $("#formPayment").serialize(),
			dataType : "text",
			complete : function(response, status)
			{
				onItemSaveComplete(response.responseText, status);
			}
		});
});

// UPDATE
$(document).on("click", ".btnUpdate", function(event)
{		
	
	 $("#hidPaymentIDSave").val($(this).data("paymentid"));
	 $("#paymentMethod").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#address").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#nic").val($(this).closest("tr").find('td:eq(2)').text());
	 $("#amount").val($(this).closest("tr").find('td:eq(3)').text());
});

//DELETE
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
		{
			url : "PaymentsAPI",
			type : "DELETE",
			data : "paymentID=" + $(this).data("paymentid"),
			dataType : "text",
			complete : function(response, status)
			{
				onPaymentDeleteComplete(response.responseText, status);
			}
		});
});

// CLIENT-MODEL
function validatePaymentForm()
{
	// PAYMENT METHOD
	if ($("#paymentMethod").val().trim() == "")
	 {
		return "Insert Payment Method.";
	 }
	// ADDRESS
	if ($("#address").val().trim() == "")
	 {
		return "Insert Address.";
	 }
	//NIC
	if ($("#nic").val().trim() == "")
	 {
		return "Insert Nic.";
	 }
	// AMOUNT
	if ($("#amount").val().trim() == "")
	 {
		return "Insert Amount.";
	 }
	// is numerical value
	var tmpAmount = $("#amount").val().trim();
		if (!$.isNumeric(tmpAmount))
	 {
			return "Insert a numerical value for Amount.";
	 }
	// convert to decimal amount
	 $("#amount").val(parseFloat(tmpAmount).toFixed(2));

	return true;
}
function onPaymentSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidPaymentIDSave").val("");
	$("#formPayment")[0].reset();
}
function onPaymentDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}