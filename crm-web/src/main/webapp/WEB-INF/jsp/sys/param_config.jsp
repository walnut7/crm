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
<title>参数管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>参数管理</h2>
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit',plain:true" onclick="edit()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:true" onclick="save()">保存</a>
		<!-- <a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true" onclick="removeIt()">Cancel</a> -->
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="collapseAll()">折叠</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="expandAll()">展开</a>
	</div>
	<table id="tg_param_conf" class="easyui-treegrid"
		style="width: auto; height: 500px;"
		data-options="
				iconCls: 'icon-ok',
				singleSelect:true,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				url: '${ctx}/sys/param_list',
				method: 'post',
				idField: 'id',
				height: 450,
				treeField: 'nodeCode',
				onBeforeSelect: beforSelect,
				onBeforeEdit: beforeEdit,
				onAfterEdit: afterEdit,
				onContextMenu: onContextMenu,
				autoRowHeight:false,
				pagination:true,
				pageSize:20
			">
		<thead>
			<tr>
				<th data-options="field:'id',width:100,hidden:true,editor:'numberbox'" />
				<th data-options="field:'nodeCode',width:100,editor:'textbox'">属性编码</th>
				<th data-options="field:'nodeValue',width:80,editor:'textbox'">属性值</th>
				<th data-options="field:'parentNodeId',width:80" >上级属性ID</th>
				<th data-options="field:'sort',width:80,hidden:true,editor:'numberbox'">排序</th>
				<th data-options="field:'memo',width:100,editor:'textbox'">备注</th>
				<th data-options="field:'status',width:80,align:'center',formatter:formatStatus,
										editor:{type:'checkbox',
												options:{on:0,off:1}}">状态</th>
			</tr>
		</thead>
	</table>
	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div onclick="openAddDialog()" data-options="iconCls:'icon-add'">新建</div>
		<div onclick="removeIt()" data-options="iconCls:'icon-remove'">删除</div>

	</div>
	<div id="dlg" style="display: none">
		<form id="add_node" class="easyui-form"
			style="width: 400px; height: 300px; padding: 20px">
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" id="parentNodeName"
					name="parentNodeName" disabled="disabled" style="width: 100%"
					data-options="label:'父属性编码：'" />
			</div>
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" id="nodeCode" name="nodeCode" 
					style="width: 100%" data-options="label:'属性编码：',required:true" />
			</div>
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" id="nodeValue" name="nodeValue"
					style="width: 100%" data-options="label:'属性值：', required:true" />
			</div>
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" id="sort" value="0" name="sort"
					style="width: 100%" data-options="label:'排序：'" />
			</div>
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" value="" id="memo" name="memo"
					style="width: 100%" data-options="label:'备注：',multiline:true" />
			</div>
			<div>
				<input type="hidden" class="easyui-textbox"  id="id" name="id" /> 
				<input type="hidden" class="easyui-textbox" id="parentNodeId" name="parentNodeId" /> 
				<input type="hidden" class="easyui-textbox"  id="status" name="status" />
			</div>
		</form>
		<div id="dlg-buttons" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="append()">Save</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				onclick="javascript:$('#dlg').dialog('close')">Close</a>
		</div>
	</div>


	<script type="text/javascript">
		var editIndex = undefined;
		
		var editflg=false;
		function beforSelect(){
			if(editflg){
				return false;
			}
		}

		function beforeEdit(){
			editflg=true;
		}
		
		function afterEdit(row,changes){
			editflg=false;
		}

		function collapseAll(){
			$('#tg_param_conf').treegrid('collapseAll');
		}
		function expandAll(){
			$('#tg_param_conf').treegrid('expandAll');
		}	
	
		function formatStatus(value) {
			if (value == '0') {
				return '<input id="status_c" name="status" disabled="disabled" type="checkbox" checked="checked" />';
			} else {
				return '<input id="status_c" name="status" disabled="disabled" type="checkbox" />';
			}
		}

		function onContextMenu(e, row) {
			e.preventDefault();
			$(this).treegrid('select', row.id);
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}

		
	 	function edit() {
			if (editIndex != undefined) {
				$('#tg_param_conf').treegrid('select', editIndex);
				return;
			}
			var row = $('#tg_param_conf').treegrid('getSelected');
			if (row) {
				editIndex = row.id
				$('#tg_param_conf').treegrid('beginEdit', editIndex);
			}
		} 

		function save() {
			//debugger;
			if (editIndex != undefined) {
				var t = $('#tg_param_conf');
				t.treegrid('endEdit', editIndex);
				var node = t.treegrid('getSelected');
				var param = {
					"id" : parseInt(node.id),
					"nodeCode" : node.nodeCode,
					"nodeValue" : node.nodeValue,
					"sort" : parseInt(node.sort),
					"memo": node.memo,
					"status" : parseInt(node.status)
				};
					
				$.ajax({
					type : "POST",
					dataType : "json",
					async : false,
					data : param,
					url : "${ctx}/sys/update_param",
					success : function(data) {
						editIndex = undefined;
						$('#tg_param_conf').treegrid('update',{
							id: node.id,
							row: {
								nodeCode: data.nodeCode,
								nodeValue: data.nodeValue,
								sort: data.sort,
								memo: data.memo,
								status: data.status
							}
						});
							
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("error:" + textStatus);
						editIndex = undefined;
					}
				});
			}
		}
	

		function openAddDialog() {
			//debugger;
			$('#add_node').form('clear');
			var node = $('#tg_param_conf').treegrid('getSelected');
			$('#dlg').dialog({
				title : '新增节点',
				top : 100,
				modal : true,
				cache : false,
				closed : true,
				onOpen : function() {
					$('#parentNodeId').textbox('setValue', node.id);
					$('#parentNodeName').textbox('setValue', node.nodeCode);
				}
			});
			$('#dlg').dialog("open");
		}

		function append() {
			//debugger;
			var node = $('#tg_param_conf').treegrid('getSelected');
			var tmp_s = $('#sort').textbox('getValue');
			var sort;
			if(!tmp_s){
				sort = 0;
			}else{
				sort =  parseInt(tmp_s);
			}
			
			var param = {
				"nodeCode" : $('#nodeCode').textbox('getValue'),
				"nodeValue" : $('#nodeValue').textbox('getValue'),
				"memo" : $('#memo').textbox('getValue'),
				"parentNodeId": parseInt($('#parentNodeId').textbox('getValue')),
				"status": 0,
				"sort": sort
			};

			$.ajax({
				type : "POST",
				async : false,
				dataType : "text",
				data : param,
				url : "${ctx}/sys/save_param",
				success : function(data) {
					//alert(data);
					$('#id').textbox('setValue',data);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("error:" + textStatus);
				}
			});

		   $('#tg_param_conf').treegrid('append',{
				parent: node.id,
				data: [{
					id: $('#id').textbox('getValue'),
					nodeCode: $('#nodeCode').textbox('getValue'),
					nodeValue: $('#nodeValue').textbox('getValue'),
					sort: $('#sort').textbox('getValue'),
					memo : $('#memo').textbox('getValue'),
					status: 0,
					parentNodeCode: $("#parentNodeId").textbox('getValue')
				}]
			}); 
		    $('#tg_param_conf').treegrid('reload',$('#id').textbox('getValue'));
			$('#dlg').dialog('close');
		}

		function removeIt() {
			var node = $('#tg_param_conf').treegrid('getSelected');
			var node_s = $('#tg_param_conf').treegrid('getChildren', node.id);
			if(node_s.length > 0 ){
				$.messager.alert('Error','请先删除父节点下所有子节点','error');
				return;
			}
			var param = {
					"id": parseInt(node.id)
				};
			$.messager.confirm('Confirm', '是否要删除该节点?', function(r){
				if (r){
					if (node) {
						$.ajax({
							type : "POST",
							async : false,
							data : param,
							url : "${ctx}/sys/delete_param",
							success : function(data) {
								$('#tg_param_conf').treegrid('remove', node.id);
								//$('#tg_param_conf').treegrid('reload');
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
								alert("error:" + textStatus);
							}
						});
					}
				}
			});
		}
		
	</script>

</body>
</html>