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
	<iframe style="display:none;" id="hiddenFrame" name="hiddenFrame" ></iframe>
	<div id="uploadview" class="easyui-panel" style="width: 100%; padding: 30px 70px 50px 70px">
		<form action="${ctx}/crm/excel_import_customer" method="post" enctype="multipart/form-data" target="hiddenFrame">
			<div style="margin-bottom: 20px">
				<input class="easyui-filebox" id="file" name="file" data-options="buttonText:'选择',prompt:'请选择一个excel文件...'" style="width:100%" >
			</div>
			<div>
				<a class="easyui-linkbutton" href="javascript:void(0)" onclick="javascript:uploadFile()" style="width:100%; ">导入</a>
			</div>
			<c:if test="${!empty result}"><div id="view" style="margin-bottom: 20px;color: red;">${result}</div></c:if>
			
		</form>
	</div>

	<script type="text/javascript">
	
		function uploadFile() {
			//debugger;	
			$("form").submit();
			var t = setInterval(function() {
                var view = $(window.frames["hiddenFrame"].document).find("#view").html();
                if (view != "") {
                    //弹窗提示是否上传成功
                    if(view == "SUCCESS"){
        				$.messager.alert('SUCCESS','导入成功！','info');
        			}else{
        				$.messager.alert('ERROR',view,'error');
        			}
                    clearInterval(t);   //清除定时器
                }
            }, 1500);
		}
	</script>
</body>
</html>