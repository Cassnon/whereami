$(document).ready(function(){
    jQuery.ajax({
			contentType : 'application/json',
			url:'checklogin',
			dataType:'json',
			success:function(data){
				if(data.role === 1){
					loadadmin();
				}
				else{
					window.location.href="./login.html";
				}
			}
	});
	
	$("#new_site").on("click",function(){
		$("#new_site").addClass("site_select").removeClass("site_unselect");
		$("#new_user").addClass("user_unselect").removeClass("user_select");
		$("#site").show();
		$("#user").hide();
	})
	
	$("#new_user").on("click",function(){
		$("#new_user").addClass("user_select").removeClass("user_unselect");
		$("#new_site").addClass("site_unselect").removeClass("site_select");
		$("#site").hide();
		$("#user").show();
	})
	
	$("#site_btn").on("click",function(){
		$("#error1").html("");
		$("#error2").html("");
		$("#error3").html("");
		$("#error4").html("");
		$("#error5").html("");
		var site = serializeSite($("#site"));
		if (site){
			$.ajax({
				type:'POST',
	    		contentType : 'application/json',
	    		url:'savedchost',
	    		data:JSON.stringify(site),
	    		dataType:'text',
	    		success:function(data){
	    			alert(data);
	    			location.reload();
	    		}
			})
		}
	})
	
	$("#user_btn").on("click",function(){
		$("#error5").html("");
		$("#error6").html("");
		$("#error7").html("");
		var user = serializeUser($("#user"));
		if (user){
			$.ajax({
				type:'POST',
	    		contentType : 'application/json',
	    		url:'saveuser',
	    		data:JSON.stringify(user),
	    		dataType:'text',
	    		success:function(data){
	    			alert(data);
	    			location.reload();
	    		}
			})
		}
	})
});

//load the page information of the admin mode
function loadadmin(){
	$("#head").load("adminhead.html",function(){
		$("#logout").click(function(){
			jQuery.ajax({
				contentType : 'application/json',
				url:'logout',
				dataType:'json',
				success:function(result){
					window.location.href="./login.html";
				}
			})
		})
	});
	$("#footer").load("footer.html");
}

function serializeUser(obj){
	var exitSubmit = false; 
	var o = {};
	var a = obj.serializeArray();
	$.each(a, function() { 
		if (this.name=="username"){
			if($("#username").val() === ""){
				$("#error6").html("Username can't be empty!");
				exitSubmit = true;
				return false;
			}
			else
				o["username"] = $("#username").val();
		}
		if (this.name=="password"){
			if($("#password").val() ===""){
				$("#error7").html("Password can't be empty!");
				exitSubmit = true;
				return false;
			}
			else
				o["password"] = $.md5($("#password").val())
		}
		if ($("input[type='radio'][name='role']:checked").length == 0){
			$("#error8").html("You should choose a role");
			exitSubmit = true;
			return false;
		}
		else{
			o["role"] = $('input[type="radio"][name="role"]:checked').val(); 
		}
	});
	if (exitSubmit) {
		return false;
	}
	else{
		$("#error6").html("");
		$("#error7").html("");
		$("#error8").html("");
		return o;
	}
}

function serializeSite(obj){
	var exitSubmit = false; 
	var o = {};
	var a = obj.serializeArray();
	$.each(a, function() {    
		if (this.name=="name"){
			var nametest = new RegExp(/^[0-9a-zA-Z_]+/);
			if($("#name").val() === ""){
				$("#error1").html("Site name can't be empty!");
				exitSubmit = true;
				return false;
			}
			else if(!nametest.test($("#name").val())){
				$("#error1").html("Name invalid! You can only use English alphabets, Numbers and _");
				exitSubmit = true;
				return false;
			}
			o["name"] = $("#name").val();
		}
		if (this.name=="ip"){
			var iptest1 = new RegExp(/\w+\.\w+\.(\w+\.)*\w+/);
			var iptest2 = new RegExp(/^[0-9a-zA-Z_]+/);
			if($("#ip").val() === ""){
				$("#error2").html("IP can't be empty!");
				exitSubmit = true;
				return false;
			}
			else if((!iptest1.test($("#ip").val()) && !iptest2.test($("#ip").val())) || ($("#ip").val() == "localhost" || $("#ip").val() == "127.0.0.1")){
				//规则 要么满足hostname 英文加上数字  要么满足ip地址并且不是127.0.0.1或者localhost
				$("#error2").html("This value should be a hostname or an ip address(localhost or 127.0.0.1 are invalid!)");
				exitSubmit = true;
				return false;
			}
			else
				o["ip"] = $("#ip").val();
		}
		if (this.name == "en"){
			if($("#en").val() === ""){
				$("#error3").html("English name can't be empty");
				exitSubmit = true;
				return false;
			}
			else
				o["en"] = $("#en").val();
		}
		if (this.name == "chn_hk"){
			 if($("#chn_hk").val() === ""){
				 $("#error4").html("Chinese HongKong name can't be empty");
				 exitSubmit = true;
				 return false;
			 }
			 else
				 o["chn_hk"] = $("#chn_hk").val();
		}
		if (this.name == "chn_cn"){
			 if($("#chn_cn").val() === ""){
				 $("#error5").html("Chinese name can't be empty");
				 exitSubmit = true;
				 return false;
			 }
			 else
				 o["chn_cn"] = $("#chn_cn").val();
		}
	});
		   
	if (exitSubmit) {
	    return false;
	}
	else{
		o["port"] = 443;
		o["gps_lat"] = 0;
		o["gps_long"] = 0;
		o["defaultzoom"] = 1;
		return o;
	}
}