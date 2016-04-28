// Login processing
$("#loginButton").click(processLogin);
$("#registerButton").click(processRegister);
$(document).keypress(keyPress);

function keyPress(e) {
	switch (e.which) {
	case 13:
		$("#loginButton").click();
	}
}

function processAjaxResponse(response) { // Processes the AJAX response into a message for the user
	switch (response) {
	case "bad_login":
		$("#message").html("Wrong username - password");
		break;
	case "good_login":
		//window.location = "/Dominion-server/welcome.jsp";
		break;
	case "user_not_found":
		$("#message").html("No such user, please register");
		break;
	case "user_not_available":
		$("#message").html("User already exists");
	case "user_registered":
		$("#message").html("User succesfully registered");
		break;
	case "general_error":
		$("#message").html("The server ran into some trouble :(");
		break;
	default:
		$("body").html(xhttp.responseText);
	}
}

function checkInput() {
	if ($("#username").val() != "" && $("#password").val() != "") {
		return true;
	}
	else {
		$("#message").html("Please fill in all fields");
		return false;
	}
}

function processLogin(e) {
	if (checkInput()) {
		var username = $("#username").val();
		var password = $("#password").val();
		$("#message").html("...");
		/*
		$.ajax({
			type: 'POST',
			url: '/LoginController',
			dataType: 'json',
			data: $("#loginForm").serialize(),
			statusCode: {
				403: function(e) {
					$("message").html("Wrong username / password");
				}
			}
		});
		*/
		
		var xhttp;
		if (window.XMLHttpRequest) {
			xhttp = new XMLHttpRequest();
		}
		else {
			xhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		xhttp.open("POST", "LoginController", true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("username=" + username + "&password=" + password + "&action=login");
		
		xhttp.onreadystatechange = function() {
			console.log(xhttp.response);
			if (xhttp.readyState == 4) {
				processAjaxResponse(xhttp.responseText);
			}
		}
	}
};

function processRegister(e) {
	if (checkInput()) {
		var username = $("#username").val();
		var password = $("#password").val();
		$("#message").html("...");
		
		var xhttp;
		if (window.XMLHttpRequest) {
			xhttp = new XMLHttpRequest();
		}
		else {
			xhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		xhttp.open("POST", "LoginController", true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("username=" + username + "&password=" + password + "&action=register");
		
		xhttp.onreadystatechange = function() {
			console.log(xhttp.response);
			if (xhttp.readyState == 4) {
				processAjaxResponse(xhttp.responseText);
			}
		}
	}
}