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
	<div style="float:left;">
	<form id="base_info" class="easyui-form" >
		<table cellpadding="3" style="width:auto;">
			<tr>
				<td style="text-align: right">客户姓名：</td>
				<td><input class="easyui-textbox" id="custName" name="custName" value="${custName }"
					data-options="required:true"></td>
				<td style="text-align: right">姓名拼音：</td>
				<td><input class="easyui-textbox" id="custNameSpell" name="custNameSpell" value="${custNameSpell }"></td>
				<td style="text-align: right">民族：</td>
				<td><input class="easyui-textbox" id="nation" name="nation" value="${nation }"></td>
				<td style="text-align: right">准驾车型：</td>
				<td><input class="easyui-combobox" id="driveModel" name="driveModel" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=18&valueField=driveModel&textField=driveModel_n',
														  method: 'get',
														  valueField:'driveModel',
												          textField:'driveModel',
												          value:'${driveModel }',
												          editable:false" />
				</td>
			</tr>
			 <tr>
				<td style="text-align: right">证件类型：</td>
				<td><input class="easyui-combobox" id="certType" name="certType" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=13&valueField=certType&textField=certType_n',
														  method: 'get',
														  valueField:'certType',
												          textField:'certType_n',
												          value:'${certType }',
												          editable:false,
												          required:true" />
				</td>
				<td style="text-align: right">证件号码：</td>
				<td><input class="easyui-textbox" id="certCode" name="certCode" value="${certCode }"
					data-options="required:true"></td>
				<td style="text-align: right">是否本地人：</td>
				<td><input class="easyui-combobox" id="relFlag" name="relFlag" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=27&valueField=relFlag&textField=relFlag_n',
														  method: 'get',
														  valueField:'relFlag',
												          textField:'relFlag_n',
												          value:'${relFlag }',
												          editable:false" />
				</td>
				<td style="text-align: right">性别：</td>
				<td><input class="easyui-combobox" id="gender" name="gender" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=30&valueField=gender&textField=gender_n',
														  method: 'get',
														  valueField:'gender',
												          textField:'gender_n',
												          value:'${gender }',
												          editable:false" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right">出生日期：</td>
				<td><input class="easyui-datebox" id="birthday" name="birthday" value="${birthday }" data-options="formatter:formatterDate" style="width: 100%"></td>
				<td style="text-align: right">学历：</td>
				<td><input class="easyui-combobox" id="eduLevel" name="eduLevel" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=34&valueField=eduLevel&textField=eduLevel_n',
														  method: 'get',
														  valueField:'eduLevel',
												          textField:'eduLevel_n',
												          value:'${eduLevel }',
												          editable:false" />
				</td>
				<td style="text-align: right">婚姻状况：</td>
				<td><input class="easyui-combobox" id="marrStatus" name="marrStatus" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=39&valueField=marrStatus&textField=marrStatus_n',
														  method: 'get',
														  valueField:'marrStatus',
												          textField:'marrStatus_n',
												          value:'${marrStatus }',
												          editable:false" />
				</td>
				<td style="text-align: right">从业年限：</td>
				<td><input class="easyui-numberbox" id="workYear" name="workYear" value=${workYear } style="width: 100%"></td>
			</tr>
			 <tr>
				<td style="text-align: right">发证机关：</td>
				<td><input class="easyui-textbox" id="certOrg" name="certOrg" value="${certOrg }" style="width: 100%"></td>
				<td style="text-align: right">定期存款金额：</td>
				<td><input class="easyui-numberbox" data-options="precision:2,groupSeparator:','" id="regularDepositAmt" name="regularDepositAmt" value=${regularDepositAmt } style="width: 100%"></td>
				<td style="text-align: right">年收入：</td>
				<td><input class="easyui-numberbox" data-options="precision:2,groupSeparator:','" id="annualIncome" name="annualIncome" value=${annualIncome } style="width: 100%"></td>
				<td style="text-align: right">收入来源：</td>
				<td><input class="easyui-combobox" id="incomeFrom" name="incomeFrom" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=45&valueField=incomeFrom&textField=incomeFrom_n',
														  method: 'get',
														  valueField:'incomeFrom',
												          textField:'incomeFrom_n',
												          value:'${incomeFrom }',
												          editable:false" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right">收入状态：</td>
				<td><input class="easyui-combobox" id="incomeStatus" name="incomeStatus" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=51&valueField=incomeStatus&textField=incomeStatus_n',
														  method: 'get',
														  valueField:'incomeStatus',
												          textField:'incomeStatus_n',
												          value:'${incomeStatus }',
												          editable:false" />
				</td>
				<td style="text-align: right">入职年限：</td>
				<td><input class="easyui-textbox" id="entryYear" name="entryYear" value="${entryYear }" style="width: 100%"></td>
				<td style="text-align: right">工作单位：</td>
				<td><input class="easyui-textbox" id="workUnit" name="workUnit" value="${workUnit }" style="width: 100%"></td>
				<td style="text-align: right">职务：</td>
				<td><input class="easyui-textbox" id="position" name="position" value="${position }" style="width: 100%"></td>
			</tr>
			<tr>
				<td style="text-align: right">办公地址：</td>
				<td colspan="3"><input class="easyui-textbox"  id="workAddr" name="workAddr" value="${workAddr }"
					style="width: 100%"></td>
				<td style="text-align: right">行业：</td>
				<td colspan="3"><input class="easyui-combotree" id="industry" name="industry" value='${industry }' style="width:100%"
						 data-options="url:'${ctx}/crm/get_industry_combo_tree?parentNodeId=108', method: 'get'" />
				</td>
			
			</tr>
			<tr>
				<td style="text-align: right">手机号码：</td>
				<td><input class="easyui-textbox" id="phone" name="phone" value="${phone }" data-options="required:true" style="width: 100%"></td>
				<td style="text-align: right">工作电话：</td>
				<td><input class="easyui-textbox" id="unitTel" name="unitTel" value="${unitTel }" style="width: 100%"></td>
				
				<td style="text-align: right">单张信用卡最高额度：</td>
				<td><input class="easyui-combobox" id="maxQuota" name="maxQuota" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=57&valueField=maxQuota&textField=maxQuota_n',
														  method: 'get',
														  valueField:'maxQuota',
												          textField:'maxQuota_n',
												          value:'${maxQuota }',
												          editable:false" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right">住房状态：</td>
				<td><input class="easyui-combobox" id="liveStatus" name="liveStatus" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=62&valueField=liveStatus&textField=liveStatus_n',
														  method: 'get',
														  valueField:'liveStatus',
												          textField:'liveStatus_n',
												          value:'${liveStatus }',
												          editable:false" />
				</td>
				<td style="text-align: right">邮箱：</td>
				<td><input class="easyui-textbox" id="email" name="email" value="${email }" style="width: 100%"></td>
				<td style="text-align: right">邮编：</td>
				<td><input class="easyui-textbox" id="zipCode" name="zipCode" value="${zipCode }" style="width: 100%"></td>
				<td style="text-align: right">家庭电话：</td>
				<td><input class="easyui-textbox" id="familyTel" name="familyTel" value="${familyTel }" style="width: 100%"></td>
			</tr>
			<tr>
				<td style="text-align: right">身份证地址：</td>
				<td colspan="3"><input class="easyui-textbox"  id="certAddr" name="certAddr" value="${certAddr }"
					style="width: 100%"></td>
				<td style="text-align: right">联系地址：</td>
				<td colspan="3"><input class="easyui-textbox" id="contactAddr" name="contactAddr" value="${contactAddr }"
					style="width: 100%"></td>
			</tr>
			<tr>
				<td style="text-align: right">配偶姓名:</td>
				<td><input class="easyui-textbox" id="spouseName" name="spouseName" value="${spouseName }" style="width: 100%"></input></td>
				<td style="text-align: right">配偶证件类型:</td>
				<td><input class="easyui-combobox" id="spouseCertType" name="spouseCertType" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=13&valueField=spouseCertType&textField=spouseCertType_n',
														  method: 'get',
														  valueField:'spouseCertType',
												          textField:'spouseCertType_n',
												          value:'${spouseCertType }',
												          editable:false" />
				</td>
				
				<td style="text-align: right">配偶证件号码：</td>
				<td><input class="easyui-textbox" id="spouseCertCode" name="spouseCertCode" value="${spouseCertCode }" style="width: 100%"></td>
				<td style="text-align: right">配偶手机号:</td>
				<td><input class="easyui-textbox" id="spousePhone" name="spousePhone" value="${spousePhone }" style="width: 100%"></input></td>
			</tr>
			<tr>
				<td style="text-align: right">配偶收入来源:</td>
				<td><input class="easyui-combobox" id="spouseIncomeFrom" name="spouseIncomeFrom" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=45&valueField=spouseIncomeFrom&textField=spouseIncomeFrom_n',
														  method: 'get',
														  valueField:'spouseIncomeFrom',
												          textField:'spouseIncomeFrom_n',
												          value:'${spouseIncomeFrom }',
												          editable:false" />
				</td>
				<td style="text-align: right">配偶年收入:</td>
				<td><input class="easyui-numberbox" data-options="precision:2,groupSeparator:','" id="spouseAnnualIncome" name="spouseAnnualIncome" value=${spouseAnnualIncome } style="width: 100%"></input></td>
				<td style="text-align: right">配偶工作单位:</td>
				<td><input class="easyui-textbox" id="spouseWorkUnit" name="spouseWorkUnit" value="${spouseWorkUnit }" style="width: 100%"></input></td>
				<td style="text-align: right">渠道来源：</td>
				<td><input class="easyui-combobox" id="channel" name="channel" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=76&valueField=channel&textField=channel_n',
														  method: 'get',
														  valueField:'channel',
												          textField:'channel_n',
												          value:'${channel }',
												          editable:false" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right">配偶详细地址:</td>
				<td colspan="3"><input class="easyui-textbox" id="spouseContactAddr" name="spouseContactAddr" value="${spouseContactAddr }" style="width: 100%"></input></td>
				<td style="text-align: right">用户类型:</td>
				<td><input class="easyui-combobox" id="custType" name="custType" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=69&valueField=custType&textField=custType_n',
														  method: 'get',
														  valueField:'custType',
												          textField:'custType_n',
												          value:'${custType }',
												          editable:false" />
				</td>
				<td style="text-align: right">用户状态:</td>
				<td><input class="easyui-combobox" id="custStatus" name="custStatus" style="width:100%"
						panelHeight="auto" data-options="url: '${ctx}/crm/get_comb_param?parentNodeId=73&valueField=custStatus&textField=custStatus_n',
														  method: 'get',
														  valueField:'custStatus',
												          textField:'custStatus_n',
												          value:'${custStatus }',
												          editable:false" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right">黑名单备注:</td>
				<td colspan="3" rowspan="2">
					<input class="easyui-textbox" id="custMemo" name="custMemo" value="${custMemo }" data-options="multiline:true" style="width: 100%">
				</td>
				<td style="text-align: right">备注:</td>
				<td colspan="3" rowspan="2">
					<input class="easyui-textbox" id="memo" name="memo" value="${memo }" data-options="multiline:true" style="width: 100%">
				</td>
			</tr>
			
		</table>
		<br/>
	</form>
	</div>
	<div style="text-align: center;float:left; width:50px; margin-left:80px;" >
		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="javascript:void(0)" onclick="javascript:saveBaseInfo()" style="width: 80px">保存</a> 
	</div>
	<script type="text/javascript">
		$(function(){
			$('#industry').combotree({
				onBeforeSelect:function(node){
					//返回树对象 
					var tree = $(this).tree;
					//选中的节点是否为叶子节点,如果不是叶子节点,清除选中 
					var isLeaf = tree('isLeaf', node.target);
                    if(!isLeaf){
                        $("#industry").tree("unselect");
                    }
                }
			});
		});

			
		function formatterDate(date){
			if(date == null || date == ""){
				return "";
			}
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		}
	
		function saveBaseInfo(){
			//debugger;
			var base_info_json = $('#base_info').serializeArray();
			for(var i=0; i<base_info_json.length;i++){
				if(base_info_json[i].name == "custName"){
					if(base_info_json[i].value == ""){
						$.messager.alert('提示','客户姓名不能为空！','info');
						return;
					}
				}
				if(base_info_json[i].name == "certType"){
					if(base_info_json[i].value == ""){
						$.messager.alert('提示','证件类型不能为空！','info');
						return;
					}
				}
				if(base_info_json[i].name == "certCode"){
					if(base_info_json[i].value == ""){
						$.messager.alert('提示','证件号码不能为空！','info');
						return;
					}
				}
				if(base_info_json[i].name == "phone"){
					if(base_info_json[i].value == ""){
						$.messager.alert('提示','手机号码不能为空！','info');
						return;
					}
				}
				
				if(base_info_json[i].name == "birthday"){
					 if(base_info_json[i].value == ""){
						base_info_json[i].value = new Date('1000-01-01');
					}else{
						var birthday =  new Date($('#birthday').datetimebox('getValue'));
						base_info_json[i].value= birthday;
					}  
					
				}
				if(base_info_json[i].name == "workYear"){
					if(base_info_json[i].value == ""){
						base_info_json[i].value = 0;
					}else{
						var workYear = parseInt($('#workYear').numberbox('getValue'));
						base_info_json[i].value= workYear;
					}
				}
				if(base_info_json[i].name == "regularDepositAmt"){
					if(base_info_json[i].value == ""){
						base_info_json[i].value = 0;
					}else{
						var regularDepositAmt = parseFloat($('#regularDepositAmt').numberbox('getValue'));
						base_info_json[i].value= regularDepositAmt;
					}
				}
				if(base_info_json[i].name == "annualIncome"){
					if(base_info_json[i].value == ""){
						base_info_json[i].value = 0;
					}else{
						var annualIncome = parseFloat($('#annualIncome').numberbox('getValue'));
						base_info_json[i].value= annualIncome;
					}
				}
				if(base_info_json[i].name == "spouseAnnualIncome"){
					if(base_info_json[i].value == ""){
						base_info_json[i].value = 0;
					}else{
						var spouseAnnualIncome = parseFloat($('#spouseAnnualIncome').numberbox('getValue'));
						base_info_json[i].value= spouseAnnualIncome;
					}
				}
			}
			
			var custId ;
			if($('#custId').numberbox('getValue') == ""){
				custId = 0;
			}else{
				custId = parseInt($('#custId').numberbox('getValue'));
			}
			var arr = {"name":"custId","value":custId};
			base_info_json.push(arr);
			//debugger;
			 $.ajax({
	            type : "POST",
	            dataType : "json",
	            async : false,
	            data : base_info_json,
	            url : "${ctx}/crm/save_base_info",
	            success : function(data) {	
					if(data.result == "SUCCESS"){
						$("#custId").numberbox('setValue',data.custId);
		            	$.messager.alert('提示',data.message,'info');
					}else {
						$.messager.alert('错误',data.message,'error');
					}
	            	
	            },
	            error : function(XMLHttpRequest, textStatus, errorThrown) {
	                alert("error:" + textStatus);
	            }
			}); 
		}

	</script> 
</body>
</html>