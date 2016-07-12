$(document).ready(function(){
	
	$("#alert").css("visibility","hidden")
	
	 jQuery.ajax({
		    type:'GET',
			contentType : 'application/json',
			url:'gethosts',	
			dataType:'json',
			success:function(data){
				var optionstring = "";
				for(var i in data){
					optionstring +="<option value = "+data[i]._id+ ">" +data[i].name+"</option>"	
				}
				$("#siteselect").html(optionstring);
				},
			})
	
	 $("#login").click(function(){
		 
		 var loginstr = {"username": $("#username").val(),"password": $.md5($("#password").val()),"site":$("#siteselect").val()};
		 jQuery.ajax({
			    type:'POST',
				contentType : 'application/json',
				url:'loginaction',	
				data:JSON.stringify(loginstr),
				dataType:'json',
				success:function(data){
					if(data.isSuccess === 1){
						window.location.href="./polygon.html";
					}
					else{
						$("#alert").css("visibility","visible")
					}
				}           
			})
	 })
                	       
})

