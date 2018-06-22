<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="tb_reserver_head" style="height: auto; width: auto;">
		<table>
			<tr>
				<td style="text-align: right">客户姓名：</td>
				<td><input id="custName" name="custName" value=""
					class="easyui-textbox"></td>
				<td style="text-align: right">手机号码：</td>
				<td><input id="phone" name="phone" value=""
					class="easyui-textbox"></td>
				<td colspan="4" style="text-align: right">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="reserverSearchButt()" iconCls="icon-search">查询</a>
				</td>
			</tr>
		</table>
	</div>
	<table id="dg_reserver_list" class="easyui-datagrid"		
		data-options="rownumbers:true,
						singleSelect:true,
						url:'${ctx}/crm/get_reserver_list',
						method:'post',
						toolbar:'#tb_reserver_head',
						fit:true,
						pagination:true,
						pageSize:20">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true,editor:'numberbox'" />
				<th data-options="field:'custId',hidden:true,align:'center'">客户ID</th>
				<th data-options="field:'custName',width:80,align:'center'">客户姓名</th>
				<th data-options="field:'gender',width:60,align:'center',
							formatter:function(value,row,index){return row.gender=='MALE'?'男':'女'}">性别</th>
				<th data-options="field:'reserverTime',width:100,align:'center'">预约时间</th>
				<th data-options="field:'certCode',width:160,align:'center'">证件号码</th>
				<th data-options="field:'phone',width:100,align:'center'">手机号码</th>
				<th data-options="field:'productTitle',width:120,align:'center'">车辆名称</th>
				<th data-options="field:'productDesc',width:160,align:'center'">车辆信息</th>
				<th data-options="field:'reserverStore',width:150,align:'center'">预约门店</th>
			</tr>
		</thead>
	</table>

	<script type="text/javascript">

		$(function() {
			//debugger;
			var dg_reserver_list_page = $('#dg_reserver_list').datagrid().datagrid('getPager');
			dg_reserver_list_page.pagination({
				pageSize:20,
				 onChangePageSize:function(pageSize) {
					 reserverSearch(1,pageSize);
				},
				onSelectPage:function(pageNumber, pageSize){
					reserverSearch(pageNumber,pageSize);
				}	 	    
			});
		}); 

		function reserverSearchButt(){
			$("#dg_reserver_list").datagrid('getPager').pagination('select', 1);
		}

		function reserverSearch(pageNumber,pageSize) {
			//debugger;
			var param = {
				"page" : pageNumber,
				"rows" : pageSize,
				"custName" : $('#custName').textbox('getValue'),
				"phone" : $('#phone').textbox('getValue')
			};

			$.ajax({
				type : "POST",
				dataType : "json",
				async : false,
				data : param,
				url : "${ctx}/crm/get_reserver_list",
				success : function(data) {
					$("#dg_reserver_list").datagrid("loadData", data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("error:" + textStatus);
				}
			});

		}
		
	</script>
</body>
</html>