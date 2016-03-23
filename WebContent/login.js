// Login processing
$("#loginForm input[type=button]").click(processLogin);
//$("#loginForm input[type=button] #register").click(processRegister);

function processLogin(e) {
	//e.preventDefault();
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
	xhttp.send("username=" + username + "&password=" + password);
	
	xhttp.onreadystatechange = function() {
		console.log(xhttp.response);
		if (xhttp.readyState == 4) {
			if (xhttp.readyState == 4 && xhttp.responseText == "403_bad_login") {
				$("#message").html("Wrong username - password")
			}
			else if (xhttp.responseText = "200_good_login") {
				window.location = "/Dominion-server/welcome.jsp";
			}
		}
	}
};