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
	<table id="contact_info"></table>
	<!-- <div id="tb_contact_button" style="height: auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	</div> -->
	<script type="text/javascript">
		var contact_flag = 0 ;
		function createContactDataGrid(){
			var custId ;
			if($('#custId').numberbox('getValue') == ""){
				custId = 0;
			}else{
				custId = parseInt($('#custId').numberbox('getValue'));
			}
			
			$('#contact_info').datagrid({      
			    rownumbers:true,
				singleSelect:true,
				url:'${ctx}/crm/get_contactrel_list',
				//toolbar:'#tb_contact_button',
				method: 'POST',
				queryParams: { 'custId': custId },
				idField: 'id',
				fit:true,
				//onClickRow: onClickRow,
				//onEndEdit:onEndEdit,
				autoRowHeight:false,
				pagination:true,
				pageSize:20,   
			    columns:[[      
			        {field:'id',title:'ID',hidden:true,width:100,editor:{type:'numberbox'}},      
			        {field:'contactName',title:'联系人名称',width:90,
				        editor:{type:'textbox',options:{required:true}}},      
			        {field:'isImportantContact',title:'是否紧急联系人',width:100,align:'center',
				        editor:{type:'checkbox',options:{on:'Y',off:'N'}}},
			        {field:'relation',title:'关系',width:100,align:'center',
						formatter:function(value, row) {
							return row.relation_n;
						},
						editor:{type:'combobox',
								options:{url:'${ctx}/crm/get_comb_param?parentNodeId=4&valueField=relation&textField=relation_n',
										method:'get',
										valueField:'relation',
										textField:'relation_n',
										required:true}}},
			        {field:'contactCertType',title:'证件类型',width:80,align:'center',
						formatter:function(value, row) { 
							return row.contactCertType_n;
						},
						editor:{type:'combobox',
								options:{url: '${ctx}/crm/get_comb_param?parentNodeId=13&valueField=contactCertType&textField=contactCertType_n',
										 method: 'get',
										 valueField:'contactCertType',
								         textField:'contactCertType_n'}}},   
			        {field:'contactCertCode',title:'证件号码',width:150,
						editor:{type:'textbox'}},   
			        {field:'contactPhone',title:'联系电话',width:100,
				        editor:{type:'textbox',options:{required:true}}},   
			        {field:'contactFax',title:'传真',width:100,
				        editor:{type:'textbox'}},   
			        {field:'contactEmail',title:'邮箱',width:100,
				        editor:{type:'textbox'}},   
			        {field:'contactAddr',title:'联系地址',width:150,
				        editor:{type:'textbox'}},   
			        {field:'contactWorkUnit',title:'工作单位',width:150,
				        editor:{type:'textbox'}},   
			        {field:'isSendSms',title:'是否发送短信',width:100,align:'center',
				        	editor:{type:'checkbox',options:{on:'Y',off:'N'}}} 
			    ]]      
			});
			contact_flag = 1;
		}
	
		var editIndex = undefined;
		function endEditing() {
			if (editIndex == undefined) {
				return true;
			}
			if ($('#contact_info').datagrid('validateRow', editIndex)) {
				var ed_relation = $('#contact_info').datagrid('getEditor', {index:editIndex,field:'relation'});
				var relation_n = $(ed_relation.target).combobox('getText');
				$('#contact_info').datagrid('getRows')[editIndex]['relation_n'] = relation_n;
				
				var ed_cct = $('#contact_info').datagrid('getEditor', {index:editIndex,field:'contactCertType'});
				var contactCertType_n = $(ed_cct.target).combobox('getText');
				$('#contact_info').datagrid('getRows')[editIndex]['contactCertType_n'] = contactCertType_n;
				
				$('#contact_info').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
	
		function onClickRow(index) {
			if (editIndex != index) {
				if (endEditing()) {
					$('#contact_info').datagrid('selectRow', index).datagrid(
							'beginEdit', index);
					editIndex = index;
				} else {
					$('#contact_info').datagrid('selectRow', editIndex);
				}
			}
		}
	
		function append(){
			if($('#custId').numberbox('getValue') == ""){
				$.messager.alert('提示','请先保存客户基本信息！','info');
				return;
			}
			if (endEditing()){
				$('#contact_info').datagrid('appendRow',{});
				editIndex = $('#contact_info').datagrid('getRows').length-1;
				$('#contact_info').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
			}
		}

		function removeit(){
			if (editIndex == undefined) {
				return;
			}
			var node = $('#contact_info').datagrid("getSelected");
			if(node.id == "" || typeof(node.id) =="undefined"){
				$('#contact_info').datagrid('cancelEdit', editIndex).datagrid('deleteRow',
						editIndex);
				editIndex = undefined;
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
							url : "${ctx}/crm/delete_contact_info",
							success : function(data) {
								if(data == "SUCCESS"){
									$('#contact_info').datagrid('cancelEdit', editIndex).datagrid('deleteRow',
											editIndex);
									editIndex = undefined;
									$.messager.alert('SUCCESS','删除成功！','info');
								}else{
									$.messager.alert('ERROR','删除失败！','error');
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
								alert("error:" + textStatus);
							}
						});
					}
				}
			});
			
		}

		function onEndEdit(rowIndex, rowData) {
			debugger;
			if(rowData['contactName'] == ""){
				$.messager.alert('提示','联系人名称不能为空！','info');
				return;
			}
			if(rowData['relation'] == ""){
				$.messager.alert('提示','联系人关系不能为空！','info');
				return;
			}
			if(rowData['contactPhone'] == ""){
				$.messager.alert('提示','联系电话不能为空！','info');
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
				return;
			}else{
				custId = parseInt($('#custId').numberbox('getValue'));
			}
			rowData["custId"] = custId;
			
			//联系人手机号去空格
			rowData["contactPhone"] = rowData["contactPhone"].replace(/\s+/g,"");
			
			 $.ajax({
                type : "POST",
                dataType : "json",
                async : false,
                data : rowData,
                url : "${ctx}/crm/save_contact_info",
                success : function(data) {
                	$('#contact_info').datagrid('getRows')[editIndex]['id'] = data.id;                     
 	            	$.messager.alert('SUCCESS','保存成功！','info');
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("error:" + textStatus);
                }
			}); 
		}
		
		function accept(){
			if (endEditing()){
				$('#contact_info').datagrid('acceptChanges');
			}
		}

	</script> 
</body>
</html>