<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>黑名单</title>
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
<table id="dg" class="easyui-datagrid" title="黑名单列表" style="width:100%;height:250px"
            data-options="rownumbers:true,singleSelect:true,autoRowHeight:false,pagination:true,pageNumber:1,pageSize: 10,
                toolbar:'#tb',method:'get',url:'/manage/blacklist/list'">
  <thead>
    <tr>
      <th data-options="field:'operator',width:80,align:'center',formatter:formatOperator">操作</th>
      <th data-options="field:'playerId',width:120,align:'center'">用户编码</th>
      <th data-options="field:'playerName',width:120,align:'center'">用户姓名</th>
      <th data-options="field:'playerNickname',width:120,align:'center'">用户昵称</th>
      <th data-options="field:'playerPhone',width:120,align:'center'">用户手机号</th>
      <th data-options="field:'holdLogoAmount',width:120,align:'center'">抢标次数</th>
      <!--th data-options="field:'holdLogoTime',width:120,align:'center'">持有时间</th-->
      <th data-options="field:'integral',width:120,align:'center'">积分</th>      
    </tr>
  </thead>
</table>
<div id="tb" style="padding:15px;height:auto">
   <div style="margin:10px;"> 用户编号：<input id="playerId" class="easyui-textbox" style="width:100px">
   用户姓名：<input id="playerName" class="easyui-textbox" style="width:100px">
   用户昵称：<input id="playerNickname" class="easyui-textbox" style="width:100px">&nbsp;&nbsp;<a id="search" href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a></div>
</div>
<script type="text/javascript">
$(function(){
	
	$('#search').bind('click',function(){
		 _stopEvent();
		 $('#dg').datagrid('load',{gameCity:'',playerId:$('#playerId').textbox('getValue'),playerName:$('#playerName').textbox('getValue'),playerNickname:$('#playerNickname').textbox('getValue')});  		 
		}); 
})
function formatOperator(val,row){           
   return '<a href="javascript:void(0)" onclick="deleteBlacklist(\''+row.playerId+'\')">删除黑名单</a>';   
}
function deleteBlacklist(playerId){
	$.messager.confirm('删黑名单', '是否确定将该用户('+playerId+')删除黑名单？', function(r){
				if (r){
					$.ajax({
							   type: "GET",
							   url: "/manage/blacklist/delete",	
							   dataType:"json",
							   data:{playerId:playerId},
							   success: function(msg){
								  if(msg==1){
								  	$('#dg').datagrid('reload');
								  }								  	  
							   },
							   error:function(a,b,c){
								 $.messager.show({
									title:'删除黑名单:',
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
