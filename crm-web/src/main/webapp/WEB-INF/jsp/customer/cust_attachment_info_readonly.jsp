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
	<table cellpadding="3" style="width: auto;">
		<tr>
			<td style="text-align: right">身份证正面：</td>
			<td><input type="hidden" name="idCardP_id" id="idCardP_id"
				value="${idCardP_id }" />
				<div id="idCardP_n">
					<a href="javascript:void(0)"
						onclick="downloadAttach('${idCardP_id }')">${idCardP_n }</a>
				</div></td>
			<%-- <td><a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="openUploadWind('idCardP','${idCardP_id }')"
				style="width: 60px">上传附件</a>&nbsp&nbsp 
				<a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="deleteFile('${idCardP_id }')" style="width: 60px">删除附件</a>
			</td> --%>
		</tr>
		<tr>
			<td style="text-align: right">身份证反面：</td>
			<td><input type="hidden" name="idCardB_id" id="idCardB_id"
				value="${idCardB_id }" />
				<div id="idCardB_n">
					<a href="javascript:void(0)" 
						onclick="downloadAttach('${idCardB_id }')">${idCardB_n }</a>
				</div></td>
			<%-- <td><a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="openUploadWind('idCardB','${idCardB_id }')"
				style="width: 60px">上传附件</a>&nbsp&nbsp 
				<a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="deleteFile('${idCardB_id }')" style="width: 60px">删除附件</a>
			</td> --%>
		</tr>
		<tr>
			<td style="text-align: right">银行卡正面：</td>
			<td><input type="hidden" name="bankCardP_id" id="bankCardP_id"
				value="${bankCardP_id }" />
				<div id="bankCardP_n">
					<a href="javascript:void(0)" 
						onclick="downloadAttach('${bankCardP_id }')">${bankCardP_n }</a>
				</div></td>
			<%-- <td><a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="openUploadWind('bankCardP','${bankCardP_id }')"
				style="width: 60px">上传附件</a>&nbsp&nbsp 
				<a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="deleteFile('${bankCardP_id }')" style="width: 60px">删除附件</a>
			</td> --%>
		</tr>
		<tr>
			<td style="text-align: right">银行卡反面：</td>
			<td><input type="hidden" name="bankCardB_id" id="bankCardB_id"
				value="${bankCardB_id }" />
				<div id="bankCardB_n">
					<a href="javascript:void(0)" 
						onclick="downloadAttach('${bankCardB_id }')">${bankCardB_n }</a>
				</div></td>
			<%-- <td><a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="openUploadWind('bankCardB','${bankCardB_id }')"
				style="width: 60px">上传附件</a>&nbsp&nbsp 
				<a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="deleteFile('${bankCardB_id }')" style="width: 60px">删除附件</a>
			</td> --%>
		</tr>
		<tr>
			<td style="text-align: right">驾驶证正本：</td>
			<td><input type="hidden" name="drivLicP_id" id="drivLicP_id"
				value="${drivLicP_id }" />
				<div id="drivLicP_n">
					<a href='javascript:void(0)' 
						onclick="downloadAttach('${drivLicP_id }')">${drivLicP_n }</a>
				</div></td>
			<%-- <td><a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="openUploadWind('drivLicP','${drivLicP_id }')"
				style="width: 60px">上传附件</a>&nbsp&nbsp 
				<a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="deleteFile('${drivLicP_id }')" style="width: 60px">删除附件</a>
			</td> --%>
		</tr>
		<tr>
			<td style="text-align: right">驾驶证副本：</td>
			<td><input type="hidden" name="drivLicB_id" id="drivLicB_id"
				value="${drivLicB_id }" />
				<div id="drivLicB_n">
					<a href="javascript:void(0)" 
						onclick="downloadAttach('${drivLicB_id }')">${drivLicB_n }</a>
				</div></td>
			<%-- <td><a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="openUploadWind('drivLicB','${drivLicB_id }')"
				style="width: 60px">上传附件</a>&nbsp&nbsp 
				<a class="easyui-linkbutton" href="javascript:void(0)"
				onclick="deleteFile('${drivLicB_id }')" style="width: 60px">删除附件</a>
			</td> --%>
		</tr>
	</table>
	<div id="open_upload_wind"></div>
	<script type="text/javascript">
		
		function openUploadWind(attachmentType,id){
			//debugger;
			if($('#custId').numberbox('getValue') == ""){
				$.messager.alert('提示','请先保存客户基本信息！','info');
				return;
			}
			var custId = $('#custId').numberbox('getValue');
			if(id == "" || typeof(id) == "undefined" || id == null){
				id = 0;
			}
			
			 $('#open_upload_wind').dialog({
			    title: '附件上传',
			    width: 500,
			    top: 200,
			    closed: false,
			    cache: false,
			    href : '${ctx}/crm/upload_attach?attachmentType='+attachmentType+'&custId='+custId+'&id='+id,
			    modal: true,
			    onBeforeClose:function(){
			    	doSearchDetail(custId);
			    }  
			}); 
		}

		function downloadAttach(id){
			window.location= "${ctx}/crm/download_cust_attach?id="+id;
		}

		function doSearchDetail(custId){
			debugger;
			var param = {"custId" : custId};
			$.ajax({
                type : "POST",
                dataType : "json",
                async : false,
                data : param,
                url : '${ctx}/crm/search_attach_info',
                success : function(data) {
                	$("#idCardP_id").attr("value",data.idCardP_id || 0 );
                	$('#idCardP_n').html("<a href='javascript:void(0)' onclick='downloadAttach("+ (data.idCardP_id || 0) + ")'>" + (data.idCardP_n || "")+"</a>");
                	
                	$("#idCardB_id").attr("value",data.idCardB_id || 0 );
                	$('#idCardB_n').html("<a href='javascript:void(0)' onclick='downloadAttach(" + (data.idCardB_id || 0) + ")'>" + (data.idCardB_n || "")+"</a>");
                	
                	$("#bankCardP_id").attr("value",data.bankCardP_id || 0 );
                	$('#bankCardP_n').html("<a href='javascript:void(0)' onclick='downloadAttach(" + (data.bankCardP_id || 0) + ")'>" + (data.bankCardP_n || "")+"</a>");
                	
                	$("#bankCardB_id").attr("value",data.bankCardB_id || 0 );
                	$('#bankCardB_n').html("<a href='javascript:void(0)' onclick='downloadAttach(" + (data.bankCardB_id || 0) + ")'>" + (data.bankCardB_n || "")+"</a>");
                	
                	$("#drivLicP_id").attr("value",data.drivLicP_id || 0 );
                	$('#drivLicP_n').html("<a href='javascript:void(0)' onclick='downloadAttach(" + (data.drivLicP_id || 0) + ")'>" + (data.drivLicP_n || "")+"</a>");
                	
                	$("#drivLicB_id").attr("value",data.drivLicB_id || 0 );
                	$('#drivLicB_n').html("<a href='javascript:void(0)' onclick='downloadAttach(" + (data.drivLicB_id || 0) + ")'>" + (data.drivLicB_n || "")+"</a>");
                	
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("error:" + textStatus);
                }
			});				
		}

		function deleteFile(id){
			if(id == "" || id == undefined || id == null){
				return;
			}else{
				id = parseInt(id);
			}
			var custId = parseInt($('#custId').numberbox('getValue'));
			var param = {"id" : parseInt(id)};
			$.messager.confirm('提示', '是否要删除该附件?', function(r){
				if (r){
					$.ajax({
		                type : "POST",
		                dataType : "text",
		                async : false,
		                data : param,
		                url : "${ctx}/crm/delete_cust_attach",
		                success : function(data) {
		                	if(data == "SUCCESS"){
		 	            		$.messager.alert('SUCCESS','删除成功！','info');
		 	            		doSearchDetail(custId);
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
	
	</script>
</body>
</html>