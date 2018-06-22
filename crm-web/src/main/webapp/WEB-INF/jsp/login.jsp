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
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/md5.js" /></script>
<style>
#mainview{position: absolute;top:0;left:0;bottom:0;right:0;width:50%;height:50%;margin:auto;}
#viewer {position: relative;padding: 0 60px;top: -70px;font-size: 54px;line-height: 60px;}
</style>
</head>
<body>
	<div style="position:fixed;width:100%;height:100%;top;0;left:0;background-color:#EE8440;">
		<div id="mainview" class="easyui-panel" style="width: 450px; padding: 50px 60px">
			<div style="margin-bottom: 60px; font-size: 28pt; font-weight: bold; letter-spacing: 12pt;">客户管理系统</div>
			<form action="${ctx}/login" method="post">
				<input type="hidden" name="username" value="" />
				<input type="hidden" name="password" value="" />
				<div style="margin-bottom: 20px">
					<input id="user" class="easyui-textbox" prompt="请输入账户名" iconWidth="28" style="width: 100%; height: 34px; padding: 10px;">
				</div>
				<div style="margin-bottom: 20px">
					<input id="pwd" class="easyui-passwordbox" prompt="请输入密码" iconWidth="28" style="width: 100%; height: 34px; padding: 10px">
				</div>
				<c:if test="${!empty loginInfo}"><div style="margin-bottom: 20px;color: red;">${loginInfo}</div></c:if>
				<div data-options="region:'south',border:false" style="text-align: center; margin-top: 10px; padding:10px 0 0;">
					<a class="easyui-linkbutton" href="javascript:void(0)" onclick="javascript:login()" style="width:200px; background:#EE8440; letter-spacing: 12pt;">登录</a>
				</div>
			</form>
		</div>
		<div id="viewer"></div>
	</div>
	<script type="text/javascript">
	    $(function() {
	    	$('#pass').passwordbox({
	            inputEvents: $.extend({}, $.fn.passwordbox.defaults.inputEvents, {
	                keypress: function(e){
	                    var char = String.fromCharCode(e.which);
	                    $('#viewer').html(char).fadeIn(200, function() {
	                        $(this).fadeOut();
	                    });
	                }
	            })
	        });
		});

	    function login(){
			//非空验证
			var user = $.trim($("#user").val());
			var pwd = $.trim($("#pwd").val());

			if (!user || user == '') {
				alert("帐号不能为空");
				return false;
			}

			if (!pwd || pwd == '') {
				alert("密码不能为空");
				return false;
			}
			$("input[name='username']").val(user);
			$("input[name='password']").val(hex_md5(pwd).toLowerCase());
			$("form").submit();
		}
    </script>
</body>
</html>