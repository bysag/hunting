<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>游戏规则</title>
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
  <form id="viewform" class="easyui-form" action="/manage/gameinfo/saveGameRule" method="post" data-options="novalidate:true" enctype="multipart/form-data">
    <input type="hidden" id="id" value="0" name="id"/>
    <table style="width:680px; padding-top:5px;">
      <tr >
        <td style="width:95px;" >城市：</td>
        <td style="width:200px;"><select id="gameCity" name="gameCity" class="easyui-combobox" style="width:180px;float:left" data-options="valueField:'configValue',textField:'configKey',onChange:function(newValue,oldValue){selectCity(newValue);}"/>
          
          </select></td>
      </tr>
      <tr>
        <td >游戏协议：</td>
        <td colspan="2"><textarea id="gameRule" name="gameRule"
						 rows="26" cols="80"></textarea></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="formSave">保存</a></td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript"> 
 
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
		 var gameCity = $('#gameCity').combobox('getValue');
 		 if(gameCity=='全部'){
			$.messager.alert('游戏协议提示','请选择城市！','info');
			return; 
			}	
		 if($('#gameRule').val()==''){
			$.messager.alert('游戏协议提示','请填写协议！','info');
			return; 
			}
		 $('#viewform').form('submit',{
			    url:'/manage/gameinfo/saveGameRule',
                success:function(data){	
				        var data = $.parseJSON(data);					       	     
						var text = "";
				        if(data==undefined) text = "系统异常";
						if(parseInt(data.result)==0){
							text = "系统异常,保存失败！";
						}else if(parseInt(data.result)>=1){
							text ="保存游戏协议成功！";							
							
						}
						
						$.messager.show({
							title:'游戏协议提示:',
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
* 选择城市
*/
function selectCity(gameCity){	 
     if(gameCity=='全部')return;	
	 $.ajax({
	   type: "GET",
	   url: "/manage/gameinfo/getGameRule",	
	   data:{gameCity:gameCity},
	   dataType:"json",
	   success: function(msg){		  
		    if(msg==null||msg.result==null){				
				$('#gameRule').val('');
			}else{
			  $('#gameRule').val(msg.result.configValue);			
			}					
	   },
	   error:function(a,b,c){
		 $.messager.show({
			title:'获取游戏协议提示:',
			msg:'获取失败，系统异常！',
			showType:'show'
		}); 
	   }
	  });
}
</script>
</body>
</html>
