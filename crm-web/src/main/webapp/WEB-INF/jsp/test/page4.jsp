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
<title>Demo Page4</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<div id="tb_head" style="height: auto; width: auto;">
		<table>
			<tr>
				<td style="text-align: right">客户姓名：</td>
				<td><input id="custName" name="custName" value=""
					class="easyui-textbox"></td>
				<td style="text-align: right">证件号码：</td>
				<td><input id="certCode" name="certCode" value=""
					class="easyui-textbox"></td>
				<td style="text-align: right">手机号码：</td>
				<td><input id="phone" name="phone" value=""
					class="easyui-textbox"></td>
				<td style="text-align: right">客户类型：</td>
				<td><input class="easyui-combobox" id="custType"
					name="custType" style="width: 100%" panelHeight="auto"
					data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=69&valueField=custType&textField=custType_n',
																  method: 'get',
																  valueField:'custType',
														          textField:'custType_n',
														          value:''" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right">状态：</td>
				<td><input class="easyui-combobox" id="custStatus"
					name="custStatus" style="width: 100%" panelHeight="auto"
					data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=73&valueField=custStatus&textField=custStatus_n',
																  method: 'get',
																  valueField:'custStatus',
														          textField:'custStatus_n',
														          value:''" />
				</td>

				<td style="text-align: right">渠道来源：</td>
				<td><input class="easyui-combobox" id="channel" name="channel"
					style="width: 100%" panelHeight="auto"
					data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=76&valueField=channel&textField=channel_n',
																  method: 'get',
																  valueField:'channel',
														          textField:'channel_n',
														          value:''" />
				</td>
				<td colspan="4" style="text-align: right">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="downloadFile()" iconCls="icon-print" >模板下载</a>&nbsp&nbsp
					<!-- <a href="#" id="pluginurl"  class="easyui-linkbutton" onclick="downloadFile()" data-options="iconCls:'icon-print'" >模板下载</a>&nbsp&nbsp -->
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openUploadWind()" iconCls="icon-add">EXCEL导入</a>&nbsp&nbsp
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openAddWindow()" iconCls="icon-add">新增</a> &nbsp&nbsp 
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doSearch(1,20)" iconCls="icon-search">查询</a></td>
			</tr>
		</table>
	</div>
	<div id="open_cust_detail"></div>
	<div id="upload_win"></div>
<table id="dg_test4" class="easyui-datagrid"
		style="width: auto; height: 570px;"
		data-options="rownumbers:true,
						singleSelect:true,
						url:'${ctx}/crm/customer_list',
						method:'post',
						toolbar:'#tb_head',
						pagination:true,
						pageSize:20">
		<thead>
			<tr>
				<th data-options="field:'custId',width:100,align:'center'">客户ID</th>
				<th data-options="field:'custName',width:100,align:'center'">客户姓名</th>
				<th data-options="field:'certCode',width:160,align:'center'">证件号码</th>
				<th data-options="field:'phone',width:120,align:'center'">手机号码</th>
				<th data-options="field:'createAt',width:160,align:'center'">创建日期</th>
				<!-- <th data-options="field:'custType',width:120,hidden:true" />
				<th data-options="field:'custType_n',width:120,align:'center'">客户类型</th>  -->
				<th data-options="field:'channel',width:120,hidden:true" />
				<th	data-options="field:'channel_n',width:120,align:'center'">渠道来源</th>
				<th data-options="field:'custStatus',width:100,hidden:true" />
				<th data-options="field:'custStatus_n',width:100,align:'center'">状态</th>
				<th data-options="field:'operation',width:120,align:'center',formatter:operation">操作</th>
			</tr>
		</thead>
	</table>
	
	<script type="text/javascript">
		$(function() {
			debugger;
			var cust_list_page = $('#dg_test4').datagrid().datagrid('getPager');
			//var pageNumber = $('#dg_cust_list').datagrid('options').pageNumber;
			cust_list_page.pagination({
				pageSize:20,
				 
				 onChangePageSize:function(pageSize) {
					 debugger;
					 alert('onChangePageSize');  
					 doSearch(1,pageSize);
					//$('#dg_cust_list').datagrid({pageSize: pageSize}).datagrid('reload');
				},
				onSelectPage:function(pageNumber, pageSize){
					debugger;
					//alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
					doSearch(pageNumber,pageSize);
					//$('#dg_cust_list').datagrid({pageNumber: pageNumber, pageSize: pageSize}).datagrid('reload');
				}	 	    
			});
		});

		function doSearch(pageNumber,pageSize) {
			debugger;
			//$("#dg_cust_list").datagrid('getPager').pagination('select', 1);
			//var pageNumber = $('#dg_cust_list').datagrid('options').pageNumber;
			var param = {
					"page":pageNumber,
					"rows":pageSize,
					"custName":$('#custName').textbox('getValue'),
					"certCode":$('#certCode').textbox('getValue'),
					"phone":$('#phone').textbox('getValue'),
					"custType":$('#custType').combobox('getValue'),
					"custStatus":$('#custStatus').combobox('getValue'),
					"channel":$('#channel').combobox('getValue')
			};
								
			$.ajax({
                type : "POST",
                dataType : "json",
                async : false,
                data : param,
                url : "${ctx}/crm/customer_list",
                success : function(data) {
                    //alert(data);
                	$("#dg_test4").datagrid("loadData",data);
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("error:" + textStatus);
                }
			});				
			
		}
		
		function operation(value,rowData,rowIndex) {
			return  '<a href="javascript:void(0)" onclick="openViewWindow(\'' + rowData.custId + '\')">查看</a> | ' +
					'<a href="javascript:void(0)" onclick="openModifyWindow(\'' + rowData.custId + '\')">修改</a> | ' +
					'<a href="javascript:void(0)" onclick="submit(\'' + rowData.custId + '\')">提交</a>';
		}

		function openAddWindow() {
			//debugger;
			//$('#open_cust_detail').window({
			$('#open_demo4_detail', window.parent.document).window({
				title : '客户信息新建',
				closable: false,
				collapsible: false, 
				minimizable: false,
				width : 1350,
				height : 650,
				closed : false,
				cache : false,
				href : '${ctx}/crm/customer_add',
				modal : true
			});

		}

		function openModifyWindow(custId) {
			var custId = parseInt(custId);
			$('#open_cust_detail').window({
				title : '客户信息修改',
				closable: false,
				collapsible: false, 
				minimizable: false,
				width : 1350,
				height : 650,
				closed : false,
				cache : false,
				href : '${ctx}/crm/customer_add?custId='+custId,
				modal : true
			});
		}

		function openViewWindow(custId) {
			debugger;
			parent.window.openWindow(custId, '${ctx}/crm/customer_add?custId='+custId+'&operator=view');
			
			
		}

		function submit(custId){
			var param = {"custId" : parseInt(custId)};
			$.messager.confirm('提示', '确认提交到业务系统?', function(r){
				if (r){
					$.ajax({
		                type : "POST",
		                dataType : "text",
		                async : false,
		                data : param,
		                url : "${ctx}/crm/submit_cust_info",
		                success : function(data) {
		                	if(data == "SUCCESS"){
		 	            		$.messager.alert('SUCCESS','提交成功！','info');
		                    }else{
		                    	$.messager.alert('ERROR' , data,'error');
		                    }
		                },
		                error : function(XMLHttpRequest, textStatus, errorThrown) {
		                	$.messager.alert('ERROR' , textStatus,'error');
		                }
					});
					
				}
			});
			
		}

		function openUploadWind(){
			$('#upload_win').dialog({
			    title: '客户信息EXCEL导入',
			    width: 500,
			    top: 200,
			   // height: 450,
			    closed: false,
			    cache: false,
			    href : '${ctx}/crm/import_customer',
			    modal: true,
			    onBeforeClose:function(){
			    	doSearch();
			    }  
			});
		
		}

		function downloadFile(){
			window.location= "${ctx}/crm/excel_templete_download";
		}
		  
	</script>
</body>
</html>