$(document).ready(function(){
	  
  var scale = 1;
  var areaid = null;
  var curId = null;
  
  $("#hidelogodiv").hide();
  $("#name_en_div").hide();
  $("#detail_en_div").hide();
  $("#name_zhhk_div").hide();
  $("#detail_zhhk_div").hide();
  $("#name_zhcn_div").hide();
  $("#detail_zhcn_div").hide(); 
  $("#langdiv").hide();
  $("#disablediv").hide();
  $("#loadinfo").hide();
  $("#preview").hide();
  $("#choosePos").hide();
  $("#detail").hide();
  $("#lg").hide();
  $("#btns").hide();
  var myCanvas = $("#map").get(0); 
  var context = myCanvas.getContext("2d");
  var mapstage = new createjs.Stage("map");
  mapstage.enableMouseOver(20);
  var cur_container = new createjs.Container();
  var exist_container = new createjs.Container();
  var preview_container = new createjs.Container();
  var Bitmap = new createjs.Bitmap();
  mapstage.addChild(cur_container);
  //mapstage.addChild(exist_container);

  var site =null;
  
  var fac = new Image();
  fac.src = "./img/fac.png"  
  var selected_fac = new Image();
  selected_fac.src = "./img/selected_fac.png"  
  fac.onload = function () { 
	  bm_fac = new createjs.Bitmap(fac);
	  bm_facModify = new createjs.Bitmap(fac);
  }
  
  var entry = new Image();
  entry.src = "./img/entry.png"  
  var selected_entry = new Image();
  selected_entry.src = "./img/selected_entry.png"  

  var exit = new Image();
  exit.src = "./img/exit.png" 
  var selected_exit = new Image();
  selected_exit.src = "./img/selected_exit.png"  

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
  	  if(scale < 2){
		  scale = scale+0.3;
		  $("#show_map").click();
  	  } 
	  else {
		  return;
	  }

  })
  
	//解析session 
  	jQuery.ajax({
			contentType : 'application/json',
			url:'getfunctions',
			dataType:'text',
			success:function(data){
				if(data.length>0){
					var funcs = JSON.parse(data);
					if(funcs.facs==0){$("#faclabel").hide()}
					if(funcs.polygons==0){$("#polygon").hide()}
					if(funcs.types==0){$("#typelabel").hide()}
					if(funcs.ads==1){$("#adslabel").show()}
					if(funcs.sites==1){$("#maplabel").show()}
					if(funcs.events==1){$("#eventlabel").show()}
					if(funcs.panos==1){$("#panolabel").show()}
					if(funcs['dc points']==1){$("#wcpointslabel").show()}
					if(funcs['facs import']==0){$("#importlabel").hide()}
				}
				else{
					 window.location.href="./login.html";
				}

				
			}
		})
  
  
  jQuery.ajax({
				contentType : 'application/json',
				url:'getlangs',
				dataType:'text',
				success:function(data){
					var langsstr = data.substring(1,data.length-1)
					var langslist = langsstr.split(",");
					var optionstring = "";
					for(var i in langslist){
						if(langslist[i].replace(/"/g,"")=="ZH-HK"){
							$("#name_zhhk_div").show();
							$("#detail_zhhk_div").show();							
						}
						if(langslist[i].replace(/"/g,"")=="ZH-CH"){
							$("#name_zhcn_div").show();
							$("#detail_zhcn_div").show();						
						}
						else if(langslist[i].replace(/"/g,"")=="EN"){
							$("#name_en_div").show();
							$("#detail_en_div").show();
						}
						
						optionstring +="<option value = "+langslist[i] +">" +langslist[i].substring(1,langslist[i].length-1)+"</option>"	
					}
					$("#language").html(optionstring);
					}
				})
  
  
  
 
			jQuery.ajax({
				contentType : 'application/json',
				url:'loadBuilding',
				dataType:'json',
				success:function(data){
					buildinglist = new Array();
					arealist = new Array();
					for(var item in data){
						buildinglist[item] =  JSON.parse(data[item].name)[0].en;
						arealist[item] = JSON.parse(data[item].areas);
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
													optionstring +="<option value = "+data1[b]._id+ ">" +buildinglist[a]+","+JSON.parse(data1[b].name)[0].en+"</option>"	
													
												}
											}
										}	
									}
										$("#area").html(optionstring);
									}
								})
							
					}

				
    		
			});
  
  jQuery.ajax({
		type:'GET',
		contentType : 'application/json',
		url:'loadType',		
		success:function(data){	
			var optionstring = "";
			for(var item in data){
				var temp = JSON.parse(data[item].details)[0].en;   				
				optionstring +="<option value="+data[item]._id+">"+temp+"</option>"
	
			}
			
			$("#factype").html(optionstring);
			$("#factype").each(function(){this.selectedIndex=-1});				
		    }
		});
  

  jQuery.ajax({
		type:'GET',
		contentType : 'application/json',
		url:'getver',		
		success:function(data){	
			$("#version").val(data);				
		    }
		});
  
  
 
  $("#show_map").click(function(){
	  
	  resetField();
	  var MapImage = new Image();

	  if(getRadioText()==""){
		  alert("Please Choose Operation!")
	  }
	  else{
	  preview_container.removeAllChildren();
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
//			var mapurl = "";		
//			var temp = data[0].imPath;   				
			MapImage.src="data:image/png;base64,"+data;
//	        MapImage.onload = function () {
	    	   myCanvas.width = MapImage.width*scale;
	           myCanvas.height = MapImage.height*scale;
	           $("#map").css("background","url("+MapImage.src+")");
//	           $("#map").attr("src","data:image/png;base64,"+data);
	           $("#map").css("background-size","100% 100%");
	     	  jQuery.ajax({
	  			type:'POST',
	  			contentType : 'application/json',
	  			url:'showfac',			
	  			data:$("#area").val(),
	  			dataType:'json',

	  			success:function(data){
	  				cur_container.removeAllChildren();	
	  				exist_container.removeAllChildren();								
	  				for(var item in data){
	  						areaid = data[item].areaId;
	  						var bm_existfac = new createjs.Bitmap(fac);
	  						bm_existfac.name = "fac_"+data[item]._id;
	  						if(data[item].logoPt!=""){
	  							var xfac = JSON.parse(data[item].logoPt)[0];
	  							var yfac = JSON.parse(data[item].logoPt)[1];
	  							bm_existfac.x=(xfac-10)*scale;
	  							bm_existfac.y=(yfac-10)*scale;
	  						}

	  						if(getRadioText() == "Modify")
	  						{
	  							bm_existfac.addEventListener("click",bitmapClick);
	  						    bm_existfac.cursor = "pointer";
	  						 }
	  						exist_container.addChild(bm_existfac);
	  						
	  						if(data[item].exitPts!=""){
	  							var exitloc = JSON.parse(data[item].exitPts);
	  							for(var exitem in exitloc){
	  								
	  								if(exitem % 2 == 1 )
	  									{								    
	  										var bm_existexit = new createjs.Bitmap(exit);
	  									    bm_existexit.name = "exit_"+data[item]._id+"_"+parseInt(exitem/2);
	  									    var xexit = exitloc[exitem-1];
	  									    var yexit = exitloc[exitem];
	  									    bm_existexit.x=(xexit-5)*scale;
	  										bm_existexit.y=(yexit-5)*scale;
	  										exist_container.addChild(bm_existexit);
	  								    }

	  							    }
	  						}

	  						if(data[item].entryPts!=""){
	  							var entryloc = JSON.parse(data[item].entryPts);
	  							for(var enitem in entryloc){
	  								if(enitem % 2 == 1 )
	  								{
	  									var bm_existentry = new createjs.Bitmap(entry);
	  									bm_existentry.name = "entry_"+data[item]._id+"_"+parseInt(enitem/2);
	  								    var xentry = entryloc[enitem-1];
	  								    var yentry = entryloc[enitem];
	  								    bm_existentry.x=(xentry-5)*scale;
	  								    bm_existentry.y=(yentry-5)*scale;
	  									exist_container.addChild(bm_existentry);
	  							    }
	  							  }	
	  						}
	  					
	  						
	  					    }	
	  				   mapstage.addChild(exist_container);
	  				   
	  				   mapstage.update();
	  					},
				 complete: function() {
					 
					 $("#loading").css('visibility','hidden')
		        }
        
	  			});
//	          }
	          
		    }
		});
	  }
  })
  $("#modify_facP").click(function(){
	  
	  for(var i in  cur_container.children){
		  var e = cur_container.getChildAt(i);
		  if(e.name.split("_")[0]=="fac"){
			 e.addEventListener("pressmove",movefac);
			 e.addEventListener("pressup",pressupfac)
		  }
		  else if(e.name.split("_")[0]=="exit"&& e.x/scale+5 == $("#exitPos").val().split(",")[0]&&e.y/scale+5 == $("#exitPos").val().split(",")[1]){			  
			  cur_container.getChildAt(i).cursor = "pointer";
			  cur_container.getChildAt(i).addEventListener("pressmove",movefac);
			  cur_container.getChildAt(i).addEventListener("pressup",pressupexit)
		  }
		  else if(e.name.split("_")[0]=="entry"&& e.x/scale+5 == $("#entryPos").val().split(",")[0]&&e.y/scale+5 == $("#entryPos").val().split(",")[1]){
			  cur_container.getChildAt(i).cursor = "pointer";
			  cur_container.getChildAt(i).addEventListener("pressmove",movefac);
			 cur_container.getChildAt(i).addEventListener("pressup",pressupentry)
		  }
		  
	  }
  })
//    $("#modify_exitP").click(function(){
//	  
//	  for(var i in  cur_container.children){
//		  var e = cur_container.getChildAt(i);
//		  if(e.name.split("_")[0]=="exit"&& e.x+5 == $("#exitPos").val().split(",")[0]&&e.y+5 == $("#exitPos").val().split(",")[1]){			  
//			  cur_container.getChildAt(i).cursor = "pointer";
//			  cur_container.getChildAt(i).addEventListener("pressmove",movefac);
//			  cur_container.getChildAt(i).addEventListener("pressup",pressupexit)
//		  }
//		  
//	  }
//  })
//    $("#modify_entranceP").click(function(){
//	  
//	  for(var i in  cur_container.children){
//		  var e = cur_container.getChildAt(i);
//		  
//		  if(e.name.split("_")[0]=="entry"&& e.x+5 == $("#entryPos").val().split(",")[0]&&e.y+5 == $("#entryPos").val().split(",")[1]){
//			  cur_container.getChildAt(i).cursor = "pointer";
//			  cur_container.getChildAt(i).addEventListener("pressmove",movefac);
//			 cur_container.getChildAt(i).addEventListener("pressup",pressupentry)
//		  }
//		  
//	  }
//  })
  

	function movefac(evt){	
	  		evt.target.cursor = "pointer";
			evt.target.x = evt.stageX;
		    evt.target.y = evt.stageY;
		    mapstage.update();				  
	}
	
	function pressupfac(evt){
		$("#logoPos").val((parseInt(evt.target.x/scale)+10)+", "+(parseInt(evt.target.y/scale)+10));
		 mapstage.update();
	}
	function pressupentry(evt){
		$("#entryPos option:selected").text((parseInt(evt.target.x/scale)+5)+","+(parseInt(evt.target.y/scale)+5))
		mapstage.update();
	}

	function pressupexit(evt){
		$("#exitPos option:selected").text((parseInt(evt.target.x/scale)+5)+","+(parseInt(evt.target.y/scale)+5))
//		evt.target.removeEventListener("pressmove",movefac,false);
//		evt.target.removeEventListener("pressup",pressupexit,false);
//		evt.target.cursor = "default"
		mapstage.update();
	}


   function bitmapClick(event) {

	          //cur_container.removeAllChildren();
	   			
	          myCanvas.removeEventListener('click', canvasclick,false);
			  while(cur_container.getNumChildren()!=0){
				  cur_container.getChildAt(0).removeEventListener("pressmove",movefac,false);
				  
				  
				  if(cur_container.getChildAt(0).name.split("_")[0]=="entry"){
					  	cur_container.getChildAt(0).image = entry;
						cur_container.getChildAt(0).removeEventListener("pressup",pressupentry,false);
						cur_container.getChildAt(0).cursor = "default"
					  }
				  else if(cur_container.getChildAt(0).name.split("_")[0]=="exit"){
					  	cur_container.getChildAt(0).image = exit;
						cur_container.getChildAt(0).removeEventListener("pressup",pressupexit,false);
						cur_container.getChildAt(0).cursor = "default"
					  }
				  else if(cur_container.getChildAt(0).name.split("_")[0]=="fac"){
					  	cur_container.getChildAt(0).image = fac;
					  	cur_container.getChildAt(0).removeEventListener("pressup",pressupfac,false);
					  }
				  exist_container.addChild(cur_container.getChildAt(0));
			  }
// 			  var logoPosString = Math.round(event.target.x/scale+10).toString()+", "+Math.round(event.target.y/scale+10).toString()
// 			  $("#logoPos").val(logoPosString);
// 			 $("#cur_pos")[0].innerHTML=logoPosString;
 			  var facname = event.target.name;
 			  var index = facname.split("_")[1];
 			 curId = index;
 			 jQuery.ajax({
 				type:'POST',
 				contentType : 'application/json',
 				url:'loadModifyInf',
 				data:curId,
// 				url:'loadModifyInf',
// 				data:"["+logoPosString+"]",
 				dataType:'json',
 				success:function(data){	
 		 			$("#logoPos").val(JSON.parse(data.logoPt)[0]+", "+JSON.parse(data.logoPt)[1]);
 		 			$("#cur_pos")[0].innerHTML=JSON.parse(data.logoPt)[0]+", "+JSON.parse(data.logoPt)[1];
 		 			$('#name').val("");
 		 			$('#zh_cn_name').val("");
 		 			$('#zh_hk_name').val("");
 		 			if(data.name!=""){
     					for(var i in JSON.parse(data.name)){
	        				if(JSON.parse(data.name)[i]["en"]){
	        					$('#name').val(JSON.parse(data.name)[i]["en"]);
	        				}
	        				if(JSON.parse(data.name)[i]["chn"]){
	        					$('#zh_cn_name').val(JSON.parse(data.name)[i]["chn"]);
	        				}
		        				if(JSON.parse(data.name)[i]["chn-hk"]){
	        					$('#zh_hk_name').val(JSON.parse(data.name)[i]["chn-hk"]);
	        				}
    					}
 					}
 					
 					$("#addr").val(data.addr);
 					$("#zone").val(data.zone);
 					$("#version").val(data.ver);
 					$('#details').val("");
 					$('#zh_cn_details').val("");
 					$('#zh_hk_details').val("");
 					if(data.details!=""){
     					for(var i in JSON.parse(data.details)){
	        				if(JSON.parse(data.details)[i]["en"]){
	        					$('#details').val(JSON.parse(data.details)[i]["en"]);
	        				}
	        				if(JSON.parse(data.details)[i]["chn"]){
	        					$('#zh_cn_details').val(JSON.parse(data.details)[i]["chn"]);
	        				}
		        				if(JSON.parse(data.details)[i]["chn-hk"]){
	        					$('#zh_hk_details').val(JSON.parse(data.details)[i]["chn-hk"]);
	        				}
    					}
 					}
 					if(data.stat==""){
 						$('input:radio[name="disableradio"]').filter('[value="enable"]').prop('checked',true);							
 					}
 					else{$('input:radio[name="disableradio"]').filter('[value="disenable"]').prop('checked',true);	}
 					var typeid = data.typeId;
 					var logoid = data.logoId;
 					curId = data._id;
 					jQuery.ajax({
 						type:'POST',
 		 				contentType : 'application/json',
 		 				url:'getlogo',
 		 				data:logoid.toString(),
 		 				dataType:'text',
 		 				success:function(data){	
 		 					var typeImage = new Image();
 		 					typeImage.src="data:image/png;base64,"+data;
 		 		            $("#logoimg").css("background","url("+typeImage.src+")");
 		 		            $("#logoimg").css("background-size","100px 100px");
 		 					$("#logoimg").attr("display","block");
 		 				},
 					})
 					
 					jQuery.ajax({
 						type:'POST',
 		 				contentType : 'application/json',
 		 				url:'getTypebyId',
 		 				data:typeid.toString(),
 		 				dataType:'json',
 		 				success:function(type){	
 		 					var typename = JSON.parse(type.details)[0].en; 		 					
// 		 					var htmlstr = "";	    	           		 	    	           			 
//	    	           	    htmlstr += "<label  name = "+typeid.toString()+">"+typename+"</label>";
//	    	           	   $("#selectedtype").html(htmlstr);
 		 					selected(typename);
 		 					 $("#factype option").filter(function () { return $(this).html == typename; }).prop("selected",true);
 		 					$("#factype option select").val(typename);

 		 				},
 					})
 					
 					var entr =JSON.parse(data.entryPts);
 					var exit = JSON.parse(data.exitPts);
 					
 					var optionentr = "";
 					var optionexit = "";
 					
 					for(var item in entr){	
 							if(item%2==1){
 							var temp1 = exist_container.getChildByName("entry_"+index+"_"+parseInt(item/2));
 							temp1.image = selected_entry;
 	 						cur_container.addChild(temp1); 
 	 						optionentr +="<option>"+entr[item-1]+","+entr[item]+"</option>"
 	 						
 						}
 					}
 					$("#entryPos").html(optionentr);
 					
                    for(var item in exit){	
                    	if(item%2==1){
 							var temp1 = exist_container.getChildByName("exit_"+index+"_"+parseInt(item/2));
 							temp1.image = selected_exit;
 	 						cur_container.addChild(temp1); 
 	 						optionexit +="<option>"+exit[item-1]+","+exit[item]+"</option>"
 							
 						}
 					}
 					$("#exitPos").html(optionexit);
 					event.target.image = selected_fac;
 		 			cur_container.addChild(event.target);
 		 			mapstage.addChild(cur_container);
 		 			mapstage.update();
 				}

		    		
 			 });
		  }

  function resetField(){
	  
	    $("#cur_pos").html("0,0");
	    $("#logoPos").val(null);
		$("#entryPos").html(null);
		$("#exitPos").html(null);
		$("#name").val(null);
		$("#zh_hk_name").val(null);
		$("#zh_cn_name").val(null);
		$("#addr").val(null);
		$("#details").val(null);
		$("#zh_hk_details").val(null);
		$("#zh_cn_details").val(null);
		$("#logoimg").attr("style",null);
		$("#logoimg").attr("src",null);
		$("#logoimg").css("background","");
		$("#textfield").val(null);
		$("#zone").val(null);	  
  }
  
  $("input[name='modeltype']").change(function(){
	  
	  var model = getRadioText();
	  if(model =="Modify"){
		  $("#hidelogodiv").show();
		  $("#submit").hide();		 
		  $("#clear_pos").hide();
		  $("#modify_facP").show();
//		  $("#modify_entranceP").show();
//		  $("#modify_exitP").show();
		  $("#modify").show();
		  $("#delete").show();
		  $("#choosePos").show();
		  $("#detail").show();
		  $("#btns").show();
		  $("#langdiv").hide();
		  $("#preview").hide();
//		  $("#lg").show();
		  $("#add_facP").hide();
		  $("#add_entr").hide();
		  $("#add_exit").hide();
		  $("#loadinfo").show();
		  resetField();
		  mapstage.mouseChildren = true;
		  myCanvas.removeEventListener('click', canvasclick,false);
		  preview_container.removeAllChildren();
		  exist_container.removeAllChildren();
		  cur_container.removeAllChildren();
		  mapstage.removeChild(preview_container);
		  mapstage.update();
	  }
	  else if(model == "Add"){	
		  $("#hidelogodiv").show();
		  $("#clear_pos").show();
		  $("#submit").show();
		  $("#modify_facP").hide();
//		  $("#modify_exitP").hide();
//		  $("#modify_entranceP").hide();
		  $("#modify").hide();
		  $("#delete").hide();
		  $("#langdiv").hide();
		  $("#preview").hide();
		  $("#choosePos").show();
		  $("#detail").show();
		  $("#btns").show();
//		  $("#lg").show();
		  $("#add_facP").show();
		  $("#add_entr").show();
		  $("#add_exit").show();
		  $("#loadinfo").show();
		  mapstage.mouseChildren = false;
		  resetField();
		  preview_container.removeAllChildren();
		  exist_container.removeAllChildren();
		  cur_container.removeAllChildren();
		  mapstage.removeChild(preview_container);
		  mapstage.addChild(cur_container);
		  mapstage.update();
		  
		  myCanvas.addEventListener("click", canvasclick,false);
	  }	
	  else if(model == "Preview"){	
		 $("#hidelogodiv").hide();
		 $("#detail").hide();
		 $("#btns").hide();
		 $("#langdiv").show();
		 $("#preview").show();
		 $("#loadinfo").show();
		 $("#choosePos").hide();
		 $("#lg").hide();
		 exist_container.removeAllChildren();
		 cur_container.removeAllChildren();
		 mapstage.update();
		  
	  }
	
  	}
  )
  
	function getRadioText(){
		var item = $(':radio[name="modeltype"]:checked').val();
		return item
	}
  
  				

	function canvasclick(evt){
		cur_container.removeChild(bitmap);
		var mousePos = getMousePos(myCanvas, evt);
	
			bitmap.x = mousePos.x-5;
			bitmap.y = mousePos.y-5;
			cur_container.addChild(bitmap);
			mapstage.update();

		$("#cur_pos")[0].innerHTML = parseInt(mousePos.x/scale) +',' + parseInt(mousePos.y/scale);
		
	}
					
  $("#preview").click(function(){
	  
	  exist_container.removeAllChildren();
	  preview_container.removeAllChildren();
	  
	  
      if( $("#map").css('backgroundImage')=="none")
      {
     	 alert("Please load map first!");
     	 return;
     	 
      }

		jQuery.ajax({
 				type:'POST',
 				contentType : 'application/json',
 				url:'preview',
 				data:$("#area").val(),
 				dataType:'json',
 				beforeSend:function(){
 	  	           $("#loading").css('visibility','visible');	  	   
 		  	       $("#loading").css('top','200px');
 	  	        },
 				success:function(result){
// 				    mapstage.removeAllChildren();
 					for(var a in result){
 						//logo
 						if(result[1]!=null){
		 					var imgList = result[1];
		 					for(var item in imgList)
		 						{
	 						    if((imgList[item].split("|")[1]).split("/").length>1){
			 						logos = new Image();
//					 						logos.src = baseurl+imgList[item].split("|")[1];
			 						logos.src="data:image/png;base64," + imgList[item].split("|")[1];
			 						logoitem = new createjs.Bitmap(logos);
			 						logoitem.name = "logo_"+ item;
			 						logoitem.scaleX = 0.2*scale;
			 						logoitem.scaleY = 0.2*scale;
			 						var temp = imgList[item].split("|")[0];
			 						logoitem.x=(parseInt(temp.substr(1,temp.indexOf(',')))-15)*scale;
			 						logoitem.y=(parseInt(temp.substr(temp.indexOf(',')+1,temp.indexOf(']')-1))-15)*scale;
			 						preview_container.addChild(logoitem);
	 						     }	
		 						}
 						}
		 					//name 
 						if(result[0]!=null){
		 					var textList = result[0];
		 					for(var item in textList){
		 						var names = textList[item].split("|")[1].toString();
		 						var textstring = "";
		 						var temp = textList[item].split("|")[0];
		 						var width =null;
		 						if( $("#language").val()=="EN"){
		 		   					for(var i in JSON.parse(names)){
		 		        				if(JSON.parse(names)[i]["en"]){
		 		        					textstring = JSON.parse(names)[i]["en"];
		 		        				}
		 		        
		 	    					}
		 							 ;
		 						}
		 						else if( $("#language").val()=="ZH-CH"){
		 		   					for(var i in JSON.parse(names)){
		 		        				if(JSON.parse(names)[i]["chn"]){
		 		        					textstring = JSON.parse(names)[i]["chn"];
		 		        				}
		 		        
		 	    					}
		 							 ;
		 						}
		 						else if( $("#language").val()=="ZH-HK"){
		 		   					for(var i in JSON.parse(names)){
		 		        				if(JSON.parse(names)[i]["chn-hk"]){
		 		        					textstring = JSON.parse(names)[i]["chn-hk"];
		 		        				}
		 		        
		 	    					}
		 							 ;
		 						}
		 						if(textstring.length>0){
		 							if(textstring.indexOf("\\n") > -1){
			 							var lines = textstring.split("\\n");
			 							for(var i in lines){
			 								var text = new createjs.Text(lines[i], "18px Sans,Microsoft JhengHei", "#000000");
			 								text.shadow  = new createjs.Shadow("#fff", 0, 0, 3);
//					 								text.text = lines[i];
			 								width = text.getMeasuredWidth ();
				 							text.x=(parseInt(temp.substr(1,temp.indexOf(',')))-width/2)*scale;
			 						    	text.y=(parseInt(temp.substr(temp.indexOf(',')+1,temp.indexOf(']')-1))-20+18*i)*scale;	 	
			 						    	preview_container.addChild(text);
			 							}
//					 							lines[0]+="\n"
//					 							lines =lines[0]+lines[1];
//					 							text.text = lines;
//					 							width = text.getMeasuredWidth ();
//					 							text.x=(parseInt(temp.substr(1,temp.indexOf(',')))-width/4)*scale;
//				 						    	text.y=(parseInt(temp.substr(temp.indexOf(',')+1,temp.indexOf(']')-1))-20)*scale;	 					
			 						}
			 						else{
//					 							var text = new createjs.Text(textstring, "18px  Microsoft JhengHei", "#000000");
		 								var text = new createjs.Text(textstring, "18px  Microsoft JhengHei", "#000000");
		 								text.shadow  = new createjs.Shadow("#fff", 0, 0, 3);
//					 							text.text = textstring;				 							
			 						    width = text.getMeasuredWidth ();
			 							text.x=(parseInt(temp.substr(1,temp.indexOf(',')))-width/2)*scale;
		 						    	text.y=(parseInt(temp.substr(temp.indexOf(',')+1,temp.indexOf(']')-1))-10)*scale;
		 						    	preview_container.addChild(text);			
			 						}
		 						}

	 						     }	
 							
 						   }
 						//address
 						if(result[2]!=null){
		 					var nameList = result[2];
		 					for(var item in nameList){
		 						var text = new createjs.Text("", "12px Comic Sans MS", "#000000");
		 						text.shadow  = new createjs.Shadow("#fff", 0, 0, 3);
		 						var textstring = nameList[item].split("|")[1].toString();
		 						var temp = nameList[item].split("|")[0];
		 						var width =null;
		 						if(textstring.indexOf("\\n") > -1){
		 							var lines = textstring.split("\\n");
		 							lines[0]+="\n"
		 							lines =lines[0]+lines[1];
		 							text.text = lines;
		 							width = text.getMeasuredWidth ();
		 							text.x=(parseInt(temp.substr(1,temp.indexOf(',')))-width/4)*scale;
	 						    	text.y=(parseInt(temp.substr(temp.indexOf(',')+1,temp.indexOf(']')-1))-20)*scale;
		 						}
		 						else{
		 							text.text = textstring;
		 						    width = text.getMeasuredWidth ();
		 							text.x=(parseInt(temp.substr(1,temp.indexOf(',')))-width/2)*scale;
	 						    	text.y=(parseInt(temp.substr(temp.indexOf(',')+1,temp.indexOf(']')-1))-10)*scale;
		 						}

 						    	preview_container.addChild(text);
	 						     }	
 							
 						   }

 					}
 					
 					mapstage.addChild(preview_container);
 					mapstage.update();
 					
 			},
	  	      complete: function() {
					 
					 $("#loading").css('visibility','hidden')
		        }
					
 				
		});
	  
  })
  


  function selected(option)  
  {  
	  var kk = document.getElementById("factype").options;  
	  for (var i=0; i<kk.length; i++) {  
		  if (kk[i].text==option) {  
		  kk[i].selected=true;  
		  break;  
	      }   
      }
  }
  //Get Mouse Position 
	function getMousePos(myCanvas, evt) { 
		var rect = myCanvas.getBoundingClientRect(); 
		return { 
			x: evt.clientX - rect.left * (myCanvas.width / rect.width),
			y: evt.clientY - rect.top * (myCanvas.height / rect.height)
				}
		}
	
	//get the text of selectedbox
	$.fn.getSelectedText=function(){
		if(this.size()!==0) {
			var index = jQuery(this).get(0).selectedIndex;
			return jQuery(this).get(0).options[index].text;
		}
	}
	


	
    $("#uploadlogo").on('change', function(){

        if ($("#uploadlogo")[0].files && $("#uploadlogo")[0].files[0])
        {
        	 if(event.target.files[0].type == "image/png" || event.target.files[0].type == "image/jpeg")
        	    {

		            var fReader = new FileReader();
		            fReader.onloadend = function(event){
		            	
		            	var url=fReader.result;
		            	$("#logoimg").css("background","url("+url+")");
		                $("#logoimg").css("background-size","100px 100px");
		            	//if(image.width>120||image.height>120){
		            		
		            	//	alert("Please make sure the dimensions of the logo no larger than 120*120!");
		            	//	$("#logoimg")[0].src = null;
		            	//	event.target.files[0]=null;
		            	//	return false;
		            	//}
		            }
		            fReader.readAsDataURL($("#uploadlogo")[0].files[0]);

		            $("#logoimg")[0].onload = function () 
		            {
		            	$("#logoimg")[0].style.display="block";
		            }
//                    }
                 }
        	 else{
        		   alert("The image type must be \".jpeg\",\"jpg\" or\"png\"!");  
        		   this.value = null;
        		   document.getElementById('textfield').value=this.value;
     	           return; 
        		 }
            
        }

    });
    

	
    $.fn.serializeObject = function()    
	{    
	   var o = {};  
	   var detailJson = [];
	   var nameJson = [];
	   var a = this.serializeArray();    
	   $.each(a, function() {    
		   if (o[this.name]) {    
			   if (!o[this.name].push) {    
				   o[this.name] = [o[this.name]];    
			   }    
			   o[this.name].push(this.value || '');    
		   } 
		   else {  
			   			   
			   if (this.name=="entryPos"){
				   var entryList = new Array();
				   for(i=0;i<$("#entryPos")[0].options.length;i++){
					   entryList[i*2] = parseInt($("#entryPos")[0].options[i].text.split(",")[0]);
					   entryList[i*2+1] = parseInt($("#entryPos")[0].options[i].text.split(",")[1]);
				   }
				   var temp0 = JSON.stringify(entryList);
				   temp0 = temp0.replace(/\,/g , ", ");
				   o["entryPts"] = temp0;
			   }
			   else if (this.name=="exitPos"){
				   var exitList = new Array();
				   for(i=0;i<$("#exitPos")[0].options.length;i++){
					   exitList[i*2] = parseInt($("#exitPos")[0].options[i].text.split(",")[0]);
					   exitList[i*2+1] =parseInt($("#exitPos")[0].options[i].text.split(",")[1]);
				   }
				   var temp1 = JSON.stringify(exitList);
				   temp1 = temp1.replace(/\,/g , ", ");
				   o["exitPts"] = temp1;
			   }
			   else if (this.name=="logoPos"){
				   var logoList = new Array();
				   logoList[0] = parseInt($("#logoPos").val().split(",")[0]);
				   logoList[1] = parseInt($("#logoPos").val().split(",")[1]);
				   var temp2 = JSON.stringify(logoList);
				   temp2 = temp2.replace(/\,/g , ", ");
				   o["logoPt"] = temp2;
				   }

			   else if (this.name=="details" && $("#details").val()!= ""){
				   var d_en = {};
				   d_en["en"] = $("#details").val().trim();
				   detailJson.push(d_en);
			   }
			   
			   else if (this.name=="zh_hk_details" && $("#zh_hk_details").val()!= ""){
				   var d_ch_hk = {};
				   d_ch_hk["chn-hk"] = $("#zh_hk_details").val().trim();
				   detailJson.push(d_ch_hk);
			   }
			   else if (this.name=="zh_cn_details" && $("#zh_cn_details").val()!= ""){
				   var d_ch = {};
				   d_ch["chn"] = $("#zh_cn_details").val().trim();
				   detailJson.push(d_ch);
			   }
			   
			   else if (this.name=="name"){
				   var n_en = {};
				   n_en["en"] = $("#name").val().trim();
				   nameJson.push(n_en);
			   }
			   
			   else if (this.name=="zh_hk_name"&& $("#zh_hk_name").val()!= ""){
				   var n_ch_hk = {};
				   n_ch_hk["chn-hk"] = $("#zh_hk_name").val().trim();
				   nameJson.push(n_ch_hk);
			   }
			   else if (this.name=="zh_cn_name"){
				   var n_ch = {};
				   n_ch["chn"] = $("#zh_cn_name").val().trim();
				   nameJson.push(n_ch);
			   }
			   else if (this.name=="disableradio"){
				if($(':radio[name="disableradio"]:checked').val()=="enable"){
					o["stat"] = "enable";
				}
				else{
					o["stat"] = "disenable";
				}
			   }
			   
			   else{o[this.name] = this.value || '';}

		   } 
		   
	   });    
	   o["details"] =JSON.stringify(detailJson);
//	   o["name"] ='[{"en":"test"},{"chn":"测试"}]';
	   o["name"] =JSON.stringify(nameJson);
	   o["site"] = site;
	   o["ver"]= $("#version").val();
	   o["typeId"]= $("#factype").val();
	   return o;    
	}; 
	
	function saveForm(img_id)
		{  
		 var facdetail =$("#facdetail").serializeObject();		
    	 //var json = JSON.stringify(facdetail); 
		 facdetail.logoId = img_id;
    	jQuery.ajax({
    		type:'POST',
    		contentType : 'application/json',
    		url:'saveform',
    		//data:$toJSON(facdetail),
    		data:JSON.stringify(facdetail),
    		dataType:'json',
    		success:function(data){

    			var list = cur_container.children;
    			while(list.length>0){
    				var item = list[0];   				
    				var CurNum = exist_container.getNumChildren();   				
    				exist_container.addChildAt(item,CurNum);
    			}
    			
    			mapstage.addChild(exist_container);
    			mapstage.update();
    			cur_container.removeAllChildren();
    			resetField();
    			mapstage.update();
//    			$("#area").change();
    		},
    		
    		}); 
		}
	
	$("#submit").click(function(){  
    	var facdetail =$("#facdetail").serializeObject();
    	if(facdetail["logoPt"][0]== null||facdetail["logoPt"][1]== null)
    		{
    			alert("Please Choose Position of the facility!")
    		
    		}  	
    	if(!facdetail["exitPts"]||!facdetail["entryPts"])
		{
			alert("Please choose entrance and exit for the facility!")
		
		}  	
    	else{
        $.ajaxFileUpload
        ({
        	type:'POST',
             url:'savefac', 
             secureuri:false,
             fileElementId:'uploadlogo',
             dataType : 'json',
             data:JSON.stringify(facdetail),
             success : function (data, status){
            	 if(data._id!="")
            		 {
               		 var img_id = data._id;
            		 saveForm(img_id);
            		 }
            	 else{
            		 window.location.href="./login.html";
            	 }
             },

             error:function(data, status, e){ //服务器响应失败时的处理函数  
            	 alert(e);  
             }  
          })
    	}
    	});
	
	
	
	$("#modify").click(function(){ 
		var facdetail =$("#facdetail").serializeObject();
		facdetail["_id"] = curId;
//    	var facx =parseInt($("#logoPos").val().split(",")[0]);
//    	var facy =parseInt($("#logoPos").val().split(",")[1]);
//    	var tempList = "["+facx+", "+facy+"]";
//    	var logoPt = {"logoPt": tempList};
    	
    	if($("#uploadlogo").val()!=""){
	        $.ajaxFileUpload
	        ({
	        	type:'POST',
	             url:'savefac', 
	             secureuri:false,
	             fileElementId:'uploadlogo',
	             dataType : 'json',
	             data:JSON.stringify(facdetail),
	             success : function (data, status){
	            	 if(data._id!="")
	            		 {
	               		 facdetail.logoId = data._id;	
	             		jQuery.ajax({
	            			type:'POST',
	            			contentType : 'application/json',
	            			url:'modifyfac',
	            			data:JSON.stringify(facdetail),
	            			dataType:'json',
	            			success:function(data){
	            				if(data.isSuccess==0){
	            					window.location.href="./login.html";
	            				}
	            				else{
	                			var list = cur_container.children;
	                			while(list.length>0){
	                				var item = list[0];   				
	                				var CurNum = exist_container.getNumChildren();   				
	                				exist_container.addChildAt(item,CurNum);
	                			}
	                			
	                			mapstage.addChild(exist_container);
	                			mapstage.update();
	                			cur_container.removeAllChildren();
	                			resetField();
	                			mapstage.update();
	            				}
	            			}
	            		})
	            	}
	            	 else{
	            		 window.location.href="./login.html";
	            	 }
	             },

	             error:function(data, status, e){ //服务器响应失败时的处理函数  
	            	 alert(e);  
	             }  
	          })
    		
    	}
    	else{
		jQuery.ajax({
			type:'POST',
			contentType : 'application/json',
			url:'modifyfac',
			data:JSON.stringify(facdetail),
			dataType:'json',
			success:function(data){
				if(data.isSuccess==0){
					window.location.href="./login.html";
				}
				else{
	    			var list = cur_container.children;
	    			while(list.length>0){	
	    				
	    				var item = list[0];  
	    				item.removeEventListener("pressmove",movefac,false);
	    					  	    					  
    					  if(item.name.split("_")[0]=="entry"){
    						  item.image = entry;
    						  item.removeEventListener("pressup",pressupentry,false);
    						  item.cursor = "default"
    						  }
    					  else if(item.name.split("_")[0]=="exit"){
    						  item.image = exit;
    						  item.removeEventListener("pressup",pressupexit,false);
    						  item.cursor = "default"
    						  }
    					  else if(item.name.split("_")[0]=="fac"){
    						  item.image = fac;
    						  item.removeEventListener("pressup",pressupfac,false);
    						  }   					 	    					    				 				
	    				var CurNum = exist_container.getNumChildren();   				
	    				exist_container.addChildAt(item,CurNum);
	    			}
	    			
	    			mapstage.addChild(exist_container);
	    			mapstage.update();
	    			cur_container.removeAllChildren();
	    			resetField();
	    			mapstage.update();
				}

				}
			})
    	}
   	
    });
	
	
	$("#delete").click(function(){    	
    	var facx =parseInt($("#logoPos").val().split(",")[0]);
    	var facy =parseInt($("#logoPos").val().split(",")[1]);
    	var tempList = "["+facx+", "+facy+"]";
    	var logoPt = {"logoPt": tempList};
    	jQuery.ajax({
			type:'POST',
			contentType : 'application/json',
			url:'deletefac',
			data:JSON.stringify(curId),
//			data:JSON.stringify(logoPt),
			dataType:'json',
			success:function(data){
				if(data.isSuccess==0){
					 window.location.href="./login.html";
				}
				else{
					resetField();
					cur_container.removeAllChildren();
					$("#show_fac").click();
					mapstage.update();
				}

			}
		
	          })
	          
    	});

	
	$("#add_facP").click(function(){
		var cur_pos = $("#cur_pos").html();
		  $("#logoPos").val(cur_pos);
			 // var fac = new Image();
			  //fac.src = "./img/fac.png"	
			  var bm_fac = new createjs.Bitmap(fac)
			  bm_fac.x = bitmap.x-5;
			  bm_fac.y = bitmap.y-5;
			  cur_container.removeChild(bm_fac);
			  cur_container.addChild(bm_fac);
			  cur_container.removeChildAt(bitmap);
			  mapstage.update();
	})
	
	$("#add_entr").click(function(){
		var cur_pos = $("#cur_pos").html();
		  $("#entryPos").append("<option>"+cur_pos+"</option>");	
		  
		  bm_entr = new createjs.Bitmap(entry);
		  
		  bm_entr.x = bitmap.x;
		  bm_entr.y = bitmap.y;
		  cur_container.addChild(bm_entr);
		  cur_container.removeChild(bitmap);
		  mapstage.update();
	})
	
	$("#add_exit").click(function(){
		var cur_pos = $("#cur_pos").html();
		 $("#exitPos").append("<option>"+cur_pos+"</option>");	
		  bm_exit = new createjs.Bitmap(exit);			  
		  bm_exit.x = bitmap.x;
		  bm_exit.y = bitmap.y;
		  cur_container.addChild(bm_exit);
		  cur_container.removeChild(bitmap);
		  mapstage.update();
		 	
	});
	
	
	$("#clear_pos").click(function(){
		$("#logoPos").val(null)
		$("#entryPos").html(null)
		$("#exitPos").html(null)
		cur_container.removeAllChildren();
		mapstage.update();
		
	});
//	$("#name").change(function(){
//		$("#nameselect").offset({top:522});
//		$("#nameselect").css("height","25px");
//		$("#nameselect")[0].size=1;
//		
//	})
//	$("#name").bind('input propertychange',function(){
//		var siteAndArea = {"site": site,"building": $("#area").getSelectedText().split(",")[0],
//		            "floor": $("#area").getSelectedText().split(",")[1]};
//		 var nametext=$(this).val();  
//		 if(nametext==""){
//				$("#nameselect").offset({top:522});
//				$("#nameselect").css("height","25px");
//				$("#nameselect")[0].size=1;
//				
//
//				  var options = new Array();
//				  jQuery.ajax({
//						type:'POST',
//						contentType : 'application/json',
//						url:'loadName',		
//						data:JSON.stringify(siteAndArea),
//						dataType:'json',
//						success:function(result){
//							var optionstring = "";
//							for(var item in result){
//								var temp =result[item].name;   				
//								optionstring +="<option>"+temp+"</option>";
//							}
//							$("#nameselect").html(optionstring);
//							$("#nameselect").each(function(){this.selectedIndex=-1});
//							
//						    }
//						});
//			 return
//		 }
//		 jQuery.ajax({
//				type:'POST',
//				contentType : 'application/json',
//				url:'getAreaId',
//				data:JSON.stringify(siteAndArea),
//				dataType:'json',
//				success:function(areaid){
//					var temp = {"areaId": areaid,"name": nametext};
//					 jQuery.ajax({
//							type:'POST',
//							contentType : 'application/json',
//							url:'getnameselect',
//							data:JSON.stringify(temp),
//							dataType:'json',
//							success:function(result){
//								var optionstring = "<option></option>";
//								for(var item in result){
//									var temp =result[item].name;   				
//									optionstring +="<option>"+temp+"</option>"
//								}
//								$("#nameselect").html(optionstring);
//			//			         if(result.length<20 ){$("#nameselect")[0].size = result.length+1}
//			//			         else{$("#nameselect")[0].size =20;}
//			//					 $("#nameselect").offset({top:550});
//			//					 $("#nameselect").css("height","auto");
//			//					 $("#nameselect").each(function(){this.selectedIndex=-1});
//								
//							}
//						
//					          })
//				}
//
//		});
//	})
	
	function delunmake(event){
		
		var addr =$(event.target).parent().parent()[0].id;
		var logoPt = {"addr": addr};
    	jQuery.ajax({
			type:'POST',
			contentType : 'application/json',
			url:'deletefacbyaddr',
			data:JSON.stringify(logoPt),
			dataType:'json',
			success:function(data){
				
				if(data.isSuccess==0){
					 window.location.href="./login.html";
				}
				else{
					
					layer.close(implayer);
					
					$("#imp").click();
				}

			}
	          
    	});
		
	}
	
	$("#imp").click(function(){
		var siteAndArea = {"building": $("#area").getSelectedText().split(",")[0],
	            "floor": $("#area").getSelectedText().split(",")[1]};
		 implayer= $.layer({
	        type: 1,
	        title: "Select a facility", //不显示默认标题栏
	        area: ['560px', '500px'],
	        page: {html:'<form id="impform"><div id = "title1" >Address</div><div id = "unmark"></div></form>'},
	        success: function(elem){
			  jQuery.ajax({
					type:'POST',
					contentType : 'application/json',
					url:'loadName',		
					data:JSON.stringify(siteAndArea),
					dataType:'json',
					success:function(result){
						var optionstring = "";
						for(var item in result){
							var tem = JSON.parse(result[item].name)[0];
							optionstring +="<ul id =\""+result[item].addr+"\"><li class = \"li1\">"+result[item].addr+"</li><li class = \"li2\">"+tem.en+"</li><a class=\"Btn btn btn-primary\"><i  class = \"fa fa-times\"></i></a></ul>";
						}
						$("#unmark").html(optionstring);
						if(optionstring!=null&&optionstring!=""){
							var lis = document.getElementById("unmark").getElementsByTagName("li");
							for(var i=0;i<lis.length; i++){
							    if(lis[i].tagName=="LI"){			        
							        lis[i].addEventListener("click",listClick,false)
							    }
							  }	
						}
						
						if(optionstring!=null&&optionstring!=""){
							var lis = document.getElementById("unmark").getElementsByTagName("a");
							for(var i=0;i<lis.length; i++){
							    if(lis[i].tagName=="A"){			        
							        lis[i].addEventListener("click",delunmake,false)
							    }
							  }	
						}

					    }
					});
	        	
	        }
		})
		
	})
	


	
  function listClick(event)
  {
	var qstr = {"areaId": areaid,"addr":$(event.target).parent()[0].id};
		
		jQuery.ajax({
			type:'POST',
			contentType : 'application/json',
			url:'loadfactem',
			data:JSON.stringify(qstr),
			dataType:'json',
			success:function(result){
				var name = JSON.parse(result.name);
				var detail = JSON.parse(result.details);
				$("#chn_name").val(name[1]["chn-hk"]);
				$("#addr").val(result.addr);
				$("#details").val(detail[0]["en"]);
				$("#chn_details").val(detail[1]["chn-hk"]);
				$("#name").val(name[0]["en"]);
				var type = (result.typeId).toString();
				
			    jQuery.ajax({
					type:'POST',
					contentType : 'application/json',
					url:'getTypebyId',		
					data:type,
					dataType:'json',
					success:function(result){
						var typedetail = JSON.parse(result.details);
						$("#factype option").filter(function () { return $(this).html() == typedetail[0]["en"] }).prop("selected",true);
						
					}
			    })
				

				}		    		
		
	          })
	  layer.close(implayer);
  }
	
//	$("#addtype").click(function(){
//		var types= $.layer({
//	        type: 1,
//	        title: "Choose type", //不显示默认标题栏
//	        shade: [0], //不显示遮罩
//	        area: ['500px', '500px'],
//	        page: {html:'<div id="typelist"></div><div><a class = "Btn btn btn-primary" id = "type_ok" onclick="">OK</a></div>'},
//	        success: function(elem){
//	        	  jQuery.ajax({
//	        			type:'GET',
//	        			contentType : 'application/json',
//	        			url:'loadType',		
//	        			success:function(data){	
//	        				var optionstring = "";
//	        				for(var item in data){
//	        					var temp = JSON.parse(data[item].details)[0].en;   				
//	        					optionstring +="<div><label><input value="+data[item]._id+" type =checkbox name = typecheckbox></label>"+temp+"</div>"
////	        					factype.options[item].value=temp;			
//	        				}
//	        				
//	        				$("#typelist").html(optionstring);
////	        				var selectedlist = $("#selectedtype").find("label");
//	        				if($("#selectedtype>label").length>0){
//	        					$("#selectedtype>label").each(function(){
//	        						var typeid = $(this).attr('name');
//	        						$("[name=typecheckbox]:checkbox[value='"+typeid+"']").attr('checked','true');		
//	        					})
//	        				}
//		    	           	$("input[name='typecheckbox']").change(function(){
//		    	           		 
//		    	           		 var htmlstr = $("#selectedtype").html();
//		    	           		 
//		    	           		 if(this.checked){
//		    	           			 
//		    	           			htmlstr += "<label name = "+this.value+">"+this.parentNode.nextSibling.data+"</label>"
//		    	           			$("#selectedtype").html(htmlstr)
//		    	           		 }
//		    	           		 
//		    	           		 else{
//		    	           			 $("label[name='"+this.value+"']").remove();
//		    	           		 }
////		    					
//		    	         		
//		    	           	  })
//	        			    }
//	        			});
//	        }
//		})
//	})
	
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
	$("#hidelogo").click(function(){ 
		if($("#lg").is(":visible")==true){
			$("#i2").hide();
			$("#i1").show();
			$("#lg").hide();
		}
		else
		{
			$("#lg").show();
			$("#i1").hide();
			$("#i2").show();
		}
//		$(this).toggleClass("active"); return false; 
		});

})
