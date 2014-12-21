<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>统计</title>
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
<style>
body {
	text-align: left;
	font-size: 12px;
	font-weight: bold;
}
</style>
</head>
<body>
<div style="margin:20px 0;"></div>
<div class="easyui-layout" style="width:95%;height:170px;border:0;">
  <div data-options="region:'center',split:true" style="height:130px;width:50%;">
    <div id="p" class="easyui-panel" title="搜索" style="width:100%;height:100%; padding-left:10px;" data-options="border:false" >
      <div style="margin:10px;"> 城市：
        <select id="gameCity" name="gameCity" class="easyui-combobox" style="width:220px;float:left" data-options="valueField:'configValue',textField:'configKey'"/>
        
        </select>
        &nbsp;&nbsp;&nbsp;
        <input type="checkbox" id="totalSta" onClick="disabledDate(this.checked)"/>
        总体统计</div>
      <div style="margin:10px;"> 日期：
        <input id="startDate" name="startDate" class="easyui-datebox" style="width:120px" />
        &nbsp;至&nbsp;
        <input id="endDate" name="endDate " class="easyui-datebox" style="width:120px" />
      </div>
      <div style="margin:10px;" > 时间：
        <input id="startTime" class="easyui-timespinner" style="width:120px" data-options="value:'00:01',min:'00:01',showSeconds:true"/>
        &nbsp;至&nbsp;
        <input id="endTime" class="easyui-timespinner" style="width:120px" data-options="value:'00:01',min:'00:01',showSeconds:true"/>
        &nbsp;&nbsp;<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="staSearch">Search</a></div>
    </div>
  </div>
  <div data-options="region:'east'" style="height:100%;width:50%;">
    <div id="p" class="easyui-panel" title="发放奖励" style="width:100%;height:100%; padding-left:10px;" data-options="border:false">
      <div style="margin:10px;"> 发放对象：
        <input id="sortStart" type="text" class="easyui-numberbox" style="width:80px;"/>
        &nbsp;名到&nbsp;
        <input id="sortEnd"type="text" class="easyui-numberbox" style="width:80px;"/>
        &nbsp;名 </div>
      <div style="margin:10px;">
        <input type="radio" name="rewardsType" checked  value="LU_DIAN" onClick="$('#dianJuanNumber').numberbox({disabled:true})">
        绿点：
        <input type="text" id="luDianNumber" class="easyui-numberbox" style="width:80px;"/>
      </div>
      <div style="margin:10px;">
        <input type="radio" name="rewardsType"  value="DIAN_JUAN" onClick="$('#dianJuanNumber').numberbox({disabled:false})">
        点劵：
        <input type="text" id="dianJuanNumber" class="easyui-numberbox" style="width:80px;" disabled/>
        &nbsp;&nbsp;<a id="selectDianJuan" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:90px">选择点劵</a>&nbsp;&nbsp;<a id="rewards" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:60px">发放</a>&nbsp;<a id="rewardsRecord" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:90px">发放记录</a> </div>
    </div>
  </div>
</div>
<div style="margin:20px 0;"></div>
<!--,toolbar:'#tb'-->
<table id="dg" class="easyui-datagrid" style="width:95%;height:310px"
            data-options="method:'get',url:'/manage/statistics/list',toolbar:'#totalTb',rownumbers:true,singleSelect:true,autoRowHeight:false,pagination:true,pageNumber:1,pageSize: 10
                ">
  <thead>
    <tr>
      <th data-options="field:'operator',width:120,align:'center',formatter:formatOperator">操作</th>
      <th data-options="field:'playerId',width:120,align:'center'">用户编码</th>
      <th data-options="field:'playerName',width:120,align:'center'">用户姓名</th>
      <th data-options="field:'playerNickname',width:120,align:'center'">用户昵称</th>
      <th data-options="field:'playerPhone',width:120,align:'center'">用户手机号</th>
      <th data-options="field:'holdLogoAmount',width:80,align:'center'">抢标次数</th>
      <th data-options="field:'integral',width:120,align:'center'">积分</th>
    </tr>
  </thead>
</table>
<div  id="totalTb" style="padding:5px;height:auto"> 上线人数：
  <input id="onlineAmount" class="easyui-textbox" style="width:180px"/>
  &nbsp;&nbsp;注册人数：
  <input id="registerAmount" class="easyui-textbox" style="width:180px"/>
  &nbsp;&nbsp;总注册人数：
  <input id="totalRegisterAmount" class="easyui-textbox" style="width:180px"/>
</div>
<div id="dlg" class="easyui-dialog" title="发放记录" data-options="iconCls:'icon-save',closed:true" style="width:620px;height:480px;">
  <table id="rewardsDg" class="easyui-datagrid" style="width:100%;height:100%"
     data-options="rownumbers:true,singleSelect:true,autoRowHeight:false,pagination:true,pageNumber:1,pageSize: 10,
               toolbar:'#tb',border:false,method:'get',url:'/manage/rewards/list'">
    <!-- pageList: [1, 2, 3, 4, 5],-->
    <thead>
      <tr>
        <th data-options="field:'playerId',width:100,align:'center'">获奖人编号</th>
        <th data-options="field:'playerName',width:100,align:'center'">获奖人姓名</th>
        <th data-options="field:'playerPhone',width:100,align:'center'">获奖人手机</th>
        <th data-options="field:'rewardsAmount',width:60,align:'center'">奖品</th>
        <th data-options="field:'createdTime',width:120,align:'center'">发放日期</th>
        <th data-options="field:'operator',width:60,align:'center'">操作人</th>
      </tr>
    </thead>
  </table>
  <div id="tb" style="padding:15px;height:auto">
    <div style="margin:10px;"> 发放日期：
      <input id="rewardsDate" class="easyui-datebox" style="width:120px">
      &nbsp;操作人：
      <input id="rewardsOperator" class="easyui-textbox" style="width:120px">
    </div>
    <div style="margin:10px;" > 用户昵称：
      <input id="rewardsNickName" class="easyui-textbox" style="width:120px">
      &nbsp;用户手机：
      <input id="rewardsPhone" class="easyui-textbox" style="width:120px">
      &nbsp;&nbsp;<a id="rewardsSearch" href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a></div>
  </div>
</div>
<div id="djdlg" class="easyui-dialog" title="点劵列表" data-options="iconCls:'icon-save',closed:true" style="width:620px;height:480px;">
  <table id="rewardsDg" class="easyui-datagrid" style="width:100%;height:100%"
     data-options="rownumbers:true,singleSelect:true,autoRowHeight:false,pagination:true,pageNumber:1,pageSize: 10,
               toolbar:'#djtb',border:false,method:'get',url:'/manage/rewards/list'">
    <!-- pageList: [1, 2, 3, 4, 5],-->
    <thead>
      <tr>
        <th data-options="field:'playerId',width:100,align:'center'">操作</th>
        <th data-options="field:'playerName',width:100,align:'center'">ID</th>
        <th data-options="field:'playerPhone',width:100,align:'center'">点数</th>
        <th data-options="field:'rewardsAmount',width:60,align:'center'">有效期</th>
        <th data-options="field:'createdTime',width:120,align:'center'">使用条件</th>
      </tr>
    </thead>
  </table>
  <div id="djtb" style="padding:15px;height:auto">
    <div style="margin:10px;"> 点劵ID：
      <input class="easyui-textbox" style="width:120px">
      &nbsp;点数：
      <input class="easyui-textbox" style="width:120px">
    </div>
    <div style="margin:10px;" > 有效期：
      <input class="easyui-textbox" style="width:120px">
      &nbsp;
      <select class="easyui-combobox" style="width:50px;" >
        <option value="日" selected>日</option>
        <option value="周">周</option>
        <option value="年">年</option>
      </select>
      &nbsp;<a id="djSearch" href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a></div>
  </div>
</div>
<script type="text/javascript">
function disabledDate(checked){
	$('#endDate').datebox({disabled:checked});
	$('#startDate').datebox({disabled:checked});
	$('#startTime').timespinner({disabled:checked});
	$('#endTime').timespinner({disabled:checked});
	if(!checked){
		initDate();		
	}
}
function initDate(){
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
	
	$('#startDate').datebox('setValue', todayDay);
	$('#endDate').datebox('setValue', todayDay);
}
$(function(){	 
    initDate();		
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
			title:'获取城市列表:',
			msg:'获取失败，系统异常！',
			showType:'show'
		}); 
	   }
	  });
  
	  //统计搜索
	  var statSeachClick=false;
	  $('#staSearch').bind('click',function(){
		  _stopEvent();		  
		  var gameCity = $('#gameCity').combobox('getValue');
		  if(gameCity=="全部"){
			  $.messager.alert('搜索提示','请选择城市！','info');
		  }
		  var totalStr = $('#totalSta').prop("checked");
		  var params = null;
		  if(!totalStr){
			 var startDate = $('#startDate').datebox('getValue');
			 var endDate = $('#endDate').datebox('getValue');
			 var startTime = $('#startTime').timespinner('getValue');
			 var endTime = $('#endTime').timespinner('getValue');		  	
			 params = {city:gameCity,startDate:startDate+" "+startTime,endDate:endDate+" "+endTime};
		  }else{
			 params = {city:gameCity,startDate:"",endDate:""};
		  }
		  $('#dg').datagrid('load',params);
		  	  //获取上线人数、注册人数、总注册人数
	  	 $.ajax({
				   type: "get",
				   url: "/manage/statistics/total",	
				   dataType:"json",
				   data:params,
				   success: function(msg){
						 if(msg!=null){
							  $('#onlineAmount').textbox('setValue',msg.online+"");
							  $('#registerAmount').textbox('setValue',msg.register+"");
							  $('#totalRegisterAmount').textbox('setValue',msg.totalRegister+"");					 
						 }
				   },
				   error:function(a,b,c){
					 $.messager.show({
						title:'获取统计数量:',
						msg:'失败，系统异常！',
						showType:'show'
					}); 
				   }
				  });	
		});
		//发放奖励查询
		$('#rewardsSearch').bind('click',function(){
			_stopEvent();
		  var createdTime = $('#rewardsDate').datebox('getValue');
		  var rewardsNickName = $('#rewardsNickName').textbox('getValue');
		  var rewardsOperator = $('#rewardsOperator').textbox('getValue');
		  var rewardsPhone	= $('#rewardsPhone').textbox('getValue');
		  var params = {createdTime:createdTime,operator:rewardsOperator,rewarderName:rewardsNickName,rewarderPhone:rewardsPhone};		 
  		 $('#rewardsDg').datagrid('load',params);
		});
		
		//发放记录
		$('#rewardsRecord').bind('click',function(){
			_stopEvent();
			$('#dlg').dialog("open");
		});
	
		//发放
		$('#rewards').bind('click',function(){
			_stopEvent();
			var sortStart = $('#sortStart').numberbox('getValue');
			var sortEnd = $('#sortEnd').numberbox('getValue');
			if(sortStart==""||sortStart==0||sortEnd==""||sortEnd==0){
				$.messager.alert('发放奖励提示','排名必须要有！','info');
				return;	
			}
			var rewardsType = $("input[name='rewardsType']:checked").val();
			var rewardsTypeText = "";
			var amount = 0;
			if(rewardsType=='LU_DIAN'){
				var luDianNumber = $('#luDianNumber').numberbox('getValue');
				if(luDianNumber==""||luDianNumber==0){
					$.messager.alert('发放奖励提示','绿点必须有值！','info');
					return;	
				}
				rewardsTypeText = luDianNumber+'绿点';
				amount = luDianNumber;
			}else if(rewardsType=='DIAN_JUAN'){
				var dianJuanNumber = $('#dianJuanNumber').numberbox('getValue');
				if(luDianNumber==""||luDianNumber==0){
					$.messager.alert('发放奖励提示','点劵必须有值！','info');
					return;	
				}
				rewardsTypeText = dianJuanNumber+'点劵';
				amount = dianJuanNumber;
			}
		  var city = $('#gameCity').combobox('getValue');
		  if(city=='全部'){
				$.messager.alert('发放奖励提示','城市不能是全部！','info');
				return;	  
		  }
						  
			$.messager.confirm('发放奖励', '是否确定为'+sortStart+'到'+sortEnd+'名玩家发放奖励：'+rewardsTypeText+'？', function(r){
				if (r){
						  var gameCity = $('#gameCity').combobox('getValue');
						  if(gameCity=='全部'){
							  
						  }
						  var totalStr = $('#totalSta').prop("checked");
						  var params = null;
						  if(!totalStr){
							 var startDate = $('#startDate').datebox('getValue');
							 var endDate = $('#endDate').datebox('getValue');
							 var startTime = $('#startTime').timespinner('getValue');
							 var endTime = $('#endTime').timespinner('getValue');		  	
							 params = {startTop:sortStart,endTop:sortEnd,rewardType:rewardsType,amount:amount,gameCity:gameCity,startDate:startDate+" "+startTime,endDate:endDate+" "+endTime};
						  }else{
							 params = {startTop:sortStart,endTop:sortEnd,rewardType:rewardsType,amount:amount,gameCity:gameCity,startDate:"",endDate:""};
						  }
		  		
						$.ajax({
							   type: "POST",
							   url: "/manage/rewards/reward",	
							   dataType:"json",
							   data:params,
							   success: function(msg){
								     if(msg>0){
										 $.messager.alert('发放奖励提示','奖励已发放完成！','info');
									 }
							   },
							   error:function(a,b,c){
								 $.messager.show({
									title:'发放奖励提示:',
									msg:'失败，系统异常！',
									showType:'show'
								}); 
							   }
							  });	
				}
			});
		});	
		
		//点劵选择
		$('#selectDianJuan').bind('click',function(){		
			_stopEvent()
			$('#djdlg').dialog("open");
		});		
});		
function formatOperator(val,row){           
   return '<a href="javascript:void(0)" onclick="addBlacklist(\''+row.playerId+'\')">加入黑名单</a>';   
}

function addBlacklist(playerId){
$.messager.confirm('加黑名单', '是否确定将该用户('+playerId+')加入黑名单？', function(r){
				if (r){
					$.ajax({
							   type: "POST",
							   url: "/manage/blacklist/add",	
							   dataType:"json",
							   data:{city:$('#gameCity').combobox('getValue'),playerId:playerId},
							   success: function(msg){
								  if(msg==1){
								  	$('#dg').datagrid('reload');
								  }	;	  
							   },
							   error:function(a,b,c){
								 $.messager.show({
									title:'添加黑名单:',
									msg:'失败，系统异常！',
									showType:'show'
								}); 
							   }
							  });	
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
</script>
</body>
</html>
