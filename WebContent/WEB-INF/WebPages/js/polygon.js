$(document).ready(function(){
		  
	  var scale = 1;
	  
	  var myCanvas = $("#map").get(0); 
	  var context = myCanvas.getContext("2d");
	  var mapstage = new createjs.Stage("map");
	  mapstage.enableMouseOver(20);
	  
	  var cur_container = new createjs.Container();
	  var exist_container = new createjs.Container();
	  var curP_con = new createjs.Container();
	  var exP_con = new createjs.Container();
	  var temP_con = new createjs.Container();
	  
	  var ifFirst = 1;

	  var addtag = 0;
	  var site = null;
	  
	  var maxconnectorid = 0; //最大的connectorID
	  var maxregionid = 0; // 最大的regionID
	  var head;//区域前缀
	  
	  mapstage.addChild(exP_con);
	  mapstage.addChild(curP_con);
	  mapstage.addChild(temP_con);
	  
	  mapstage.addChild(cur_container);
	  mapstage.addChild(exist_container);
	  var cur_points = new Array();
	  var pointsList = new Array();
		  
	  var ex_pointimg = new Image();
	  ex_pointimg.src = "./img/poly_point.png"	  
		  
	  var pointimg = new Image();
	  pointimg.src = "./img/P_point1.png"	
		  
	  var connimg = new Image();
	  connimg.src = "./img/conn.png"	
	  
	  var C_connimg = new Image();
	  C_connimg.src = "./img/c_conn.png"	

		  
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
				//get the max region id of this area
				jQuery.ajax({
					contentType : 'application/json',
					url:'getmaxregionid',
					dataType:'text',
					success:function(data){
						head = data.substring(0,data.lastIndexOf("_")+1);
						maxregionid = data.substring(data.lastIndexOf("_")+1,data.length);
					}
				});
			} 
			else if (data.role === 0){
				loaduser();
				//get the max region id of this area
				jQuery.ajax({
					contentType : 'application/json',
					url:'getmaxregionid',
					dataType:'text',
					success:function(data){
						head = data.substring(0,data.lastIndexOf("_")+1);
						maxregionid = data.substring(data.lastIndexOf("_")+1,data.length);
					}
				});			
			}
			else if(data.role === -1){
				window.location.href="./login.html";
			}
		}
	})
	
	function getRadioText(){
		var item = $(':radio[name="modeltype"]:checked').val();
		return item
	}
	  
	function getFPRadioText(){
		var item = $(':radio[name="PFtype"]:checked').val();
		return item
	}
		  
	 
	//show map and polygon
	$("#show_map").click(function(){
		$("#selectMod option").filter(function () { return $(this).html() == "Polygon Mode"; }).prop("selected",true);
		$("#selectMod").change();
		var MapImage = new Image();
		cur_container.removeAllChildren();
		exist_container.removeAllChildren();
		exP_con.removeAllChildren();
		curP_con.removeAllChildren();
		var areaid = $("#area").val()
	  
	  
		jQuery.ajax({
			type:'POST',
			contentType : 'application/json',
			url:'showmap',		
			data:areaid,
			dataType:'text',
			beforeSend:function(){
				$("#loading").css('visibility','visible');
			},
			success:function(data){
				MapImage.src="data:image/png;base64,"+data;
  	    	   	myCanvas.width = MapImage.width*scale;
  	    	   	myCanvas.height = MapImage.height*scale;
  	    	   	$("#map").css("background","url("+MapImage.src+")");
  	    	   	$("#map").css("background-size","100% 100%");
		       
  	           jQuery.ajax({
					type:'POST',
					contentType : 'application/json',
					url:'loadPolygon',		
					data:areaid,
					dataType:'json',
					success:function(data){
						for(var i in data){
							pol_container = new createjs.Container();	
							curPlg_container = new createjs.Container();	
							curP_container = new createjs.Container();	
							pol_container.addChild(curPlg_container);
							pol_container.addChild(curP_container);
  
							var polyPoints = JSON.parse(data[i].vertex);

							for(var x in polyPoints){
								
								if(x%2 == 1){								
									var p_point = new createjs.Bitmap(ex_pointimg);
									p_point.name="point_"+(x-1)/2;
									p_point.x = (polyPoints[x-1]-10)*scale;
									p_point.y = (polyPoints[x]-10)*scale;
									curP_container.addChild(p_point);	
								}
							}

							var polygon = new createjs.Graphics();
							polygon.beginFill("#00F").moveTo(polyPoints[0]*scale,polyPoints[1]*scale);
							for(var k=2;k <polyPoints.length;k=k+2){
								polygon.lineTo(polyPoints[k]*scale,polyPoints[k+1]*scale);					
							}
							polygon.lineTo(polyPoints[0]*scale,polyPoints[1]*scale);
							
						 	var p = new createjs.Shape(polygon);
							p.addEventListener("click",clickPlg);
							p.alpha = 0.2;
							p.cursor = "pointer";
							pol_container.getChildAt(0).addChild(p);
							var text = new createjs.Text((data[i].polyid).toString(), "40px Comic Sans MS", "#000000");
							text.x=(getCentrP(polyPoints).x-10)*scale;
							text.y=(getCentrP(polyPoints).y-20)*scale;
							text.id= data[i]._id;
							pol_container.addChild(text);
							exist_container.addChild(pol_container);		
						}
						mapstage.update();			
						
					}
				});    
		    },
		    complete: function() {
				 
				 $("#loading").css('visibility','hidden')
	        }
		});
	})
  
	  
	//get the text of selectedbox
	$.fn.getSelectedText=function(){
		if(this.size()!==0) {
			var index = jQuery(this).get(0).selectedIndex;
			return jQuery(this).get(0).options[index].text;
		}
	}
		
	//bind data to "site" and "Area"

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
									optionstring +="<option value="+data1[b]._id+">"+buildinglist[a]+","+JSON.parse(data1[b].name)[0].en+"</option>"
								}
							}
						}	
					}

					$("#area").html(optionstring);
				}
			})
		}
	});
				

	//Get Mouse Position 
	function getMousePos(myCanvas, evt) { 
		var rect = myCanvas.getBoundingClientRect(); 
		return { 
			x: parseInt(evt.clientX - rect.left * (myCanvas.width / rect.width)),
			y: parseInt(evt.clientY - rect.top * (myCanvas.height / rect.height))
		}
	}
		
	$("#add_point").click(function(){
		if(cur_container.getNumChildren()!=0){
			removeAllclickPlg();
			myCanvas.addEventListener("click", addPoint, false);
		}			
	})
	
	function addPoint(evt){	
		var mousePos = getMousePos(myCanvas, evt);
		var curP_con = cur_container.getChildAt(0).getChildAt(1);
		var curPoly_con = cur_container.getChildAt(0).getChildAt(0);
		var list = curP_con.children;
		var ve = new Array();
		for(var i in list){
			ve.push(list[i].x);
			ve.push(list[i].y);
		}
		ve.push(mousePos.x-8);
		ve.push(mousePos.y-8);
		var vejs = {"vertexes":ve}
		jQuery.ajax({
			type:'POST',
			contentType :'application/json',
			url:'resort',
			data:JSON.stringify(vejs),
			dataType:'json',
			success:function(data){
				cur_container.getChildAt(0).getChildAt(1).removeAllChildren();
				curP_con.removeAllChildren();
				for(var t in data){
					if(t%2==1){
						var new_point = new createjs.Bitmap(ex_pointimg);
						new_point.x = data[t-1];
						new_point.y =data[t];	
						new_point.name="point_"+cur_container.getChildAt(0).getChildAt(1).getNumChildren ();
						cur_container.getChildAt(0).getChildAt(1).addChild(new_point);
					}
				}
						
				mapstage.update();
						
				curP_con=cur_container.getChildAt(0).getChildAt(1);
				var cur_polygon = new createjs.Graphics();
				cur_polygon.beginFill("#00F").moveTo((curP_con.getChildAt(0).x+10),(curP_con.getChildAt(0).y+10));
				for(var i=1;i <curP_con.getNumChildren ();i=i+1){
						cur_polygon.lineTo((curP_con.getChildAt(i).x+10),(curP_con.getChildAt(i).y+10));					
				}
				var p = new createjs.Shape(cur_polygon);
				p.addEventListener("click",clickPlg);
				p.cursor = "pointer";
				p.alpha = 0.5;
				curPoly_con.addChildAt(p,0);
				curPoly_con.removeChildAt(1);
				//get vertexes list of this polygon 
				var pointsList = new Array();
					for (var p in curP_con.children){
					 	pointsList.push(curP_con.children[p].x);
					 	pointsList.push(curP_con.children[p].y);
					 }
					 	
					 cur_container.getChildAt(0).getChildAt(2).x = (getCentrP(pointsList).x-10);
					 cur_container.getChildAt(0).getChildAt(2).y = (getCentrP(pointsList).y-10);
					 mapstage.update();
				}
		})
		myCanvas.removeEventListener("click", addPoint, false);
		addAllclickPlg();
	}		 	 
			 

		
	$("#del_point").click(function(){			 
		var curP_con = cur_container.getChildAt(0).getChildAt(1);
		var curPoly_con = cur_container.getChildAt(0).getChildAt(0);
		var list = curP_con.children;
			 
		//if points of this polygon less than three
		if(curP_con.children.length<=3) {
			alert("Can't Remove this Points!");
			return
		}
			 
		for(i in list){
				 	if (list[i].image == pointimg)
				 		{				 		
				 			var delPName = list[i].name;
				 			curP_con.removeChildAt (i);
				 		
					 	var cur_polygon = new createjs.Graphics();
						cur_polygon.beginFill("#00F").moveTo(curP_con.getChildAt(0).x+10,curP_con.getChildAt(0).y+10);
						for(var i=1;i <curP_con.getNumChildren ();i=i+1)
							{
								cur_polygon.lineTo(curP_con.getChildAt(i).x+10,curP_con.getChildAt(i).y+10);					
							
							}
//						cur_polygon.beginFill("#00F").moveTo((curP_con.getChildAt(0).x+10)*scale,(curP_con.getChildAt(0).y+10)*scale);
//						for(var i=1;i <curP_con.getNumChildren ();i=i+1)
//							{
//								cur_polygon.lineTo((curP_con.getChildAt(i).x+10)*scale,(curP_con.getChildAt(i).y+10)*scale);					
//							
//							}
					 	var p = new createjs.Shape(cur_polygon);
						p.addEventListener("click",clickPlg);
						p.cursor = "pointer";
					 	p.alpha = 0.5;
					 	curPoly_con.addChildAt(p,0);
					 	curPoly_con.removeChildAt(1);
					 	//get vertexes list of this polygon 
					 	var pointsList = new Array();
					 	for (var p in curP_con.children)
					 		{
					 			pointsList.push(curP_con.children[p].x);
					 			pointsList.push(curP_con.children[p].y);
					 		}
					 	
					 	cur_container.getChildAt(0).getChildAt(2).x = (getCentrP(pointsList).x-10)*scale;
					 	cur_container.getChildAt(0).getChildAt(2).y = (getCentrP(pointsList).y-10)*scale;				 		
				 	}
				 }
			 mapstage.update();
		 })
			 
		
		$("#del_p").click(function(){
			
			cur_container.removeAllChildren();
			mapstage.update();		
		})
		 
		$("#add_p").click(function(){
			 addtag = 1;
			 exist_container.mouseEnabled  = false;
			 if(cur_container.getNumChildren()!=0){
					resetAlpha();
					var list = cur_container.getChildAt(0).getChildAt(1).children;
					for (i in list){
						list[i].image = ex_pointimg;
						list[i].removeEventListener("click",ClickPoints);
						list[i].cursor = "default";
					}
					cur_container.getChildAt(0).getChildAt(0).getChildAt(0).cursor = "default";
					exist_container.addChild(cur_container.getChildAt(0));					
			 }
			 
			 myCanvas.addEventListener("click", canvasclick,false);
			 polygon = new createjs.Container();	
			 curPlg_container = new createjs.Container();	
			 curP_container = new createjs.Container();	
			 curL_container = new createjs.Container();
			 polygon.addChild(curPlg_container);
			 polygon.addChild(curP_container);
			 polygon.addChild(curL_container);
		})

		function canvasclick(evt){			 
			var mousePos = getMousePos(myCanvas, evt);
			var p_point = new createjs.Bitmap(ex_pointimg);
			p_point.name="point_"+curP_container.getNumChildren ();
			p_point.x = mousePos.x-10;
			p_point.y = mousePos.y-10;	
			cur_points.push(mousePos.x);
			cur_points.push(mousePos.y);
			
			if(ifFirst == 1){
				curP_container.addChild(p_point);
				p_point.addEventListener("click",firstPoint);
				p_point.cursor = "pointer";
				ifFirst = 0;			
			}
			else{		
				var line = new createjs.Graphics();
				var lastPointX = cur_points[cur_points.length-4];
				var lastPointY = cur_points[cur_points.length-3];
				line.beginStroke("#F00").moveTo(lastPointX,lastPointY).lineTo(mousePos.x,mousePos.y);
				var s = new createjs.Shape(line);
				curP_container.addChild(p_point);
				curL_container.addChild(s);							
			}
			cur_container.addChild(polygon);
			mapstage.update();			
		}
		 
		function firstPoint(event) {
			
			var polygon=cur_container.getChildAt(0);
            //var polygon = event.target.parent.parent;
			var i = 2;
			var cur_polygon = new createjs.Graphics();
			var line = new createjs.Graphics();
			cur_polygon.beginFill("#00F").moveTo(cur_points[0],cur_points[1]);
			for(i;i < cur_points.length;i=i+2){
				cur_polygon.lineTo(cur_points[i],cur_points[i+1]);					
			}
			cur_polygon.lineTo(event.target.x+10,event.target.y+10);
			var p = new createjs.Shape(cur_polygon);
			
			p.addEventListener("click",clickPlg);
			p.cursor = "pointer";
			
			//var l = new createjs.Shape(line);
			p.alpha = 0.2;
			polygon.getChildAt(0).addChild(p);
			polygon.removeChildAt(2);
			var polyIds = new Array();
			var maxId = -1;
			if(exist_container.getNumChildren()!=0){
				var exPolys = exist_container.children;
				for(var y in exPolys)
				{
					polyIds.push(parseInt(exPolys[y].getChildAt(2).text))
				}
				maxId = Math.max.apply(null, polyIds);
			}
			var text = new createjs.Text((maxId+1).toString(), "40px Comic Sans MS", "#000000");
			text.x=getCentrP(cur_points).x-10;
			text.y=getCentrP(cur_points).y-20;
			maxregionid ++;
			text.id = head + maxregionid;
			polygon.addChild(text);
			exist_container.addChild(polygon);			
			event.target.removeEventListener('click', firstPoint,false);
			event.target.cursor = "default";
			addAllclickPlg();						
			cur_points.length = 0
			ifFirst = 1;
			myCanvas.removeEventListener('click', canvasclick,false);
			
			exist_container.mouseEnabled  = true;
			mapstage.update();
		}
		
		//add click event for all polygons in exist_container
		function addAllclickPlg()
		{
			var polyList = exist_container.children;
			for(var i in polyList){
				polyList[i].getChildAt(0).getChildAt(0).addEventListener("click",clickPlg);		
				polyList[i].getChildAt(0).getChildAt(0).cursor = "pointer";
			}
		}
		
		//remove click event for all polygons in exist_container
		function removeAllclickPlg()
		{
			var polyList = exist_container.children;
			for(var i in polyList){
				polyList[i].getChildAt(0).getChildAt(0).removeEventListener("click",clickPlg);	
				polyList[i].getChildAt(0).getChildAt(0).cursor = "default";
			}
		}
		
		function clickPlg(event) {	
//			if(addtag == 0){
			if(cur_container.getNumChildren()!=0)
			{			
				var o =  cur_container.getObjectUnderPoint(event.stageX,event.stageY);
				if(o!=null){
					event.target = o;
					 ClickPoints(event);
					return;
				}
				resetAlpha();
				var list = cur_container.getChildAt(0).getChildAt(1).children;
				for (i in list)
					{
						list[i].image = ex_pointimg;

						list[i].removeEventListener("click",ClickPoints);
						list[i].cursor = "default";
					}
				exist_container.addChild(cur_container.getChildAt(0));
				
			}
			event.target.alpha =0.5;
			cur_container.addChild(event.target.parent.parent);
			var list = cur_container.getChildAt(0).getChildAt(1).children;
			for (i in list)
				{
					list[i].addEventListener("click",ClickPoints);
					list[i].cursor = "pointer";
				}
			
			mapstage.update();		
		}
		
			
		//click point of selected polygon 
		function ClickPoints(event) {
			var list = cur_container.getChildAt(0).getChildAt(1).children;
			for (i in list)
				{
					list[i].image = ex_pointimg;

				}
			
			event.target.image = pointimg;

			mapstage.update();		
			
		}
		
		
	
	  $("#save").click(function(){
		  exist_container.addChild(cur_container.getChildAt(0));
		  exP_con.addChild(curP_con.getChildAt(0));
		  var AreaId = null;
		  
		  AreaId = $("#area").val();
		  

		  var pointlist = exP_con.children;
		  var list = exist_container.children;
		  //regionlist
			  
		  if(list.length==0){
			  var polyJs = "\"regionList\":["
			  var polyStr = {"areaId":AreaId};
			  var o = JSON.stringify(polyStr);
			  polyJs += o;
			  polyJs +="]";
			  
		  }
		  else{
			  var polyJs = "\"regionList\":[";
			  for (var i in list)
				  {
				    var polyid = parseInt(list[i].getChildAt(2).text);
				  	var points = list[i].getChildAt(1).children;
				    var id = list[i].getChildAt(2).id;
				  	var vertexes = new Array();
				  	for (var v in points){
				  		vertexes.push(parseInt(points[v].x/scale+10));
				  		vertexes.push(parseInt(points[v].y/scale+10));	
				  		
				  	}
				  	var polyStr = {"areaId":AreaId,"polyId":polyid,"id":id,"vertexes":vertexes};
				  	var o = JSON.stringify(polyStr);
				  	polyJs += o;
				  	if( i != list.length-1){
				  		 polyJs += ",";
				  		}				  	
				  }
			    polyJs +="]";
		  }
		    //var Js = "{"+pointJs+","+polyJs+"}";
		  	var Js = "{" + polyJs + "}";
    		jQuery.ajax({
	    		type:'POST',
	    		contentType : 'application/json',
	    		url:'savepoly',
	    		data:Js,
	    		dataType:'json',

	    		beforeSend:function(){
	   	           $("#loading").css('visibility','visible');
	   	        },
	    		success:function(data){
	    			if(data.isSuccess==0){
						 window.location.href="./login.html";
					}
					else{
						alert("saved successfully!")
					}
	    		},
	    		complete: function() {
					 
					 $("#loading").css('visibility','hidden')
		        }
	    	});
	  })
		
	//get center point of the polygon
	function getCentrP(points){
		var xList = new Array();
		var yList = new Array();
		for(var i in points){
			if(i%2==1) 
				yList.push(points[i]);
			else 
				xList.push(points[i]);
		}
		return { 
			x: (Math.max.apply(null, xList)+Math.min.apply(null, xList))/2,
			y: (Math.max.apply(null, yList)+Math.min.apply(null, yList))/2
		}
	}
		
	//reset the color of polygons
	function resetAlpha(){
		if(cur_container.getNumChildren()!=0){
			cur_container.getChildAt(0).getChildAt(0).getChildAt(0).alpha = 0.2;
			mapstage.update();		
		}
	}
		
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