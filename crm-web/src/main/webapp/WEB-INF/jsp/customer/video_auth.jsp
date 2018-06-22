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
	<div id="tb_va_head" style="height: auto; width: auto;">
		<table>
			<tr>
				<td style="text-align: right">客户姓名：</td>
				<td><input id="custName" name="custName" value=""
					class="easyui-textbox"></td>
				<td colspan="4" style="text-align: right">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="videoAuthSearchButt()" iconCls="icon-search">查询</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="accept()" iconCls="icon-save">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openWin()">视频面签</a>
				</td>
			</tr>
		</table>
	</div>
	<table id="dg_video_auth_list" class="easyui-datagrid"
		data-options="rownumbers:true,
						singleSelect:true,
						url:'${ctx}/crm/video_auth_list',
						method:'post',
						toolbar:'#tb_va_head',
						onEndEdit:onEndEdit,
						onDblClickRow:onDblClickRow,
						fit:true,
						pagination:true,
						pageSize:20">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true,editor:'numberbox'" />
				<th data-options="field:'custId',width:80,align:'center'">客户ID</th>
				<th data-options="field:'custName',width:100,align:'center'">客户姓名</th>
				<th data-options="field:'certCode',width:150,align:'center'">证件号码</th>
				<th data-options="field:'phone',width:120,align:'center'">手机号码</th>
				<th data-options="field:'firstDate',width:140,align:'center'">预约时间</th>
				<th data-options="field:'secondDate',width:140,align:'center'">备选时间</th>
				<th data-options="field:'audiFlag',width:100,align:'center',
						formatter:function(value,row){
							return row.audiFlag_n;
						},
						editor:{
							type:'combobox',
							options:{url:'${ctx}/crm/get_comb_param?parentNodeId=246&valueField=audiFlag&textField=audiFlag_n',
									method:'get',
									valueField:'audiFlag',
									textField:'audiFlag_n'
							}
						}">审核状态</th>
				<th
					data-options="field:'remark',width:150,align:'center',editor:'textbox'">备注</th>
			</tr>
		</thead>
	</table>

	<script type="text/javascript">
		 $(function() {
			//debugger;
			var dg_video_auth_list_page = $('#dg_video_auth_list').datagrid().datagrid('getPager');
			dg_video_auth_list_page.pagination({
				pageSize:20,
				onChangePageSize:function(pageSize) {
					 videoAuthSearch(1,pageSize);
				},
				onSelectPage:function(pageNumber, pageSize){
					videoAuthSearch(pageNumber,pageSize);
				}	 	    
			});
		});

		var editIndex = undefined;
		function endEditing(){ 
			if (editIndex == undefined){
				return true;
			}
			if ($('#dg_video_auth_list').datagrid('validateRow', editIndex)){
				var ed = $('#dg_video_auth_list').datagrid('getEditor', {index:editIndex,field:'audiFlag'});
				var audiFlag_n = $(ed.target).combobox('getText');
				$('#dg_video_auth_list').datagrid('getRows')[editIndex]['audiFlag_n'] = audiFlag_n; 
				
				$('#dg_video_auth_list').datagrid('endEdit', editIndex); 
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		
		function onDblClickRow(index) {
			if (editIndex != index) {
				if (endEditing()) {
					$('#dg_video_auth_list').datagrid('selectRow', index).datagrid(
							'beginEdit', index);
					editIndex = index;
				} else {
					$('#dg_video_auth_list').datagrid('selectRow', editIndex);
				}
			}
		}
		
		function onEndEdit(rowIndex, rowData) {
			return $.ajax({
                type : "POST",
                dataType : "json",
                async : false,
                data : rowData,
                url : "${ctx}/crm/update_video_auth",
				success : function(data) {
					if(data.result == "SUCCESS"){
	 	            	$.messager.alert('提示',data.message,'info');
                	}else{
                		$.messager.alert('错误',data.message,'error');
                    }
					
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert("error:" + textStatus);
				}
			});
		}

		function openWin(){
		  	//判断是否IE浏览器
		    if (!!window.ActiveXObject || "ActiveXObject" in window) {
				window.open("https://console.jianmianqian.com");
		    }else{
				alert("请使用IE浏览器打开！");
			}
		}

		function videoAuthSearchButt(){
			$("#dg_video_auth_list").datagrid('getPager').pagination('select', 1);
		}
		
		function videoAuthSearch(pageNumber,pageSize) {
			editIndex = undefined;
			var param = {
				"page" : pageNumber,
				"rows" : pageSize,
				"custName" : $('#custName').textbox('getValue')
			};

			$.ajax({
				type : "POST",
				dataType : "json",
				async : false,
				data : param,
				url : "${ctx}/crm/video_auth_list",
				success : function(data) {
					$("#dg_video_auth_list").datagrid("loadData", data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("error:" + textStatus);
				}
			});

		}
		
		function accept(){
			if (endEditing()){
				$('#dg_video_auth_list').datagrid('acceptChanges');
			}
		}
	</script>
</body>
</html>