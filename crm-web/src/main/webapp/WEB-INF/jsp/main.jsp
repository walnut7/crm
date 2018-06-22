<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path+"/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<!-- <div data-options="region:'north',border:false" style="height:50px;padding:10px">
		<span style="font-weight: bold; font-size: 18pt;">客户管理</span>	
	</div> -->
	<div data-options="region:'west',split:true,title:'主菜单'" style="width:300px;">
		<table class="easyui-treegrid" 
				data-options="
					url: '${ctx}/menu/navigation',
					method: 'post',
					idField: 'id',
					treeField: 'name',
					fit:true,
					fitColumns:true,
					onClickRow: addPanel">
			<thead>
				<tr>
					<th data-options="field:'name'" width="220">菜单</th>
				</tr>
			</thead>
		</table>
	</div>
	<div data-options="region:'center'">
		<div id="tabCenterContent" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
			<div title="首页" style="padding: 10px">	</div>
		</div>
	</div>
	<div id="open_detail"></div>
	
	<script type="text/javascript">
		function addPanel(row) {
			if(row.url == undefined || $.trim(row.url) == "") return;
			var title = row.name;
			//var content=$.ajax({url:row.url,async:false,type:"post"}).responseText;		
			var content="<iframe name='ifm' scrolling='no' frameborder='no' border='0' marginwidth='0' marginheight='0' allowtransparency='yes' style='overflow:hidden;border:0;' width='100%'"+" height='100%'"+" src='"+row.url+"'>";	
			if($('#tabCenterContent').tabs('exists', title)){
			        $('#tabCenterContent').tabs('select', title);
			} else {
				$('#tabCenterContent').tabs('add',{
					title: title,
					content: content,
					closable: true
				});
			}
		}

		function openWindow(custId, url) {
			debugger;
			var custId = parseInt(custId);
			var parDiv=$(window.parent.document).find('#open_detail');
			parDiv.window({
				title : '客户信息详情',
				closable: false,
				collapsible: false, 
				minimizable: false,
				width : '100%',
				height : '100%',
				closed : false,
				cache : false,
				href : url,
				modal : true
			});
		}
	</script>
</body>
</html>