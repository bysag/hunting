<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>首页</title>
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
</head>
<body>
<div class="easyui-layout" style="width:100%;height:650px;">
  <div data-options="region:'north'" style="height:68px; padding-left:20px;">
    <h1>OO Hunting Manage</h1>
  </div>
  <div data-options="region:'west',split:true" title="Menu" style="width:300px;height:100%;">
    <div class="easyui-accordion" data-options="border:false" style="width:100%;height:100%;">
      <div title="OO Hunting" data-options="iconCls:'icon-ok'">
        <ul id="huntingTree" class="easyui-tree">
          <li><span>游戏管理</span>
            <ul>
              <li id="param"> 参数设置 </li>
              <li id="rule"> 游戏协议 </li>
              <li id="blacklist"> 黑名单管理 </li>
              <li id="statistics"> 统计管理 </li>
              <li id="monitor"> 实时监控 </li>
            </ul>
          </li>
        </ul>
      </div>
      <div title="Help" data-options="iconCls:'icon-help'" >
        <p>其它</p>
      </div>
    </div>
  </div>
  <div data-options="region:'center'">
    <div id="huntingTab" class="easyui-tabs" data-options="border:false" style="width:100%;height:580px;">
     
    </div>
  </div>
  <iframe frameborder="0" style="height:98%;width:98%"></iframe>
</div>
<script type="text/javascript">
$(function(){
		$('#huntingTree').tree({
			onClick: function(node){
				var type = node.id;
				var url = "";
				if(type == 'param'){
					url = '/huntingmanage/gameInfo.jsp';
				}else if(type == 'rule'){
					url = '/huntingmanage/gameRule.jsp';
				}else if(type == 'blacklist'){
					url = '/huntingmanage/blacklist.jsp';
				}else if(type == 'statistics'){
					url = '/huntingmanage/statistics.jsp';
				}else if(type == 'monitor'){
					url = '/huntingmanage/monitor.jsp';
				}
				
				var allTabs = $('#huntingTab').tabs("tabs");		
			
				var tab = null;
				for(var i=0;i<allTabs.length;i++){
				   if(type==allTabs[i].panel('options').id){
				   	tab =allTabs[i] ; 
					break;
				   }
				}
			
				if(tab!=null){
					$('#huntingTab').tabs('select',$('#huntingTab').tabs('getTabIndex',tab));
					tab.panel('refresh');
				}else{				
					$('#huntingTab').tabs('add',{
						id:type,
						title:node.text,
						content:'  <iframe id="'+type+'Iframe" frameborder="0" style="height:98%;width:100%" src='+url+'></iframe>',					
						closable:true
					});
				}
			}
		});
	});
</script>
</body>
</html>