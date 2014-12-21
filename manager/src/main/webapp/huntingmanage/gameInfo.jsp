<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>参数设置</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/default/linkbutton.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/default/datebox.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/icon.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=xWm90oFFoMcAf4wQ9GjVWDY0"></script>
<style>
body {
	text-align: left;
	font-size: 12px;
	font-weight: bold;
}
</style>
</head>
<body>
<div style="padding: 20px 30px 50px 30px" id="administratorView">
  <form id="viewform" class="easyui-form" action="/manage/gameinfo/saveGameInfo" method="post" data-options="novalidate:true" enctype="multipart/form-data">
    <input type="hidden" id="id" value="0" name="id"/>

    <table style="width:680px; padding-top:5px;">
      <tr >
        <td style="width:95px;" >城市：</td>
        <td style="width:200px;"><select id="gameCity" name="gameCity" class="easyui-combobox" style="width:180px;float:left" data-options="valueField:'configValue',textField:'configKey',onChange:function(newValue,oldValue){selectCity(newValue);}"/>
          
          </select></td>
        <td>添加活动城市：
          <input id="addGameCity" name="addGameCity"
						class="easyui-textbox" 
						 style="width:80px;"/>
          &nbsp;<a id="addGameCityA" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a></td>
      </tr>
      <tr>
        <td >游戏模式：</td>
        <td><input type="radio" name="gameType" value="HIDE_AND_SEEK" checked onClick="$('#gameRobGoldTimes').numberbox('disable')"/>
          捉迷藏模式
          <input type="radio" name="gameType" value="DIG_GOLD" onClick="$('#gameRobGoldTimes').numberbox('enable')"/>
          挖金子模式</td>
        <td rowspan="3" ><img id="gameLogoImg" border=0 src="/upload/default.png" style="width:150px;height:60px;"/></td>
        <input type="hidden" id="gameLogoUri" name="gameLogoUri" value=""/>
      </tr>
      <tr>
        <td >车标名称：</td>
        <td><input id="gameLogoName" name="gameLogoName"
						class="easyui-validatebox easyui-textbox" 
						data-options="required:true,validType:'maxLength[50]'" style="width:180px;" /></td>
        <td >&nbsp;</td>
      </tr>
      <tr>
        <td >车标图标：</td>
        <td><input id="logoFile" class="easyui-filebox" name="logoFile" style="width:180px" data-options="buttonText:'选择文件'"></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td >抢标距离：</td>
        <td><input id="gameGrabLogoDistance" name="gameGrabLogoDistance"
						class="easyui-validatebox easyui-textbox" 
						data-options="required:true,min:1,max:9999999" style="width:180px;" /></td>
        <td >米</td>
      </tr>
      <tr>
        <td >隐身时长：</td>
        <td><input id="gameHideTime" name="gameHideTime"
						class="easyui-validatebox easyui-numberbox" 
						data-options="required:true,min:0,max:999" style="width:180px;" />
          秒</td>
        <td >结束前
          <input id="gameHideTimeInvalid" name="gameHideTimeInvalid"
						class="easyui-validatebox easyui-numberbox" 
						data-options="required:true,min:0,max:9999" style="width:80px;" />
          秒隐身无效</td>
      </tr>
      <tr>
        <td >游戏日期：</td>
        <td><input id="gameStartDateStr" name="gameStartDateStr"
						class="easyui-datebox" 
						style="width:180px;" required data-options="currentText:'Today'"/></td>
        <td >至
          <input id="gameEndDateStr" name="gameEndDateStr"
						class="easyui-datebox" 
						style="width:180px;" required data-options="currentText:'Today'"/></td>
      </tr>
      <tr>
        <td >游戏时间：</td>
        <td><input id="gameStartTimeStr" name="gameStartTimeStr"
						class="easyui-timespinner" 
						style="width:180px;" required data-options="value:'00:01',min:'00:01',showSeconds:true"/></td>
        <td >至
          <input id="gameEndTimeStr" name="gameEndTimeStr"
						class="easyui-timespinner" 
						style="width:180px;" required data-options="value:'00:01',max:'23:59',showSeconds:true"/></td>
      </tr>
      <tr>
        <td >游戏区中心：</td>
        <td><input id="gameCenter" name="gameCenter"
						class="easyui-textbox" 
						data-options="required:true" style="width:180px;" />
          <input type="hidden" id="gameCenterLongitude" name="gameCenterLongitude" value="">
          <input type="hidden" id="gameCenterLatitude" name="gameCenterLatitude" value="">
          <input type="hidden" id="gameCenterAddress" name="gameCenterAddress" value=""></td>
        <td ><a id="gameCenterMap" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" >地图选择</a></td>
      </tr>
      <tr>
        <td >游戏区半径：</td>
        <td><input id="gameCenterRadius" name="gameCenterRadius"
						class="easyui-numberbox" 
						data-options="required:true,min:1,max:9999" style="width:180px;" /></td>
        <td>千米</td>
      </tr>
      <tr>
        <td >车标投放点：</td>
        <td><input id="gameLogoPutInPoint" name="gameLogoPutInPoint"
						class="easyui-textbox" 
						data-options="required:true" style="width:180px;" />
          <input type="hidden" id="gameLogoPutInPointLongitude" name="gameLogoPutInPointLongitude" value="">
          <input type="hidden" id="gameLogoPutInPointLatitude" name="gameLogoPutInPointLatitude" value="">
          <input type="hidden" id="gameLogoPutInPointAddress" name="gameLogoPutInPointAddress" value=""></td>
        <td ><a id="gameLogoPutInPointMap" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">地图选择</a></td>
      </tr>
      <tr>
        <td >默认显示半径：</td>
        <td><input id="gameLogoDisplayRadius" name="gameLogoDisplayRadius"
						class="easyui-numberbox" 
						data-options="required:true,min:1000,max:9999999" style="width:180px;" /></td>
        <td>米</td>
      </tr>
      <tr>
        <td >挖金子次数：</td>
        <td><input id="gameRobGoldTimes" name="gameRobGoldTimes"
						class="easyui-numberbox" 
						style="width:180px;" data-options="min:1,max:9999,value:1" disabled /></td>
        <td >次</td>
      </tr>
      <tr>
        <td >最大移动速度：</td>
        <td><input id="gameMaxMoveSpeed" name="gameMaxMoveSpeed"
						class="easyui-numberbox" 
						data-options="required:true,min:1,max:999" style="width:180px;" /></td>
        <td >千米/时</td>
      </tr>
      <tr>
        <td >最大点击速度：</td>
        <td><input id="gameMaxGetSpeed" name="gameMaxGetSpeed"
						class="easyui-numberbox" 
						data-options="required:true,min:1,max:999" style="width:180px;" /></td>
        <td >次/秒</td>
      </tr>
      <tr>
        <td >分享文本设定：</td>
        <td colspan="2"><textarea id="gameShareTextTemplate" name="gameShareTextTemplate"
						 rows="5" cols="50"
						onKeyDown="textCounter(this,'counterText',140);" onKeyUp="textCounter(this,'counterText',140);"></textarea>
          </td>
      </tr>
      <tr>
        <td>&nbsp;&nbsp;</td>
        <td colspan="2">说明:微博限制140字一条，剩下<span id="counterText" style="color:#F00">140</span>字，支持通配符$name,$score,$prize </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="formSave">保存</a>&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="formUpate">修改</a>&nbsp;&nbsp;<!--a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a--></td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </form>
</div>
<!-- width:930px;height:560px;-->
<div id="mapDialog" class="easyui-dialog" title="百度地图" style="width:580px;height:500px;"
        data-options="resizable:true,modal:false,closed:true,buttons: [{
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){
                      mapReturnValue();
                      $('#mapDialog').dialog('close');   
                    }
                },{
                    text:'取消',
                    handler:function(){
                     $('#mapLongtitude,#mapLatitude,#mapAddress').textbox('setText','');    
                     $('#mapDialog').dialog('close');                                        
                    }
                }]"> 
  <!--width:100%;height:100%-->
  <div id="map" style="width:100%;height:360px;"></div>
  <div style="margin:5px;"> 经度：
    <input type="text" id="mapLongtitude" class="easyui-textbox" style="width:190px;"/>
    &nbsp;纬度：
    <input type="text" id="mapLatitude" class="easyui-textbox" style="width:190px;"/>
  </div>
  <div style="margin:5px;"> 地址：
    <input type="text" id="mapAddress" class="easyui-textbox" style="width:433px;"/>
  </div>
</div>
<script type="text/javascript"> 
 function textCounter(field, countfield, maxlimit) {    
  	   if (field.value.length > maxlimit) {
		 field.value = field.value.substring(0, maxlimit);  
	   }
	   else{   	
		 $('#'+countfield).text(maxlimit - field.value.length);  
	   }
   } 
$(function(){	 

	//获取初始化数据
	$.ajax({
	   type: "GET",
	   url: "/manage/gameinfo/getGameList",	
	   dataType:"json",
	   success: function(msg){
		   if(msg==null)return;
		   if(msg.gameCity.length==0)return;		
		   $('#gameCity').combobox('loadData', msg.gameCity);
		   $('#gameCity').combobox('select', msg.gameCity[0].configValue);		  
	   },
	   error:function(a,b,c){
		 $.messager.show({
			title:'获取参数设置提示:',
			msg:'获取失败，系统异常！',
			showType:'show'
		}); 
	   }
	  });
	
	//添加城市事件绑定
    $('#addGameCityA').bind('click', function(){
		 _stopEvent();
        addCity($('#addGameCity').textbox('getValue'));
    });
	
	$('#logoFile').filebox({onChange:function(newValue,oldValue){
				 
		 //图片格式的校验
		 var filePath = $('#logoFile').filebox("getValue");
		 var fileSuff = filePath.substring(filePath.lastIndexOf(".")+1,filePath.length);
		 if(!(fileSuff=="PNG"||fileSuff=="png")){
			 $.messager.alert('参数检验', "图片必须为PNG/png格式", 'info');
			 return;
		 }
		 
		 $('#viewform').form('submit',{
			    url:'/manage/gameinfo/uploadLogo',
                success:function(data){	
				    var data = $.parseJSON(data);					
					if(data!=null&&data.result!=null){
						$('#gameLogoUri').val(data.result);
						$('#gameLogoImg').attr('src',data.result);
					}
                }
            });
			
		}});
	//点击地图绑定事件
    $('#gameCenterMap').bind('click', function(){
		_stopEvent();		  
		window.mapType="gameCenterMap";
		$('#mapDialog').dialog('open'); 
		setTimeout(function(){loadDbMap();},500); 			  
    });
	$('#gameLogoPutInPointMap').bind('click', function(){
		_stopEvent();		  		
		window.mapType="gameLogoPutInPointMap";
		$('#mapDialog').dialog('open'); 
		setTimeout(function(){loadDbMap();},500); 		   
    });
	
	//初始化游戏日期
	var todayDay = new Date();
	var year = todayDay.getFullYear();
	var month = todayDay.getMonth()+1;
	var date  = todayDay.getDate();
	if(month<10){
		month = '0'+month;
	}
	if(date<10){
		date = '0'+date;
	}
	todayDay = year + '-' + month + '-' + date;
	$('#gameStartDateStr').datebox('setValue', todayDay);
	$('#gameEndDateStr').datebox('setValue', todayDay);
	
	$('#formUpate').bind('click',function(){
		 _stopEvent();
		 sumbitValidate('update'); 
	});
	//保存事件绑定
	$('#formSave').bind('click',function(){
		 _stopEvent();
		 sumbitValidate('add'); 
	});
});

/**
*保存/修改
*/
function sumbitValidate(type){
		 
		 //图片格式的校验
		 var gameLogoUri = $('#gameLogoUri').val();		
		 if(gameLogoUri==""){
			 $.messager.alert('参数检验', "必须有图片", 'info');
			 return;
		 }
		 
		 //日期的校验
		 var gameStartDateStr = $('#gameStartDateStr').datebox('getValue');
		 var gameEndDateStr = $('#gameEndDateStr').datebox('getValue');
		 var dFlag = true;
		 if(gameStartDateStr!=gameEndDateStr){
			var gsd = gameStartDateStr.split("-"),ged = gameEndDateStr.split("-");
		 	var sYear = gsd[0],sMonth = gsd[1] , sD = gsd[2]; 
			var eYear = ged[0],eMonth = ged[1] , eD = ged[2];			
			if(eYear<sYear) {
				dFlag = false;
			}else if(eYear>=sYear){
				if(eMonth<sMonth){
					dFlag = false;
				}else if(eMonth==sMonth){
					if(eD<sD){
						dFlag = false;
					}
				}
			}
		 }
		 if(!dFlag){
			 $.messager.alert('参数检验', "结束日期必须大于开始日期", 'info');
			 return;
		 }
		/** 
		 //车标投放地点的校验 */
		 var maxDistance = $('#gameCenterRadius').numberbox('getValue');
		 if(maxDistance>0){
			 var distance = pointsDistance();		
			 if(parseInt(distance)>parseInt(maxDistance)*1000){
				$.messager.alert('参数检验', "车标投放点已超过游戏中心区了", 'info');
				 return;
			 }
		 }
		
   	    //挖金子次数的校验		
		 var gameType=$('input:radio[name="gameType"]:checked').val();
		 if(gameType=="DIG_GOLD"){
				var times = $('#gameRobGoldTimes').numberbox('getValue');
				if(times==''||times==0){
				$.messager.alert('参数检验', "挖金子模式下挖金子次数必填,且要大于0", 'info');
				 return;
				}
			}	
		  var url= '/manage/gameinfo/saveGameInfo';
		  var title = "保存";
		  
		  if(type=='update'){
			 url= '/manage/gameinfo/updateGameInfo';
			 title = "修改";
		  }
		 $('#viewform').form('submit',{
			    url:url,
                success:function(data){	
				        var data = $.parseJSON(data);	
				       	console.log(data.result);	     
						var text = "";
				        if(data==undefined) text = "系统异常";
						if(parseInt(data.result)==-1){
							text = "跟已有活动冲突,保存失败！";
						}else if(parseInt(data.result)==0){
							text = "系统异常,保存失败！";
						}else if(parseInt(data.result)>=1){
							text =title+"成功！";							
							
						}
						
						$.messager.show({
							title:title+'参数设置提示:',
							msg:text,
							showType:'show'
						});             
                },
				onSubmit:function(){
                    return $(this).form('enableValidation').form('validate');
                }
            });
}


/**
*阻止鼠标默认事件
*/
function _stopEvent(){
	 event.preventDefault();
	 event.stopPropagation();	
}
/**
	  function addMarker1(){		 
		   map.centerAndZoom("天津",15);   		
		var pt = new BMap.Point('116.487165','39.878298');
		var marker = new BMap.Marker(pt);
		map.addOverlay(marker);
		var label = new BMap.Label("北工业",{offset:new BMap.Size(20,-10)});
		marker.setLabel(label); //添加百度label
		map.setCenter(pt);
		//alert(pt.lng + "," + pt.lat);	
		
	  }
*/
/**
  * 百度地图
  */		
var map = new BMap.Map("map");// 创建Map实例	
map.centerAndZoom("北京",13);                 
map.enableScrollWheelZoom(); 
map.addControl(new BMap.NavigationControl());    
map.addControl(new BMap.ScaleControl());    
map.addControl(new BMap.OverviewMapControl());   
var geoc = new BMap.Geocoder(); 
map.addEventListener("click", function(e){ 	        	   
	$('#mapLongtitude,#mapLatitude,#mapAddress').textbox("setText","");  
	var pt = e.point;
	geoc.getLocation(pt, function(rs){				
		var textPref = "";
		if( window.mapType == "gameCenterMap"){
			textPref="游戏中心";
		}else if( window.mapType == "gameLogoPutInPointMap" ){
			textPref="投放点";
		}
		var allOverlay = map.getOverlays();
		for (var i = 0; i < allOverlay.length; i++){				
			try{
				if(allOverlay[i].getLabel().content.indexOf(textPref)!=-1){
					map.removeOverlay(allOverlay[i]);					
				}
			}catch(e){
			}
		}
		var addComp = rs.addressComponents;
		var address = addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
		addMarker(map,e.point.lng, e.point.lat,textPref+":"+address);
		$('#mapLongtitude').textbox("setText",e.point.lng);
		$('#mapLatitude').textbox("setText",e.point.lat);
		$('#mapAddress').textbox("setText",address);	
	});        
});   		
function loadDbMap() {	
	map.clearOverlays();
	var	cLng = $('#gameCenterLongitude').val();
	var	cLat = $('#gameCenterLatitude').val();
	var	cAddress = $('#gameCenterAddress').val();	
	var lLng = $('#gameLogoPutInPointLongitude').val();
	var lLat = $('#gameLogoPutInPointLatitude').val();
	var lAddress = $('#gameLogoPutInPointAddress').val();	
	var radius = $('#gameCenterRadius').numberbox('getValue');	
	if(cLng==""&&lLng==""){
		map.centerAndZoom($('#gameCity').combobox('getValue'),13);  
	}	
	if(cLng!=""){		
		var cPoint = new BMap.Point(cLng,cLat);	
		addMarker(map,cLng,cLat,"游戏中心:"+cAddress);		
		map.setCenter(cPoint);
		var circle = new BMap.Circle(cPoint,parseInt(radius)*1000,{strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5}); 
		map.addOverlay(circle);
	}
	if(lLng!=""){
		addMarker(map,lLng,lLat,"投放点:"+lAddress);		
	}	
}   
function mapReturnValue(){
	if(window.mapType==undefined)
	return;	
	var lng = $('#mapLongtitude').textbox("getText");
	var lat = $('#mapLatitude').textbox("getText");
	var address = $('#mapAddress').textbox("getText");
	if(window.mapType=='gameCenterMap'){
		$('#gameCenter').textbox("setText",lng+","+lat);
		$('#gameCenterLongitude').val(lng);
		$('#gameCenterLatitude').val(lat);
		$('#gameCenterAddress').val(address);		
	}else if(window.mapType=='gameLogoPutInPointMap'){
		$('#gameLogoPutInPoint').textbox("setText",lng+","+lat);
		$('#gameLogoPutInPointLongitude').val(lng);
		$('#gameLogoPutInPointLatitude').val(lat);
		$('#gameLogoPutInPointAddress').val(address);		
	}
}
/**
* 地图上加点
*/
function addMarker(map,lng,lat,address){   
	var pt = new BMap.Point(lng, lat);
	var marker = new BMap.Marker(pt);// 创建标注
	map.addOverlay(marker);//将标注添加到地图中		
	var label = new BMap.Label(address,{offset:new BMap.Size(20,-10)});
	marker.setLabel(label);	
}
/**
*计算两点之间的距离
*/
function pointsDistance(){
	var pointGameCenter = new BMap.Point($('#gameCenterLongitude').val(),$('#gameCenterLatitude').val());
	var pointLogoPutInPoint = new BMap.Point($('#gameLogoPutInPointLongitude').val(),$('#gameLogoPutInPointLatitude').val());
	var distance = map.getDistance(pointGameCenter,pointLogoPutInPoint).toFixed(0);	
	return distance;
}

/**
* 新增城市
*/
function addCity(gameCity){
	$.ajax({
		   type: "POST",
		   url: "/manage/gameinfo/saveGameCity",
		   data: {gameCity:gameCity},
		   dataType:"json",
		   success: function(msg){
			
			 var text = "";
			 if( msg.result == 0 ) {
				 text = "添加失败！";
			 }else if(msg.result == 1){
				 var city = $('#addGameCity').textbox('getValue');
				 var datas = $('#gameCity').combobox('getData');				 
				 $('#gameCity').combobox('loadData',datas.concat({configKey:city,configValue:city}));
				 $('#gameCity').combobox('select',city);
				 text = "添加成功！";
			 }else if(msg.result == 2){
				 text = "添加失败，已存在此城市！";
			 }
			 $.messager.show({
                title:'添加城市结果提示:',
                msg:text,
                showType:'show'
            }); 
		   },
		   error:function(a,b,c){
			 $.messager.show({
                title:'添加城市结果提示:',
                msg:'添加失败，系统异常！',
                showType:'show'
            }); 
		   }
		  });
}

/**
* 选择城市
*/
function selectCity(gameCity){	 	
	 $.ajax({
	   type: "GET",
	   url: "/manage/gameinfo/getGameInfo",	
	   data:{gameCity:gameCity},
	   dataType:"json",
	   success: function(msg){
		    if(msg==null||msg.gameInfo==null){				
				$('#gameCenterLongitude,#gameCenterLatitude,#gameCenterAddress,#gameLogoPutInPointLongitude,#gameLogoPutInPointLatitude,#gameLogoPutInPointAddress').val("");
                $('#gameCenter').textbox('setValue','');
				$('#gameLogoPutInPoint').textbox('setValue','');
				$('#id').val("0");
			}else{
			$('#viewform').form('load',msg.gameInfo);
			$('#gameStartDateStr').datebox('setValue',msg.gameInfo.gameStartDate);
		 	$('#gameEndDateStr').datebox('setValue',msg.gameInfo.gameEndDate);
			var gameStartTime = msg.gameInfo.gameStartTime,gameEndTime = msg.gameInfo.gameEndTime;
			var sTime = new Date(gameStartTime),eTime =  new Date(gameEndTime);
			
			$('#gameStartTimeStr').timespinner('setValue',sTime.getHours()+":"+sTime.getMinutes()+":"+sTime.getSeconds());
		 	$('#gameEndTimeStr').timespinner('setValue',eTime.getHours()+":"+eTime.getMinutes()+":"+eTime.getSeconds());
			
			$('#gameCenter').textbox('setValue',msg.gameInfo.gameCenterLongitude+','+msg.gameInfo.gameCenterLatitude);
			$('#gameLogoPutInPoint').textbox('setValue',msg.gameInfo.gameLogoPutInPointLongitude+','+msg.gameInfo.gameLogoPutInPointLatitude);
			
			$('#gameLogoImg').attr("src",msg.gameInfo.gameLogoUri);
			
			$('#counterText').text(140-$('#gameShareTextTemplate').val().length);
			
			if(msg.gameInfo.gameType=="DIG_GOLD"){
				$('#gameRobGoldTimes').numberbox({disabled:false});
			}
			
			}					
	   },
	   error:function(a,b,c){
		 $.messager.show({
			title:'获取参数设置提示:',
			msg:'获取失败，系统异常！',
			showType:'show'
		}); 
	   }
	  });
}


</script>
</body>
</html>
