<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>实时监控</title>
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
<div class="easyui-layout" style="width:100%;height:600px;">
  <div data-options="region:'east',split:true" style="height:50px;width:300px;" >
    <div class="easyui-layout" style="width:100%;height:100%;border:0;">
      <div data-options="region:'north',split:true,border:false" style="height:40%;width:100%; padding-left:10px;">
        <div style="margin:20px 0;"></div>
        <div style="margin-bottom:20px">查看城市:
          <input id="gameCity" class="easyui-searchbox" data-options="prompt:'Please Input City',searcher:citySearch" style="width:200px;height:24px;">
          </input>
        </div>       
        <div style="margin-bottom:20px">
          <div>在线玩家:
            <input id="onlinePlayerAmount" class="easyui-textbox" style="width:200px;height:24px;"/>
          </div>
        </div>
        <div style="margin-bottom:20px">
          <div>注册玩家:
            <input id="registerPlayerAmount" class="easyui-textbox" style="width:200px;height:24px;"/>
          </div>
        </div>
        <div style="margin-bottom:20px">
          <div>车标持有:
            <input id="holder" class="easyui-textbox" style="width:200px;height:24px;"/>
          </div>
        </div>
      </div>
      <div data-options="region:'center',split:true,border:false" style="height:40%;width:100%; padding-left:10px;">
      	<div style="margin:20px 0;"></div>
              <div style="margin-bottom:20px">玩家编号:
          <input id="playerId" class="easyui-searchbox" data-options="prompt:'Please Input Number',searcher:playerSearch" style="width:200px;height:24px;">
          </input>
        </div>     
        <div style="margin-bottom:20px">
          <div>玩家姓名:
            <input id="playerName" class="easyui-textbox" style="width:200px;height:24px;"/>
          </div>
        </div>
        <div style="margin-bottom:20px">
          <div>玩家昵称:
            <input id="playerNickname" class="easyui-textbox" style="width:200px;height:24px;"/>
          </div>
        </div>
        <div style="margin-bottom:20px">
          <div>玩家手机:
            <input id="playerPhone" class="easyui-textbox" style="width:200px;height:24px;"/>
          </div>
        </div>
      </div>
      <div data-options="region:'south',split:true,border:false" style="height:20%;width:100%;">
       <table id="dg" class="easyui-datagrid" style="width:95%;height:95%" data-options="border:false">
            <thead>
                <tr>
                	<!--,checkbox:true-->
                    <th data-options="field:'ck'">显示</th>
                    <th data-options="field:'itemid',width:50">图标</th>
                    <th data-options="field:'productid',width:80">状态</th>
                    <th data-options="field:'listprice',width:80,align:'right'">统计</th>                 
                </tr>
            </thead>
            <tbody>
                 <tr>
                    <td><input type="checkbox" checked/></td>
                    <td align="center"><img src="" style="width:24px;height:24px;"/></td>
                    <td>车标</td>
                    <td>1</td>                 
                </tr>
                 <tr>
                    <td ><input type="checkbox" checked/></td>
                    <td align="center"><img src="" style="width:24px;height:24px;"/></td>
                    <td>参与玩家</td>
                    <td>0</td>                 
                </tr>                
            </tbody>
        </table>
      </div>
    </div>
  </div>
  <div data-options="region:'center'">
    <div id="monitorMap" style="width:100%;height:100%;"> </div>
  </div>
</div>
<script type="text/javascript">
var gameInfoId = 0;

var logoDisplay = true,playerDisplay = true;

var logoLatLng ={},playerLatLng = {};

$('#dg').datagrid({
	onSelect: function(rowIndex, rowData){
		if(rowIndex==0){
			logoDisplay = false;
			clearLogoMarker();
		}else if(rowIndex==1){
			playerDisplay = false;
			clearPlayerMarker();
		}
	},
	onUnselect: function(rowIndex, rowData){
		if(rowIndex==0){
			logoDisplay = true;
			addLogoMarker(logoLatLng);
		}else if(rowIndex==1){
			playerDisplay = true;
			addPlayerMarker(playerLatLng);
		}
	},	
	onSelectAll: function(rowIndex, rowData){
		logoDisplay = true,playerDisplay=true;
		addLogoMarker(logoLatLng);
		addPlayerMarker(playerLatLng);
	},
	onUnselectAll: function(rowIndex, rowData){
		logoDisplay = false,playerDisplay=false;
		clearLogoMarker();
		clearPlayerMarker();
	}		
});

function clearLogoMarker(){
	var allOverlay = map.getOverlays();	
	for (var i = 0; i < allOverlay.length -1; i++){		
		try{
		if(allOverlay[i].getLabel()!=undefined&&allOverlay[i].getLabel().content.indexOf('CM')!=-1){	
			map.removeOverlay(allOverlay[i]);				
		}
		}catch(e){}
	}
}

function clearPlayerMarker(){
	var allOverlay = map.getOverlays();	
	for (var i = 0; i < allOverlay.length -1; i++){		
		try{
		 if(allOverlay[i].getLabel()!=undefined&&allOverlay[i].getLabel().content.indexOf('车标')!=-1){	
			map.removeOverlay(allOverlay[i]);				
		}
		}catch(e){}
	}
}

function addLogoMarker(logoInfo){
	addMarker(map,logoInfo.logoLongtitude,logoInfo.logoLatitude,"车标车标车标车标车标车标");	
}

function addPlayerMarker(playerInfo){	
	var playerSize = playerInfo.length;	
	for(var i=0;i<playerSize;i++){
		addMarker(map,playerInfo[i].longitude,playerInfo[i].latitude,playerInfo[i].playerId);
	}	
}

/**
  * 百度地图HI
  */		
var map = new BMap.Map("monitorMap");
var point = new BMap.Point(116.404, 39.915);
map.centerAndZoom(point, 15);

map.addControl(new BMap.NavigationControl());    
map.addControl(new BMap.ScaleControl());    
map.addControl(new BMap.OverviewMapControl());   
var geoc = new BMap.Geocoder(); 
map.addEventListener("click", function(e){ 	        	   
	       
});

function citySearch(value,name){	
	if($.trim(value)=="") return;
	clearInterval(timeid);
	$.ajax({
	   type: "GET",
	   url: "/manage/monitor/searchGameCity",	
	   dataType:"json",
	   data:{city:value},
	   success: function(msg){
		    if(msg.result==null){
			    $.messager.alert('搜索城市', "无游戏或城市名错误", 'info');
				map.clearOverlays();
				$('#holder').textbox('setValue',"");
				$('#onlinePlayerAmount').textbox('setValue',"");
				$('#registerPlayerAmount').textbox('setValue',"");
			}else{
				var gi = msg.result.gameInfo;
				gameInfoId = gi.id;
				var pt = new BMap.Point(gi.gameCenterLongitude,gi.gameCenterLatitude);
				map.centerAndZoom(pt, 16);							
				var marker = new BMap.Marker(pt);// 创建标注
				map.addOverlay(marker);//将标注添加到地图中		
				var label = new BMap.Label("游戏中心区",{offset:new BMap.Size(20,-10)});
				marker.setLabel(label);	
				map.setCenter(pt);	
				var circle = new BMap.Circle(pt,parseInt(gi.gameCenterRadius)*1000,{strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5}); 
				map.addOverlay(circle);				
				
				var holder = msg.result.holder;
				var logoLngLat = msg.result.logoLngLat;
				var onlineAmount = msg.result.onlineAmount;
				var playerLngLat = msg.result.playerLngLat;
				var registerAmount = msg.result.registerAmount;
				
				$('#holder').textbox('setValue',holder);
				$('#onlinePlayerAmount').textbox('setValue',onlineAmount);
				$('#registerPlayerAmount').textbox('setValue',registerAmount);
			
				var playerInfo = msg.result.playerLngLat;
				if(playerDisplay){
					addPlayerMarker(playerInfo);				
				}
				var logoInfo = msg.result.logoLngLat;
				if(logoDisplay){
					addLogoMarker(logoInfo);	
				}

				playerLatLng = playerInfo;
				logoLatLng = logoInfo;
				startInterval();
				
			}
	   },
	   error:function(a,b,c){
		 $.messager.alert('搜索城市', "系统异常", 'info');
	   }
	  });	
}

function searchMarker(playerId){
	var allOverlay = map.getOverlays();	
	for (var i = 0; i < allOverlay.length -1; i++){		
		try{
		if(allOverlay[i].getLabel()!=undefined&&allOverlay[i].getLabel().content == playerId){	
			allOverlay[i].setAnimation(BMAP_ANIMATION_BOUNCE);
			break;
		}
		}catch(e){}
	}
}
/**
* 地图上加点
*/
function addMarker(map,lng,lat,label,animation){   
	var pt = new BMap.Point(lng, lat);
	var marker = new BMap.Marker(pt);// 创建标注
	map.addOverlay(marker);//将标注添加到地图中
	if(animation!=undefined){
		marker.setAnimation(BMAP_ANIMATION_BOUNCE);
	}
	var label = new BMap.Label(label,{offset:new BMap.Size(20,-10)});
	marker.setLabel(label);	
	marker.addEventListener('click',function(){
		console.log(arguments);
		}
	);
}

function playerSearch(value,name){
	searchMarker(value);
	if($.trim(value)=="") return;
	$.ajax({
	   type: "GET",
	   url: "/manage/monitor/searchPlayer",	
	   dataType:"json",
	   data:{playerId:value,gameInfoId:gameInfoId},
	   success: function(msg){		 
		   if(msg==null){
			    $.messager.alert('搜索玩家编号', "玩家编号错误", 'info');
			}else{			
				$('#playerName').textbox('setValue',msg.playerName==null?'查无数据':msg.playerName);
				$('#playerNickname').textbox('setValue',msg.playerNickname==null?'查无数据':msg.playerNickname);
				$('#playerPhone').textbox('setValue',msg.playerPhone==null?'查无数据':msg.playerPhone);	
			}
	   },
	   error:function(a,b,c){
		 $.messager.alert('搜索玩家编号', "系统异常", 'info');
	   }
	  });	
}
var timeid=0;
 function startInterval() {
	    clearInterval(timeid);	    
		timeid = setInterval(function(){
				var city = $('#gameCity').textbox('getValue');
				if(city!=''){		
					$.ajax({
					   type: "GET",
					   url: "/manage/monitor/searchGameCity",	
					   dataType:"json",
					   data:{city:city},
					   success: function(msg){
							if(msg.result!=null){		
								var allOverlay = map.getOverlays();	
								for (var i = 0; i < allOverlay.length -1; i++){		
									try{
									if(allOverlay[i].getLabel()!=undefined&&allOverlay[i].getLabel().content.indexOf('CM')!=-1){	
										map.removeOverlay(allOverlay[i]);				
									}else if(allOverlay[i].getLabel()!=undefined&&allOverlay[i].getLabel().content.indexOf('车标')!=-1){	
										map.removeOverlay(allOverlay[i]);				
									}
									}catch(e){}
								}
										
								var holder = msg.result.holder;
								var logoLngLat = msg.result.logoLngLat;
								var onlineAmount = msg.result.onlineAmount;
								var playerLngLat = msg.result.playerLngLat;
								var registerAmount = msg.result.registerAmount;
								
								$('#holder').textbox('setValue',holder);
								$('#onlinePlayerAmount').textbox('setValue',onlineAmount);
								$('#registerPlayerAmount').textbox('setValue',registerAmount);
								
								var playerInfo = msg.result.playerLngLat;
								if(playerDisplay){
									addPlayerMarker(playerInfo);				
								}
								var logoInfo = msg.result.logoLngLat;
								if(logoDisplay){
									addLogoMarker(logoInfo);							
								}
																	
								playerLatLng = playerInfo;
								logoLatLng = logoInfo;		
								var gi = msg.result.gameInfo;
								gameInfoId = gi.id;
							}
					   },
					   error:function(a,b,c){
						 $.messager.alert('搜索城市', "系统异常", 'info');
					   }
					  });		  
					}
			},3000);
}

</script>
</body>
</html>
