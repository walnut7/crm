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
	 <div style="text-align: left;padding: 10px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:closeWindow()" style="width: 80px">关闭</a>
	</div> 
	<div style="display:none;">
		<input type="hidden" class="easyui-numberbox" id="custId" name="custId" value=${custId }/>
	</div>
	<div id="tt" class="easyui-tabs" style="padding: 10px; width: 1300px; height: 550px;">
		<div title="基本信息" style="padding: 10px">
			 <jsp:include page="cust_base_info_readonly.jsp"></jsp:include>
		</div>
		<div title="联系人信息" style="padding: 10px">
			 <jsp:include page="cust_contactrel_info_readonly.jsp"></jsp:include>
		</div>
		<div title="银行账户" style="padding: 10px">
			 <jsp:include page="cust_bankacc_info_readonly.jsp"></jsp:include>
		</div>
		<div title="附件上传" style="padding: 10px">
			 <jsp:include page="cust_attachment_info_readonly.jsp"></jsp:include>			
		</div>
	</div>
	
	<script type="text/javascript">
		$(function(){
			//debugger;
			var tabs = $('#tt').tabs().tabs('tabs');
			var title = "";
			var custId ;
			if($('#custId').val() == ""){
				custId = 0;
			}else{
				custId = parseInt($('#custId').val());
			}
			for(var i=0; i<tabs.length; i++){
				tabs[i].panel('options').tab.unbind().bind('click',{index:i},function(e){
					title = ($(this).find(".tabs-title").html());
					switch (title) {
						case "基本信息": break;
						case "联系人信息": 
							if(contact_flag != 1){
								createContactDataGrid();
							}
							break;
						case "银行账户": 
							if(bank_flag != 1){
								createBankAccDataGrid();
							}
							break;
						case "附件上传": break;
					}
					
				});
			}
			 $("input").attr("readonly",true);
			 			
		});

			
		function closeWindow(){
			$("input").attr("readonly",false);
			//doSearch();
			//$('#open_cust_detail').window('close');
			//self.opener.custListSearch();
			$('#open_detail').window('close');
		}

		
	
	</script> 
</body>
</html>