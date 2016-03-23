// Login processing
$("#loginButton").click(processLogin);
$("#registerButton").click(processRegister);

function processLogin(e) {
	var username = $("#username").val();
	var password = $("#password").val();
	
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
			switch (xhttp.responseText) {
			case "bad_login":
				$("#message").html("Wrong username - password");
				break;
			case "good_login":
				//window.location = "/Dominion-server/welcome.jsp";
				break;
			case "user_registered":
				$("#message").html("User registered");
				break;
			case "user_not_found":
				$("#message").html("No such user, please register");
				break;
			default:
				$("body").html(xhttp.responseText);
			}
		}
	}
};

function processRegister(e) {
	var username = $("#username").val();
	var password = $("#password").val();
	
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
			switch (xhttp.responseText) {
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
				break;
			default:
				$("body").html(xhttp.responseText);
			}
		}
	}
}