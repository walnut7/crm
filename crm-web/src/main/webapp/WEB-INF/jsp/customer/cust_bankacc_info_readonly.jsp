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
</head>
<body>
	<table id="bank_info"></table>
	<!-- <div id="tb_bank_button" style="height:auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append_b()">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept_b()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit_b()">删除</a>
	</div> -->
	<script type="text/javascript">
		var bank_flag = 0 ;
		function createBankAccDataGrid(){
			var custId ;
			if($('#custId').numberbox('getValue') == ""){
				custId = 0;
			}else{
				custId = parseInt($('#custId').numberbox('getValue'));
			}
			
			$('#bank_info').datagrid({ 
				rownumbers:true,
				singleSelect:true,
				url:'${ctx}/crm/get_bankacc_list',
				queryParams: { 'custId': custId },
				method:'POST',
				//toolbar:'#tb_bank_button',
				idField: 'id',
				fit:true,
				//onClickRow: onClickRow_b,
				//onEndEdit:onEndEdit_b,
				autoRowHeight:false,
				pagination:true,
				pageSize:20,
			    columns:[[      
			    	{field:'id',title:'ID',hidden:true,width:100,editor:{type:'numberbox'}}, 
			        {field:'accName',title:'账户名称',width:80, editor:{type:'textbox'}},      
			        {field:'bankCode',title:'银行编码',width:120,
				        	formatter:function(value, row) { 
								return row.bankCode;
							},
							editor:{type:'combobox',
									options:{url:'${ctx}/crm/get_comb_param?parentNodeId=86&valueField=bankCode&textField=bankCode_n',
											valueField:'bankCode',
											textField:'bankCode_n',
											method:'get'}}},
					{field:'bankFullName',title:'银行全称',width:130, editor:{type:'textbox'}},
					{field:'branchBankName',title:'分行名称',width:130, editor:{type:'textbox'}},
					{field:'accNo',title:'银行账号',width:150, editor:{type:'textbox',options:{required:true}}},
					{field:'bankPhone',title:'开户手机号',width:100, editor:{type:'textbox'}},
					{field:'abbreviationCardNo',title:'银行卡缩略卡号',width:100},
					{field:'isYjzfBind',title:'是否一键支付鉴权',width:100,
						formatter:function(value,row){
							return row.isYjzfBind=='1'?'已绑定':'未绑定';
						}},
					{field:'currency',title:'币种',width:80,
							formatter:function(value, row) { 
								return row.currency;
							},
							editor:{type:'combobox',
									options:{url:'${ctx}/crm/get_comb_param?parentNodeId=103&valueField=currency&textField=currency_n',
											valueField:'currency',
											textField:'currency_n',
											method:'get'}}},
					{field:'isWithholdAcc',title:'是否代扣账户',width:80,	
							editor:{type:'checkbox',
								options:{on:'Y',off:'N'}}},					
					{field:'withholdUnit',title:'代扣机构',width:100,
							formatter:function(value, row) { 
								return row.withholdUnit_n;
							},
							editor:{type:'combobox',
									options:{url:'${ctx}/crm/get_comb_param?parentNodeId=106&valueField=withholdUnit&textField=withholdUnit_n',
											valueField:'withholdUnit',
											textField:'withholdUnit_n',
											method:'get'}}},
					{field:'accStatus',title:'启用',width:80,							
							editor:{type:'checkbox',
								options:{on:'Y',off:'N'}}}
				]]      
			});
			bank_flag = 1;							
		}
	
		var editIndex_b = undefined;
		function endEditing_b() {
			if (editIndex_b == undefined) {
				return true;
			}
			if ($('#bank_info').datagrid('validateRow', editIndex_b)) {
				var ed_bankCode = $('#bank_info').datagrid('getEditor', {index:editIndex_b,field:'bankCode'});
				var bankCode_n = $(ed_bankCode.target).combobox('getText');
				$('#bank_info').datagrid('getRows')[editIndex_b]['bankCode_n'] = bankCode_n;
				
				var ed_currency = $('#bank_info').datagrid('getEditor', {index:editIndex_b,field:'currency'});
				var currency_n = $(ed_currency.target).combobox('getText');
				$('#bank_info').datagrid('getRows')[editIndex_b]['currency_n'] = currency_n;
				
				var ed_withholdUnit = $('#bank_info').datagrid('getEditor', {index:editIndex_b,field:'withholdUnit'});
				var withholdUnit_n = $(ed_withholdUnit.target).combobox('getText');
				$('#bank_info').datagrid('getRows')[editIndex_b]['withholdUnit_n'] = withholdUnit_n;
				
				$('#bank_info').datagrid('endEdit', editIndex_b);
				editIndex_b = undefined;
				return true;
			} else {
				return false;
			}
		}
	
		function onClickRow_b(index) {
			if (editIndex_b != index) {
				if (endEditing_b()) {
					$('#bank_info').datagrid('selectRow', index).datagrid(
							'beginEdit', index);
					editIndex_b = index;
				} else {
					$('#bank_info').datagrid('selectRow', editIndex_b);
				}
			}
		}
	
		function append_b(){
			if($('#custId').numberbox('getValue') == ""){
				$.messager.alert('提示','请先保存客户基本信息！','info');
				return;
			}
			if (endEditing_b()){
				$('#bank_info').datagrid('appendRow',{});
				editIndex_b = $('#bank_info').datagrid('getRows').length-1;
				$('#bank_info').datagrid('selectRow', editIndex_b).datagrid('beginEdit', editIndex_b);
			}
		}

		function onEndEdit_b(rowIndex, rowData) {
			//debugger;
			if(rowData['accNo'] == ""){
				$.messager.alert('提示','银行账号不能为空！','info');
				return;
			}
			
			if(rowData['id'] == ""){
				rowData['id'] = 0;
			}else{
				rowData['id'] = parseInt(rowData['id']);
			}
			
			var custId ;
			if($('#custId').numberbox('getValue') == ""){
				custId = 0;
			}else{
				custId = parseInt($('#custId').numberbox('getValue'));
			}
			rowData["custId"] = custId;
			
			 $.ajax({
                type : "POST",
                dataType : "json",
                async : false,
                data : rowData,
                url : "${ctx}/crm/save_bankacc_info",
                success : function(data) {
                	$('#bank_info').datagrid('getRows')[editIndex_b]['id'] = data.id;
 	            	$.messager.alert('SUCCESS','保存成功！','info');
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                	$.messager.alert('ERROR' , textStatus,'error');
                }
			}); 
		}

		function accept_b(){
			if (endEditing_b()){
				$('#bank_info').datagrid('acceptChanges');
			}
		}
	
		function removeit_b(){
			if (editIndex_b == undefined) {
				return;
			}
			var node = $('#bank_info').datagrid("getSelected");
			if(node.id == "" || typeof(node.id) =="undefined"){
				$('#bank_info').datagrid('cancelEdit', editIndex_b).datagrid('deleteRow',
						editIndex_b);
				editIndex_b = undefined;
				return;
			}
			var param = {
					"id": parseInt(node.id)
				};
			$.messager.confirm('Confirm', '是否要删除该联系人?', function(r){
				if (r){
					if (node) {
						$.ajax({
							type : "POST",
							async : false,
							data : param,
							url : "${ctx}/crm/delete_bankacc_info",
							success : function(data) {
								if(data == "SUCCESS"){
									$('#bank_info').datagrid('cancelEdit', editIndex).datagrid('deleteRow',
											editIndex);
									editIndex = undefined;
									$.messager.alert('SUCCESS','删除成功！','info');
								}else{
									$.messager.alert('ERROR','删除失败！','error');
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
								$.messager.alert('ERROR' , textStatus,'error');
							}
						});
					}
				}
			});
		}

	</script> 
</body>
</html>