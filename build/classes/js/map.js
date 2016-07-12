$(document).ready(function(){
	var site = null;
	var outbuildingId = null;
	
	//get the text of selectedbox
	$.fn.getSelectedText=function(){
		if(this.size()!==0) {
			var index = jQuery(this).get(0).selectedIndex;
			if(index == -1){
				return null;
			}
			return jQuery(this).get(0).options[index].text;
		}
	}
	
	jQuery.ajax({
		contentType : 'application/json',
		url:'checklogin',
		dataType:'json',
		success:function(data){
			if(data.role === 1){
				loadadmin();
				loadSiteInfo();
			} 
			else if (data.role === 0){
				loaduser();
				loadSiteInfo();
			}
			else if(data.role === -1){
				window.location.href="./login.html";
			}
		}
	})
	
	//加载site信息 然后顺便加载building信息
	function loadSiteInfo(){
		jQuery.ajax({
			contentType : 'application/json',
			url:'loadSite',	
			dataType:'json',
			success:function(data){
				if(data._id == "-1"){
					var buildingopt = "";
					$("#sitename").attr("placeholder","Please add a new site");
					buildingopt +="<option>Please add a Building</option>";
					$("#building").html(buildingopt);
				}
				else{
					if(data.name.length>0){
						console.log(data.name);
						$("#sitename").val(JSON.parse(data.name)[0].en);
					}
				
				    /*如果没有site信息就不加载*/
					jQuery.ajax({
						contentType : 'application/json',
						url:'loadBuilding',
						dataType:'json',
						success:function(data){
							if(data[0]){
								//数据不为空 加载
								var buildingopt = "";
								buildinglist = new Array();
								arealist = new Array();
								for(var item in data){
									if(data[item].name!=""){
										buildinglist[item] =  JSON.parse(data[item].name)[0].en;
										buildingopt +="<option value = "+data[item]._id+ ">"+buildinglist[item]+"</option>"	
									}
									
								}
								$("#building").html(buildingopt);
								$("#building").change();
							}
							else{
								$("#building").html("<option>No building exists</option>");
								$("#area").html("<option>No area exists</option>");
							}
						}
					})
				}
			}
	 	})
	}
	
	//编辑site 	
	$("#editsite").on("click",function(){
		//以下是弹出窗口
		var site = $("#sitename").val();
		var editsite= $.layer({
	        type: 1,
	        title: "Site information", //不显示默认标题栏
	        shade: [0], //不显示遮罩
	        area: ['600px','450px'],
	        page:{html:'<form  id = "editsiteform" method="post"  action="editsite" enctype="multipart/form-data"><div id = "editsitediv"><div class = "line1"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "site_en">Site Name(EN):</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "site_en"  name="site_en" type = "text"></input></div></div><div class = "line1"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "site_cn">Site Name(ZH-HK):</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "site_cn"  name="site_cn" type = "text"></input></div></div><div class = "line1"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "apps">Apps:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "apps"  name="apps" type = "text"></input></div></div><div class = "line1"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "language">Language:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><span id = "langs_selected"></span><div id = "checkboxdiv"><span class = "check"><input id = "langs_en"  name="langs" type="checkbox" value="EN">English</span><span class = "check"><input id = "langs_hk"  name="langs" type="checkbox" value = "ZH-HK">ZH-HK</span><span class = "check"><input id = "langs_ch"  name="langs" type="checkbox" value = "ZH-CH">ZH-CH</span></div></div></div><div class = "line1"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "buildinglist">Default Building:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><select id = "buildinglist" name = "buildinglist"></select></div></div><div><a class = "Btn btn btn-primary" id = "addbtn" onclick="">Save</a></div></div></form>'},
	        success: function(elem){
	        	
	        	jQuery.ajax({
    				type:'POST',
    				contentType : 'application/json',
    				url:'loadSite',
    				data:site,
    				dataType:'json',
    				success:function(data){
    					if(data._id == "-1"){
    						//如果找不到对应的site信息
							$("#site_en").attr("placeholder","Please input the name");
							$("#site_cn").attr("placeholder","Please input the name");
							$("#apps").attr("placeholder","Please input the apps info");
						}
    					else{
	    					if(data.name.length>0){
	        					$('#site_en').val(JSON.parse(data.name)[0]["en"]);
	        					$('#site_cn').val(JSON.parse(data.name)[1]["chn-hk"]);
	    					}
	    					$('#apps').val(data.apps);
	    					var buildings = null;
	    					if(data.buildings.length>0){
	    						buildings = data.buildings.split(",");
	    					}
	    					
	    					
	    					jQuery.ajax({
	    						contentType : 'application/json',
	    						url:'loadBuilding',
	    						dataType:'json',
	    						success:function(data){
	    							var buildingopt = "";
	    							buildinglist = new Array();
	    							arealist = new Array();
	    							var defaultbuildingid = -1;
	    							for(var item in data){
	    								buildinglist[item] =  JSON.parse(data[item].name)[0].en;
	    								buildingopt +="<option value = "+data[item]._id+ ">"+buildinglist[item]+"</option>"
	    								if(buildings!=null){
	    									if(data[item]._id == buildings[0]){defaultbuildingid=item }
	    								}
	    								
	    							}
	    							$("#buildinglist").html(buildingopt);
	    							
	    							$("#buildinglist").each(function(){this.selectedIndex=defaultbuildingid});		
	    						}
	    					
	    					})
	    					var langsstr = data.langs.substring(1,data.langs.length-1)
	    					var langslist = langsstr.split(",");
	    					var langsselected ="";
	    					for(var i in langslist){
	    						if(langslist[i]!="")
	    						{
	    							var temp = langslist[i].replace(/"/g,"")
	    							$("[name=langs]:checkbox[value='"+temp+"']").attr('checked','true');		
	    							langsselected +="<label name = "+temp+">"+temp+"</label>"
	    						}
	    					}
	    					$("#langs_selected").html(langsselected);
    					}
    					

			        	elem.find('#addbtn').on('click', function(){
			        		var sitedetail =$("#editsiteform").serializeSite();	
    	        			var buildingstr = "";
    	        			if(buildings!=null){
    	        				buildingstr = "["+buildings.toString()+"]";
    	        			}
    	        			 
    	        			buildingstr = buildingstr.replace(/\,/g , ", ");
    	        			sitedetail.buildings = buildingstr;
    	        			sitedetail.img = "img";
    	        			console.log(JSON.stringify(sitedetail));
			        		jQuery.ajax({
    		            		type:'POST',
        		            	contentType : 'application/json',
        		            	url:'savesite',
        	        			data:JSON.stringify(sitedetail),
        		            	dataType:'json',
        		            	success:function(data){
        		            		layer.close(editsite);
        		            	}
		            		});
			        	});
    		        	
		    	        $("input[name='langs']").change(function(){
		    	           		 
		    	           		 var htmlstr = $("#langs_selected").html();
		    	           		 
		    	           		 if(this.checked){
		    	           			 
		    	           			htmlstr += "<label name = "+this.value+">"+this.value+"</label>";
		    	           			$("#langs_selected").html(htmlstr);
		    	           		 }
		    	           		 
		    	           		 else{
		    	           			 $("label[name='"+this.value+"']").remove();
		    	           		 }
		    	        });
    	         	
    				}
	        	})
	        },
	    });
		
	})
	
	//更换building的时候更新area
	$("#building").change(function(){
		var areaopt = "";
		var site = $("#building").getSelectedText();	
			jQuery.ajax({
				contentType : 'application/json',
				url:'loadBuilding',
				dataType:'json',
				success:function(data){
					var buildingopt = "";
					buildinglist = new Array();
					arealist = new Array();
					for(var item in data){
						if(site == JSON.parse(data[item].name)[0].en){
							var arealist = data[item].areas;
							var arealist = arealist.split(",");
						}	
					}
					jQuery.ajax({
						contentType : 'application/json',
						url:'loadAreaList',
						dataType:'json',
						success:function(data1){
							console.log(data1);
							for(var a in arealist){
								for(var b in data1){
									if(arealist[a]==data1[b]._id){
										areaopt +="<option value = "+data1[b]._id+ ">" +JSON.parse(data1[b].name)[0].en+"</option>"										
									}
							    }
						    }
							if(!areaopt) areaopt = "<option value = 'No area exists'></option>";
							$("#area").html(areaopt);
						}
					})
					
				}
			
			})
			
	 	})

		
		$("#delarea").on("click",function(){
			
			if($("#area").getSelectedText()!=null){
			var pageii = $.layer({
			        type: 1,
			        title: "Alert", //不显示默认标题栏
			        //shade: [0], //不显示遮罩
			        area: ['400px', '200px'],
			        page:{html:'<div id ="delalert">Are you sure that you want to permanently delete this area?</div><div id = "alertbtn"><a class = "Btn btn btn-primary" id = "yes">Yes</a><a class = "Btn btn btn-primary" id = "no">No</a></div>'},
			        success: function(elem){
				        elem.find('#no').on('click', function(){
				        	layer.close(pageii);
				        });
				        elem.find('#yes').on('click', function(){
				        	layer.close(pageii);
				        	jQuery.ajax({
				        		type:'POST',
				        		contentType : 'application/json',
				        		url:'delArea',
				        		data:$("#area").val(),
				        		dataType:'json',
				        		success:function(data){	
					            	
				        			if(data.isSuccess==0){
		            					window.location.href="./login.html";
		            				}
			        				else{
			        					alert("Delete success!");
			        					$("#building").change();
			        				}
				        		}
				        	})
				        });
			        }
			   })
			}
			   
		})
		
		$("#delbuilding").on("click",function(){
			if($("#building").getSelectedText()!=null){
			var buildingalert = $.layer({
			        type: 1,
			        title: "Warning", //不显示默认标题栏
			        //shade: [0], //不显示遮罩
			        area: ['400px', '150px'],
			        page:{html:'<div id ="delbuildingalert">Are you sure that you want to permanently delete this Building,and areas belong to it?</div><div id = "buildingalertbtn"><a class = "Btn btn btn-primary" id = "buildingyes">Yes</a><a class = "Btn btn btn-primary" id = "buildingno">No</a></div>'},
			        success: function(elem){
				        elem.find('#buildingno').on('click', function(){
				        	layer.close(buildingalert);
				        });
				        elem.find('#buildingyes').on('click', function(){
				        	jQuery.ajax({
				        		type:'POST',
				        		contentType : 'application/json',
				        		url:'delBuilding',
				        		data:$("#building").val(),
				        		dataType:'json',
				        		success:function(buildings){
				        							            	
				        			if(buildings.isSuccess==0){
		            					window.location.href="./login.html";
		            				}
			        				else{
			        					alert("Delete success!");
			        					loadSiteInfo();
			        					layer.close(buildingalert);
			        				}
				        		}
				        	})
				        });
			        }
			   })
			}
			   
		})

		
		$('#addbuilding').on('click', function(){
			var  buildingid = -1;
			var addbuilding= $.layer({
		        type: 1,
		        title: "Add New Buiding", //不显示默认标题栏
		        shade: [0], //不显示遮罩
		        area: ['600px', '450px'],
		        page:{html:'<form  id = "addBuildingform" method="post"  action="savebuilding" enctype="multipart/form-data"><div id = "addbuildingdiv"><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "building1"><i class="fa fa-asterisk" style="color: #d9534f;"></i>Building:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "building1"  name="building1" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "zhhk_building1"><i class="fa fa-asterisk" style="color: #d9534f;"></i>Building(ZH-HK)</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "zhhk_building1"  name="zhhk_building1" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "zhcn_building1"><i class="fa fa-asterisk" style="color: #d9534f;"></i>Building(ZH-CN):</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "zhcn_building1"  name="zhcn_building1" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "gps">GPS:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "gps"  name="gps" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "scale">Scale:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "scale"  name="scale" type = "text" onKeypress="return (/\d/.test(String.fromCharCode(event.keyCode)))" style="ime-mode:Disabled"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "north">North:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "north"  name="north" type = "text" onKeypress="return (/\d/.test(String.fromCharCode(event.keyCode)))" style="ime-mode:Disabled"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "ifoutbuilding">Outdoor Building:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><div class = "col-lg-6 col-sm-6 col-xs-6"><input type="radio" name="ifoutbuilding" value="yes">Yes</div><div class = "col-lg-6 col-sm-6 col-xs-6"><input type="radio" name="ifoutbuilding" value="no" checked ="checked">No</div></div></div><div><a class = "Btn btn btn-primary" id = "addbtn">Save</a></div></div></form>	'},
		        success: function(elem){
			        	elem.find('#addbtn').on('click', function(){
			        		var buildingdetail =$("#addBuildingform").serializeBuilding();
        		            buildingdetail._id = "";
        		            buildingdetail.img = "img";
        		            jQuery.ajax({
	        		            type:'POST',
		        		        contentType : 'application/json',
		        		        url:'savebuilding',
		                    	data:JSON.stringify(buildingdetail),
		        		        dataType:'json',
		        		        success:function(data){
		        		            refreshbuilding();
		        		            layer.close(addbuilding);
		        		        }
        		            });
			        	});
		        }
			});
		});
		
		
		$('#editbuilding').on('click', function(){
			var building = $("#building").getSelectedText();
			var buildingid = -1;
			if(building!=null){
			var editbuilding= $.layer({
		        type: 1,
		        title: "Buiding information", //不显示默认标题栏
		        shade: [0], //不显示遮罩
		        area: ['600px', '450px'],
		        page:{html:'<form  id = "addBuildingform" method="post"  action="savebuilding" enctype="multipart/form-data"><div id = "addbuildingdiv"><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "building1">Building:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "building1"  name="building1" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "zhhk_building1">Building(ZH-HK)</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "zhhk_building1"  name="zhhk_building1" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "zhcn_building1">Building(ZH-CN):</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "zhcn_building1"  name="zhcn_building1" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "gps">GPS:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "gps"  name="gps" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "scale">Scale:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "scale"  name="scale" type = "text" onKeypress="return (/\d/.test(String.fromCharCode(event.keyCode)))" style="ime-mode:Disabled"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "north">North:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><input id = "north"  name="north" type = "text" onKeypress="return (/\d/.test(String.fromCharCode(event.keyCode)))" style="ime-mode:Disabled"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "ifoutbuilding">Outdoor Building:</label></div><div class = "col-sm-8 col-xs-8 col-lg-8"><div class = "col-sm-6 col-xs-6 col-lg-6"><input type="radio" name="ifoutbuilding" value="yes">Yes</div><div class = "col-sm-6 col-xs-6 col-lg-6"><input type="radio" name="ifoutbuilding" value="no" checked ="checked">No</div></div></div><div><a class = "Btn btn btn-primary" id = "addbtn">Save</a></div></div></form>'},
		        success: function(elem){
		        	
		        	jQuery.ajax({
        				type:'POST',
        				contentType : 'application/json',
        				url:'getbuilding',
        				data:building,
        				dataType:'json',
        				success:function(data){

          					for(var i in JSON.parse(data.name)){
		        				if(JSON.parse(data.name)[i]["en"]){
		        					$('#building1').val(JSON.parse(data.name)[i]["en"]);
		        				}
		        				if(JSON.parse(data.name)[i]["chn"]){
		        					$('#zhcn_building1').val(JSON.parse(data.name)[i]["chn"]);
		        				}
 		        				if(JSON.parse(data.name)[i]["chn-hk"]){
		        					$('#zhhk_building1').val(JSON.parse(data.name)[i]["chn-hk"]);
		        				}
        					}
          					if(data.isoutdoor == 1)
          						$('input:radio[name="ifoutbuilding"]').filter('[value="yes"]').prop('checked',true)
        					$('#gps').val(data.gps);
        					$('#scale').val(data.scale);
        					$('#north').val(data.north);
        					$('#verbuilding').val(data.ver);
        					buildingid = data._id;

        		            elem.find('#choose_amap').on('click', function(){
				        		$("#img_amap").click();
				        	});
				        	elem.find('#img_amap').on('change', function(){
				        		$("#img_amap_text").val($("#img_amap").val());
				        	});
                            elem.find('#addbtn').on('click', function(){
                            	var buildingdetail =$("#addBuildingform").serializeBuilding();
                    			buildingdetail._id = buildingid;
				        		if(!$("#img_amap_text").val()){
				        			buildingdetail.img = "";
				        			console.log(buildingdetail);
		        		            jQuery.ajax({
		        		            		type:'POST',
		        		            		contentType : 'application/json',
		        		            		url:'savebuilding',
		                    				data:JSON.stringify(buildingdetail),
		        		            		dataType:'json',
		        		            		success:function(data){
		        		            			alert("Update Successfully");
		        		            			layer.close(editbuilding);
		        		            			refreshbuilding();
		        		            		}
		        		            });
				        		}
				        		else{
				        			 $.ajaxFileUpload({
	        		            		type:'POST',
	        		            		url:'saveaimg',
	        		            		secureuri:false,
	        		            		fileElementId:'img_amap',
	        		            		dataType : 'text',   
	        		            		success : function (data, status){
	        		            			buildingdetail.img = data;
	        		            			jQuery.ajax({
		        		            			type:'POST',
			        		            		contentType : 'application/json',
			        		            		url:'savebuilding',
			                    				data:JSON.stringify(buildingdetail),
			        		            		dataType:'json',
			        		            		success:function(data){
			        		            			alert("Update Successfully");
			        		            			layer.close(editbuilding);
			        		            			refreshbuilding();
			        		            		}
	        		            			});
	        		            		}
				        			});
				        		}
                            });
        		        	
        		        	elem.find('#loadmap').on('click', function(){
				        		alert(buildingid);
				        		var maplayer= $.layer({
        		    		        type: 1,
        		    		        title: "Map", //不显示默认标题栏
        		    		        shade: [1], //不显示遮罩
        		    		        area: ['600px', '500px'],
        		    		        page: {html:'<div id = "mapdiv"><canvas id = "map" width = 0 height = 0></canvas><a class = "Btn btn btn-primary" id = "center_ok" onclick="">OK</a></div>'},
        		    		        success: function(elem){
	        		    		          var container = new createjs.Container();
	            		    			  var mapstage = new createjs.Stage("map");
	    		      					  mapstage.addChild(container);
	            		    			  mapstage.enableMouseOver(20);
	    		    		        	  var myCanvas = $("#map").get(0); 
	    		    		        	  var context = myCanvas.getContext("2d");
	    		    		        	  var MapImage = new Image();
	    		    		        	  jQuery.ajax({
		          		    		      	  type:'POST',
		          		    		      	  contentType : 'application/json',
	          		    		      		  url:'showmap_building',		
	          		    		      		  data:buildingid,
	          		    		      		  dataType:'text',
	          		    		      		  beforeSend:function(){
	          		    		        	           $("#loading").css('visibility','visible');
	          		    		        	  },
          		    		      		      success:function(data){		
          		    		      		    	  MapImage.src="data:image/png;base64,"+data;
          		    		      		    	  myCanvas.width = MapImage.width*0.5;
          		    		      		    	  myCanvas.height = MapImage.height*0.5;
          		    		      		    	  $("#map").css("background","url("+MapImage.src+")");
          		    		      		    	  $("#map").css("background-size","100% 100%");
          		    		      		      }
	    		    		        	  });
        		    		         }
				        		});
				        	});
        				}
		        	})
		        },
		    });
			} 	
		})
		
		
		$('#addarea').on('click', function(){
			if(!$("#building").getSelectedText()){
				alert("Please select the building first");
			}
			else{
				var addlayer= $.layer({
			        type: 1,
			        title: "Add New Area", //不显示默认标题栏
			        shade: [0], //不显示遮罩
			        area: ['600px', '500px'],
			        //page:{html:'<form id="addform" method="post"  action="savearea"><div id = "add"><div style="display:none"><span>AreaID</span><input type="text" name="_id" id="_id"></div></span><div><span>Building:</span><input id = "buildingname" name = "building" type = "text"></input></div><div><span>Floor(EN):</span><input id = "floor"  name="floor" type = "text"></input></div><div><span>Floor(ZH-HK):</span><input id = "chnhk_floor"  name="chnhk_floor" type = "text"></input></div><div><span>Floor(ZH-CN):</span><input id = "chn_floor"  name="chn_floor" type = "text"></input></div><div><span>Altitude:</span><input id = "altitude"  name="altitude" type = "text"></input></div><div><span>swRegions:</span><input id = "swRegions"  name="swRegions" type = "text"></input></div><div><span>Center:</span><input id = "center"  name="img_center" type = "text"></input></div><div><span>Upload Map:</span><a class = "Btn btn btn-primary" id = "choose_amap" style="float:right" onclick="">Choose</a><input id ="img_amap_text" name="img_amap_text" type = "text"></input><input id="img_amap" name ="img_amap" style="display:none" type="file"></div><div><a class = "Btn btn btn-primary" id = "addbtn" onclick="">Save</a></div></div></form>'},
			        page:{html:'<form  id = "addform" method="post"  action="savearea" enctype="multipart/form-data"><div id = "addbuildingdiv"><div class = "line2" style = "display:none"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "_id">AreaID:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "_id"  name="_id" type = "text" readonly = "readonly"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "buildingname">Building</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "buildingname"  name="building" type = "text" readonly="readonly"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "floor"><i class="fa fa-asterisk" style="color: #d9534f;"></i>Floor(EN):</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "floor"  name="floor" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "chnhk_floor"><i class="fa fa-asterisk" style="color: #d9534f;"></i>Floor(ZH-HK):</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "chnhk_floor"  name="chnhk_floor" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "chn_floor"><i class="fa fa-asterisk" style="color: #d9534f;"></i>Floor(ZH-CN):</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "chn_floor"  name="chn_floor" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "altitude">Altitude:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "altitude"  name="altitude" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "swRegions">swRegions:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "swRegions"  name="swRegions" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "center">Center:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "center"  name="img_center" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "img_amap_text"><i class="fa fa-asterisk" style="color: #d9534f;"></i>Upload Map:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id ="img_amap_text" name="img_amap_text" type = "text"></input><input id="img_amap" name ="img_amap" style="display:none" type="file"></div><div class = "col-sm-2 col-xs-2 col-lg-2"><a class = "Btn btn btn-primary" id = "choose_amap">Choose</a></div></div><div><a class = "Btn btn btn-primary" id = "loadmap">Map</a><a class = "Btn btn btn-primary" id = "addbtn">Save</a></div></div></form>'},
			        success: function(elem){
			        	$('#buildingname').val($("#building").getSelectedText());
			        	elem.find('#choose_amap').on('click', function(){
			        		$("#img_amap").click();
			        	});
			        	elem.find('#img_amap').on('change', function(){
			        		$("#img_amap_text").val($("#img_amap").val());
			        	});
			        	elem.find('#addbtn').on('click', function(){
			        		if(!$("#img_amap_text").val()){
			        			alert("Please upload the map");
			        		}
			        		else{
			        			if(!$("#altitude").val())
			        				$("#altitude").val(0);
			        			var areadetail =$("#addform").serializeArea();
			        			console.log(areadetail);
			        			$.ajaxFileUpload({
        		            		type:'POST',
        		            		url:'saveaimg',
        		            		secureuri:false,
        		            		fileElementId:'img_amap',
        		            		dataType : 'text',   
        		            		success : function (data, status){
        		            			areadetail.imgid = data;
        		            			console.log(areadetail);
        		            			jQuery.ajax({
	        		            			type:'POST',
		        		            		contentType : 'application/json',
		        		            		url:'savearea',
		        		            		data:JSON.stringify(areadetail),
		        		            		dataType:'json',
		        		            		success:function(data){
		        		            			$("#building").change();
		        		            			layer.close(addlayer);
		        		            		}
        		            			});
        		            		}
			        			});
			        		}
			        	});
			        },	
				});
		    }
		})
		
		$('#editarea').on('click', function(){
		    var form = {"building": $("#building").val(),
			            "floor": $("#area").val()}
			var areaid = -1;
			if($("#area").getSelectedText()!=null){
				var editarea= $.layer({
				        type: 1,
				        title: "Edit Area", //不显示默认标题栏
				        shade: [0], //不显示遮罩
				        area: ['600px', '500px'],
				        //page:{html:'<form id="addform" method="post"  action="savearea"><div id = "add"><div><span>AreaID:</span><input id = "areaid"  name="_id" type = "text" readonly="readonly" style="ime-mode:Disabled"></input></div><div><span>Building:</span><input id = "buildingname" name = "building" type = "text"></input></div><div><span>Floor(EN):</span><input id = "floor"  name="floor" type = "text"></input></div><div><span>Floor(ZH-HK):</span><input id = "chnhk_floor"  name="chnhk_floor" type = "text"></input></div><div><span>Floor(ZH-CN):</span><input id = "chn_floor"  name="chn_floor" type = "text"></input></div><div><span>Altitude:</span><input id = "altitude"  name="altitude" type = "text"></input></div><div><span>swRegions:</span><input id = "swRegions"  name="swRegions" type = "text"></input></div><div><span>Center:</span><input id = "center"  name="img_center" type = "text"></input></div><div><span>Upload Map:</span><a class = "Btn btn btn-primary" id = "choose_amap" style="float:right" onclick="">Choose</a><input id ="img_amap_text" name="img_amap_text" type = "text"></input><input id="img_amap" name ="img_amap" style="display:none" type="file"></div><div><a class = "Btn btn btn-primary" id = "loadmap">Map</a><a class = "Btn btn btn-primary" id = "addbtn" onclick="">Save</a></div></div></form>'},
				        page:{html:'<form  id = "addform" method="post"  action="savearea" enctype="multipart/form-data"><div id = "addbuildingdiv"><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "areaid">AreaID:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "areaid"  name="_id" type = "text" readonly = "readonly"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "buildingname">Building</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "buildingname"  name="building" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "floor">Floor(EN):</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "floor"  name="floor" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "chnhk_floor">Floor(ZH-HK):</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "chnhk_floor"  name="chnhk_floor" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "chn_floor">Floor(ZH-CN):</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "chn_floor"  name="chn_floor" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "altitude">Altitude:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "altitude"  name="altitude" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "swRegions">swRegions:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "swRegions"  name="swRegions" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "center">Center:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id = "center"  name="center" type = "text"></input></div></div><div class = "line2"><div class = "col-sm-4 col-xs-4 col-lg-4"><label for = "img_amap_text">Upload Map:</label></div><div class = "col-sm-6 col-xs-6 col-lg-6"><input id ="img_amap_text" name="img_amap_text" type = "text"></input><input id="img_amap" name ="img_amap" style="display:none" type="file"></div><div class = "col-sm-2 col-xs-2 col-lg-2"><a class = "Btn btn btn-primary" id = "choose_amap">Choose</a></div></div><div><a class = "Btn btn btn-primary" id = "loadmap">Map</a><a class = "Btn btn btn-primary" id = "addbtn">Save</a></div></div></form>'},
				        success: function(elem){
				        	console.log(JSON.stringify(form));
				        	jQuery.ajax({
		        				type:'POST',
		        				contentType : 'application/json',
		        				url:'getarea',
		        				data:JSON.stringify(form),
		        				dataType:'json',
		        				success:function(data){
		        					console.log(data);
		        					areaid = data._id;	
		        					$('#buildingname').val($("#building").getSelectedText());
				        			$('#areaid').val(data._id);
				        			for(var i in JSON.parse(data.name)){
				        				if(JSON.parse(data.name)[i]["en"]){
				        					$('#floor').val(JSON.parse(data.name)[i]["en"]);
				        				}
				        				if(JSON.parse(data.name)[i]["chn"]){
				        					$('#chn_floor').val(JSON.parse(data.name)[i]["chn"]);
				        				}
		 		        				if(JSON.parse(data.name)[i]["chn-hk"]){
				        					$('#chnhk_floor').val(JSON.parse(data.name)[i]["chn-hk"]);
				        				}
				        			}        		        	
				        			$('#ver').val(data.ver); 
				        			$('#swRegions').val(data.swregions.substr(1,data.swregions.length-2));
				        			$('#altitude').val(data.altitude);
				        			if(data.img_center.length>0){
		      		        			var entr =JSON.parse(data.img_center);
		    		        			$('#center').val(entr[0]+","+entr[1]);
				        			}
				        			else{
				        				$('#center').val("");
				        			}
		        				}
				        	});
				        	elem.find('#choose_amap').on('click', function(){
				        		$("#img_amap").click();
				        	});
				        	elem.find('#img_amap').on('change', function(){
				        		$("#img_amap_text").val($("#img_amap").val());
				        	});
				        	elem.find('#loadmap').on('click', function(){
				        		var areaid = $('#areaid').val();
				        		var maplayer= $.layer({
        		    		        type: 1,
        		    		        title: "Map", //不显示默认标题栏
        		    		        shade: [1], //不显示遮罩
        		    		        area: ['600px', '500px'],
        		    		        page: {html:'<div id = "mapdiv"><canvas id = "map" width = 0 height = 0></canvas><a class = "Btn btn btn-primary" id = "center_ok" onclick="">OK</a></div>'},
        		    		        success: function(elem){
	        		    		          var container = new createjs.Container();
	            		    			  var mapstage = new createjs.Stage("map");
	    		      					  mapstage.addChild(container);
	            		    			  mapstage.enableMouseOver(20);
	    		    		        	  var myCanvas = $("#map").get(0); 
	    		    		        	  var context = myCanvas.getContext("2d");
	    		    		        	  var MapImage = new Image();
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
          		    		      		    	  myCanvas.width = MapImage.width*0.5;
          		    		      		    	  myCanvas.height = MapImage.height*0.5;
          		    		      		    	  $("#map").css("background","url("+MapImage.src+")");
          		    		      		    	  $("#map").css("background-size","100% 100%");
          		    		      		      }
	    		    		        	  });
        		    		         }
				        		});
				        	});
				        	elem.find('#addbtn').on('click', function(){
				        		if(!$("#img_amap_text").val()){
				        			var areadetail =$("#addform").serializeArea();
				        			areadetail.imgid="";
		        		            jQuery.ajax({
		        		            		type:'POST',
		        		            		contentType : 'application/json',
		        		            		url:'savearea',
		        		            		data:JSON.stringify(areadetail),
		        		            		dataType:'json',
		        		            		success:function(data){
		        		            			$("#building").change();
		        		            			layer.close(editarea);
		        		            		}
		        		            });
				        		}
				        		else{
				        			var areadetail =$("#addform").serializeArea();
				        			 $.ajaxFileUpload({
	        		            		type:'POST',
	        		            		url:'saveaimg',
	        		            		secureuri:false,
	        		            		fileElementId:'img_amap',
	        		            		dataType : 'text',   
	        		            		success : function (data, status){
	        		            			alert(data);
	        		            			areadetail.imgid = data;
	        		            			jQuery.ajax({
		        		            			type:'POST',
			        		            		contentType : 'application/json',
			        		            		url:'savearea',
			        		            		data:JSON.stringify(areadetail),
			        		            		dataType:'json',
			        		            		success:function(data){
			        		            			$("#building").change();
			        		            			layer.close(editarea);
			        		            		}
	        		            			});
	        		            		}
				        			});
				        		}
				        	});
				         }
					});
				}
			});

		
	   $.fn.serializeArea = function()    
		{ 			
		   var o = {};    
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
				   
				   if (this.name=="floor" && $("#floor").val()!= "" ){					  
					   var d_en = {};
					   d_en["en"] = $("#floor").val();
					   nameJson.push(d_en);
				   }
				   
				   else if (this.name=="chnhk_floor" && $("#chnhk_floor").val()!=""){					 
					   var d_ch = {};
					   d_ch["chn-hk"] = $("#chnhk_floor").val()
					   nameJson.push(d_ch);					  

				   }
				   else if (this.name=="chn_floor" && $("#chn_floor").val()!=""){					   
						   var d_ch = {};
						   d_ch["chn"] = $("#chn_floor").val()
						   nameJson.push(d_ch);					   

				   }
				   
				   else if (this.name=="swRegions"){
					   var d_ch = {};
					   var temp = $("#swRegions").val();
					  o["swRegions"]="["+temp+"]";
				   }
				   else if (this.name=="img_center"){
					   if($("#center").val().length>0){
						   var logoList = new Array();
						   logoList[0] = parseInt($("#center").val().split(",")[0]);
						   logoList[1] = parseInt($("#center").val().split(",")[1]);
						   var temp2 = JSON.stringify(logoList);
						   temp2 = temp2.replace(/\,/g , ", ");
						   o["img_center"] = temp2;
					   }
					   else{ o["img_center"] = "";}
					   }
				   else{
					   o[this.name] = this.value || ''
					   };
				   
				   }				 
			   })
		   o["name"] =JSON.stringify(nameJson);  
		   //o["site"] = site;
		   return o;    
		};
		
		
	    $.fn.serializeBuilding = function()    
		{    
		   var o = {};    
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
				   
				   if (this.name=="building1" && $("#building1").val()!=""){
					   var d_en = {};
					   d_en["en"] = $("#building1").val();
					   nameJson.push(d_en);
				   }
				   
				   else if (this.name=="scale"){
					   o["scale"] =parseFloat($("#scale").val());   
				   }
				   else if (this.name=="north"){
					   o["north"] =parseFloat($("#north").val());   
				   }
				   
				   else if (this.name=="zhhk_building1" && $("#zhhk_building1").val()!=""){
					   var d_ch = {};
					   d_ch["chn-hk"] = $("#zhhk_building1").val()
					   nameJson.push(d_ch);
				   }
				   else if (this.name=="zhcn_building1" && $("#zhcn_building1").val()!=""){
					   var d_ch = {};
					   d_ch["chn"] = $("#zhcn_building1").val()
					   nameJson.push(d_ch);
				   }
				   else{
					   o[this.name] = this.value || ''
					   };
				   
				   }				 
			   })
		   o["name"] =JSON.stringify(nameJson);  
		   //o["site"] = site;
		   return o;    
		};
		
	    $.fn.serializeSite = function()    
		{    
		   var o = {};    
		   var nameJson = [];
		   var a = this.serializeArray(); 
		   var langslist = new Array();
		   $("#langs_selected label").each(function() {
		         langslist.push("\""+this.innerText+"\"")
	        });
		   $.each(a, function() {    
			   if (o[this.name]) {    
				   if (!o[this.name].push) {    
					   o[this.name] = [o[this.name]];    
				   }    
				   o[this.name].push(this.value || '');    
			   } 
			   else {
				   
				   if (this.name=="site_en"){
					   var d_en = {};
					   d_en["en"] = $("#site_en").val();
					   nameJson.push(d_en);
				   }
				   				   
				   else if (this.name=="apps"){
					   o["apps"] =$("#apps").val();   
				   }
				   
				   else if (this.name=="site_cn"){
					   var d_ch = {};
					   d_ch["chn-hk"] = $("#site_cn").val()
					   nameJson.push(d_ch);
				   }
				   else{
					   o[this.name] = this.value || ''
					   };
				   
				   }				 
			   })
		
		   o["langs"] = "["+langslist.toString()+"]";
		   o["name"] =JSON.stringify(nameJson);  
		   return o;    
		};
		
	    $("#uploadmap").on('change', function(){

	        if ($("#uploadmap")[0].files && $("#uploadmap")[0].files[0])
	        {
	        	 if(event.target.files[0].type == "image/png" || event.target.files[0].type == "image/jpeg")
	        	    {

			            var fReader = new FileReader();
			            fReader.onloadend = function(event){


			            }
			            fReader.readAsDataURL($("#uploadmap")[0].files[0]);

	                 }
	        	 else{
	        		   alert("The image type must be \".jpeg\",\"jpg\" or\"png\"!");  
	        		   this.value = null;
	        		   document.getElementById('textfield').value=null;
	     	           return; 
	        		 }
	            
	        }

	    });
	    
	    function selected(element,option)  
	    {  
	  	  var kk = document.getElementById(element).options;  
	  	  for (var i=0; i<kk.length; i++) {  
	  		  if (kk[i].text==option) {  
	  		  kk[i].selected=true;  
	  		  break;  
	  	      }   
	        }
	    }
		
	    $("#uploaddeclare").on('change', function(){
	    	
	    	var filename = $("#uploaddeclare").val();
	    	var extension = filename.replace(/^.*\./, '');
	    	 if (extension == filename) {
	             extension = '';
	         } else {
	             extension = extension.toLowerCase();
	         }

        	 if(extension != "html" && extension != "htm")
        	    {
	      		   alert("The image type must be \".html\", or\"htm\"!");  
	    		   this.value = null;
	    		   document.getElementById('textfield_declare').value=null;
	 	           return; 

                 }
	    });
	    
		$("#savedeclare").click(function(){
			  $.ajaxFileUpload
		        ({
		        	type:'POST',
		             url:'savedeclare', 
		             secureuri:false,
		             fileElementId:'uploaddeclare',
		             dataType : 'json',
		             success : function (data, status){
        				if(data.isSuccess==0){
      					window.location.href="./login.html";
      				}
      				else{
 		            	 alert("Saved successfully!")
		            	
		            	 $("uploaddeclare").value = null;
		         	     $("#textfield_declare").val(null);
      				}

		             }
		        })
		})

		
		function refreshbuilding(){
			jQuery.ajax({
				contentType : 'application/json',
				url:'loadBuilding',
				dataType:'json',
				success:function(data){
					if(data[0]){
						//有数据 加载
						var buildingopt = "";
						buildinglist = new Array();
						arealist = new Array();
						for(var item in data){
							if(data[item].name!=""){
								
								buildinglist[item] =  JSON.parse(data[item].name)[0].en;
								buildingopt +="<option value = "+data[item]._id+ ">"+buildinglist[item]+"</option>"	
							}
							
						}
						$("#building").html(buildingopt);
						//$("#building").each(function(){this.selectedIndex=-1});	
						$("#building").change();
					}
					else{
						//没有数据 不操作
					}
				}
			})
		}
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