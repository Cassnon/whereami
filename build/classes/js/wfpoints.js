$(document).ready(function(){

  var scale = 1;
  var areaId = -1;
  var curId = -1;
  var flag = -1;
  var max_pointid = 0;

  var myCanvas = $("#map").get(0); 
  var context = myCanvas.getContext("2d");
  var mapstage = new createjs.Stage("map");
  mapstage.enableMouseOver(20);
  var cur_container = new createjs.Container();
  var exist_container = new createjs.Container();
  var points_container = new createjs.Container();
  var Bitmap = new createjs.Bitmap();
  mapstage.addChild(cur_container);
  mapstage.addChild(exist_container);
  mapstage.addChild(points_container);
  myCanvas.addEventListener("click", canvasclick,false);
  
  var cur_pointimg = new Image();
  cur_pointimg.src = "./img/cur_point.png"
  cur_pointimg.onload = function () { 
	  cur_bitmap = new createjs.Bitmap(cur_pointimg);
  };
  var pointimg = new Image();
  pointimg.src = "./img/point.png"
  pointimg.onload = function () { 
	  bitmap = new createjs.Bitmap(pointimg);
  };
  
  $("#small").click(function(){
	  if(scale > 0.4){
		  scale = scale-0.3;
		  $("#show_map").click();
	  } 
	  else {
		  return;
	  }
  })
  
  $("#narmal").click(function(){
  	  if(scale < 1){
		  scale = scale+0.3;
		  $("#show_map").click();
  	  } 
	  else {
		  return;
	  }
  })
  
  jQuery.ajax({
		contentType : 'application/json',
		url:'checklogin',
		dataType:'json',
		success:function(data){
			if(data.role === 1){
				loadadmin();
				loadBuilding();
			} 
			else if (data.role === 0){
				loaduser();
				loadBuilding();
			}
			else if(data.role === -1){
				window.location.href="./login.html";
			}
		}
	})
  
  function loadBuilding(){
	  jQuery.ajax({
		contentType : 'application/json',
		url:'loadBuilding',
		dataType:'json',
		success:function(data){
			buildinglist = new Array();
			arealist = new Array();
			for(var item in data){
				buildinglist[item] =  JSON.parse(data[item].name)[0].en;
				arealist[item] = data[item].areas.split(",");
			}
			jQuery.ajax({
				contentType : 'application/json',
				url:'loadAreaList',
				dataType:'json',
				success:function(data1){
					var optionstring = "";
					for(var a in arealist){
						for(var c in arealist[a]){
							for(var b in data1){
								if(arealist[a][c]==data1[b]._id){
									optionstring +="<option value = "+data1[b]._id+ ">"+buildinglist[a]+", "+JSON.parse(data1[b].name)[0].en+"</option>";
								}
							}
						}	
					}
					$("#area").html(optionstring);
				}
			});
		}
	  });
  }
  
  function in_array(stringToSearch, arrayToSearch) {
	  for (s = 0; s < arrayToSearch.length; s++) {
		  thisEntry = arrayToSearch[s].toString();
		  if (thisEntry == stringToSearch.toString()) {
			  return true;
		  }
	  }
	  return false;
  }
  
  //get the text of selectedbox
  $.fn.getSelectedText=function(){
	if(this.size()!==0) {
		var index = jQuery(this).get(0).selectedIndex;
		return jQuery(this).get(0).options[index].text;
	}
  }
  
 
  $("#show_map").click(function(){
	  var MapImage = new Image();

  	  jQuery.ajax({
		type:'POST',
		contentType : 'application/json',
		url:'showmap',		
		data:$("#area").val(),
		dataType:'text',
		beforeSend:function(){
	           $("#loading").css('visibility','visible');
	    },
		success:function(data){
			cur_container.removeAllChildren();	
			exist_container.removeAllChildren();	
			points_container.removeAllChildren();	 				
			MapImage.src="data:image/png;base64,"+data;
	    	myCanvas.width = MapImage.width*scale;
	        myCanvas.height = MapImage.height*scale;
	        $("#map").css("background","url("+MapImage.src+")");
	        $("#map").css("background-size","100% 100%");
	        jQuery.ajax({
				type:'POST',
				contentType : 'application/json',
				url:'loadPolygon',		
				data:$("#area").val(),
				dataType:'json',
				success:function(data){
					for(var i in data){
						pol_container = new createjs.Container();	
						curPlg_container = new createjs.Container();	
						curP_container = new createjs.Container();	
						pol_container.addChild(curPlg_container);
						pol_container.addChild(curP_container);						  
						var polyPoints = JSON.parse(data[i].vertex);

						var polygon = new createjs.Graphics();
						polygon.beginFill("#00F").moveTo(polyPoints[0]*scale,polyPoints[1]*scale);
						for(var k=2;k <polyPoints.length;k=k+2){
							polygon.lineTo(polyPoints[k]*scale,polyPoints[k+1]*scale);					
						}
						polygon.lineTo(polyPoints[0]*scale,polyPoints[1]*scale);
					 	var p = new createjs.Shape(polygon);						
						p.alpha = 0.2;
						pol_container.getChildAt(0).addChild(p);
						exist_container.addChild(pol_container);		
					}
					mapstage.update();				
				}
			});
		},
		complete: function() {	 
			$("#loading").css('visibility','hidden');
			$("#areacode").val($("#area").val())
	    }
	});
	  

  })
  
  
  mapstage.mouseChildren = true;
  exist_container.removeAllChildren();
  cur_container.removeAllChildren();
  mapstage.update();


	
	$("#submit").click(function(){ 			

		  var pointlist = points_container.children;
		  var polyStr = "\"areaCode\":\""+$("#areacode")[0].value+"\"";
		

		  pointJs ="{\"pointsList\":[";
		  k = 0;
		  for (var p = 0; p < pointlist.length; p++) {
				var id = p;
			  	var x = pointlist [p].x;	  			  	
			  	var y = pointlist [p].y;	
			  	var connStr = {"_id":k,"x":x+5,"y":y+5};
			  	var l = JSON.stringify(connStr);
			  	pointJs += l;
			  	if( p != pointlist.length-1){
			  		pointJs += ",";
			    }
			  	k = k +1;
	      }
		  pointJs +="],";
		  pointJs += polyStr+"}";

		  
	    	jQuery.ajax({
	    		type:'POST',
	    		contentType : 'application/json',
	    		url:'savepoints',
	    		data:pointJs,
	    		dataType:'json',
	    		success:function(data){
	    			alert("Saved successfully!");
	    		}
	    	});
	 
	  })
	  
	  
	  
	$("#showpoint").click(function(){ 		
	
		cur_container.removeAllChildren();	
		points_container.removeAllChildren();
		mapstage.update();
		var url = "http://localhost/Demo/assets/dcPkgs/"+$("#areacode")[0].value+".zip";
		
		jQuery.ajax({
			type:'POST',
			contentType : 'application/json',
			url:'showpoints',
			data:url,
			dataType:'json',
			success:function(data){
				for(var i in data){	  
					var pointid = data[i]._id;
					var x = data[i].x;
					var y = data[i].y;
					var points = new createjs.Bitmap(pointimg);
					points.name = "point_"+pointid;
					max_pointid = parseInt(pointid);
					points.x=(x-5)*scale;
					points.y=(y-5)*scale;
					points.addEventListener("click",bitmapClick);
					points.cursor = "pointer";
					points_container.addChild(points);	
				}
			    //mapstage.addChild(points_container);
				mapstage.update();			
			}
		
		})
		
	})
	
	
	
	
    function bitmapClick(event) {
          cur_container.removeAllChildren();

		  while(cur_container.getNumChildren()!=0){

			  points_container.addChild(cur_container.getChildAt(0));
		  }
          event.target.addEventListener("pressmove", function(evt) {
			    evt.target.x = evt.stageX - 5;
			    evt.target.y = evt.stageY - 5;
		  });
		  var logoPosString = Math.round(event.target.x/scale+5).toString()+", "+Math.round(event.target.y/scale+5).toString()
		  $("#p_pos").val(logoPosString);
		  $("#p_id").val((event.target.name).split("_")[1]);
		  curId = (event.target.name).split("_")[1];
		  flag = 1;
		  mapstage.update();
	 }


	function canvasclick(evt){
		var mousePos = getMousePos(myCanvas, evt);
		if(flag < 0){
			$("#p_id").val("");
			myCanvas.addEventListener("click", canvasclick,false);
			cur_container.removeChild(cur_bitmap);
			
			cur_bitmap.x = mousePos.x-5;
			cur_bitmap.y = mousePos.y-5;
			cur_container.addChild(cur_bitmap);
			mapstage.update();

			$("#p_pos").val(parseInt(mousePos.x/scale) +',' + parseInt(mousePos.y/scale));
			
		}
		flag = -1;		
	}
	
	
	  //Get Mouse Position 
	function getMousePos(myCanvas, evt) { 
		var rect = myCanvas.getBoundingClientRect(); 
		return { 
			x: evt.clientX - rect.left * (myCanvas.width / rect.width),
			y: evt.clientY - rect.top * (myCanvas.height / rect.height)
				}
		}
	
	
	$("#addpoint").click(function(){
		  var points = new createjs.Bitmap(pointimg);
		  max_pointid++;
		  var pointid = (String)(max_pointid);
		  var poinsNum = points_container.getNumChildren();   
		  points.x = cur_bitmap.x;
		  points.y = cur_bitmap.y;
		  points.cursor = "pointer";
		  points.name = "point_"+pointid;
		  $("#p_id").val(pointid);
		  cur_container.removeChild(poinsNum);
		  points_container.addChild(points);
		  points.addEventListener("click",bitmapClick);
		  curId  = pointid;
		  mapstage.update();
	})
	
	$("#delete").click(function(){	

		var point = points_container.getChildByName ( "point_"+curId )
		points_container.removeChild(point);
		$("#p_id").val("");
		$("#p_pos").val("");
		mapstage.update();
		//if the curID is the max one, max_point-1
		if(curId == max_pointid)
			max_pointid --;
	})
	
	//
	$("#edit").click(function(){
		if(curId != $("#p_id").val()){
			alert("Please select a point");
		}
		else{
			$("#p_pos").removeAttr('disabled');
			$("#p_pos").focus().select();
		}
	})
	
	$("#p_pos").blur(function(){
		$("#p_pos").attr('disabled','disabled');
	})
	
	$("#save").click(function(){
		if(curId != $("#p_id").val()){
			alert("Point selection error");
		}
		else{
			position = $("#p_pos").val().split(",");
			if(position.length != 2){
				alert("Position edit error");
			}
			else{
				numexp = new RegExp(/\d/);
				if (numexp.test(position[0]) && numexp.test(position[1])){
					//delete and new
					var point = points_container.getChildByName ( "point_"+$("#p_id").val());
					points_container.removeChild(point);
					var points = new createjs.Bitmap(pointimg);
					var pointid = $("#p_id").val();
					points.x = position[0] - 5;
					points.y = position[1] - 5;
					points.cursor = "pointer";
					points.name = "point_"+pointid;
					points_container.addChild(points);
					points.addEventListener("click",bitmapClick);
					mapstage.update();
				}
				else{
					alert("Position must be a num");
				}
			}
		}
	})
})
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
	var gen_points = $('<div>',{'id':'gen_points','class':'roundborder double_height'});
	var title = $('<div>',{'class':'title'}); 
	title.html("Generate Points");
	var content1 = $('<div>',{'class':'content'});
	var div1 = $('<div>',{'class':'col-xs-4 col-lg-4 col-sm-4'});
	var label = $('<label>',{'for':'grid_size'});
	label.html("Grid Size:");
	var desc = $('<div>', {'class':'col-xs-12 col-lg-12 col-sm-12'});
	desc.html("(Determine the segmentation size of the original map, which is negative corelated to point generation density.)");
	div1.append(label);
	var div2 = $('<div>',{'class':'col-xs-8 col-lg-8 col-sm-8'});
	var gridsize = $('<input type = "text" id="grid_size" name = "grid_size">');
	div2.append(gridsize);
	content1.append(div1);
	content1.append(div2);
	content1.append(desc);
	var content2 = $('<div>',{'class':'content','style':'padding-top:30px'}); 
	var genpoint = $('<a>',{'id':'genpoint','class':'Btn btn btn-primary single_btn'});
	genpoint.html("Generate Points");
	gen_points.append(title);
	content2.append(genpoint);
	gen_points.append(content1);
	gen_points.append(content2);
	gen_points.insertBefore("#points");
	numstr = RegExp(/^(\d)+$/); 
	$("#genpoint").click(function() {
		  if($("#grid_size").val() == ""){
			  alert("Grid Size should not be empty!");
		  }
		  else{
			 if(!numstr.test($("#grid_size").val())){
			  	  alert("Grid Size should be an integer!");
		  	 }
		  	 else{
		  		  str = {"area":$("#area").val(),"gridsize":$("#grid_size").val()};
				  jQuery.ajax({
				  		type:'POST',
				  		contentType: 'application/json',
				  		url:'genpoints',
				  		data:JSON.stringify(str),
				  		dataType:'text',
				  		success:function(data) {
				  			alert(data);
				  		}
			  	  });
		  	 }
		  }
	 });
}

function loaduser(){
	$("#head").load("head.html",function(){
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
