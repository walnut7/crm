<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Demo Page1</title>
</head>
<body>
	<h2>客户管理</h2>
	<div style="margin: 20px 0 10px 0;"></div>
	<div class="easyui-tabs" style="padding: 10px; width: 700px;">
		<div title="基本信息" style="padding: 10px">
			<div style="margin: 20px 0;"></div>
			<div class="easyui-panel" title="New Topic"	style="width: 100%; max-width: 400px; padding: 30px 60px;">
				<form id="ff" class="easyui-form" method="post" data-options="novalidate:true">
					<div style="margin-bottom: 20px">
						<input class="easyui-textbox" name="name" style="width: 100%" data-options="label:'Name:',required:true">
					</div>
					<div style="margin-bottom: 20px">
						<input class="easyui-textbox" name="email" style="width: 100%" data-options="label:'Email:',required:true,validType:'email'">
					</div>
					<div style="margin-bottom: 20px">
						<input class="easyui-textbox" name="subject" style="width: 100%" data-options="label:'Subject:',required:true">
					</div>
					<div style="margin-bottom: 20px">
						<input class="easyui-textbox" name="message" style="width: 100%; height: 60px"	data-options="label:'Message:',multiline:true">
					</div>
					<div style="margin-bottom: 20px">
						<select class="easyui-combobox" name="language" label="Language" style="width: 100%"><option value="ar">Arabic</option>
							<option value="bg">Bulgarian</option>
							<option value="ca">Catalan</option>
							<option value="zh-cht">Chinese Traditional</option>
							<option value="cs">Czech</option>
							<option value="da">Danish</option>
							<option value="nl">Dutch</option>
							<option value="en" selected="selected">English</option>
							<option value="et">Estonian</option>
							<option value="fi">Finnish</option>
							<option value="fr">French</option>
							<option value="de">German</option>
							<option value="el">Greek</option>
							<option value="ht">Haitian Creole</option>
							<option value="he">Hebrew</option>
							<option value="hi">Hindi</option>
							<option value="mww">Hmong Daw</option>
							<option value="hu">Hungarian</option>
							<option value="id">Indonesian</option>
							<option value="it">Italian</option>
							<option value="ja">Japanese</option>
							<option value="ko">Korean</option>
							<option value="lv">Latvian</option>
							<option value="lt">Lithuanian</option>
							<option value="no">Norwegian</option>
							<option value="fa">Persian</option>
							<option value="pl">Polish</option>
							<option value="pt">Portuguese</option>
							<option value="ro">Romanian</option>
							<option value="ru">Russian</option>
							<option value="sk">Slovak</option>
							<option value="sl">Slovenian</option>
							<option value="es">Spanish</option>
							<option value="sv">Swedish</option>
							<option value="th">Thai</option>
							<option value="tr">Turkish</option>
							<option value="uk">Ukrainian</option>
							<option value="vi">Vietnamese</option>
						</select>
					</div>
				</form>
				<div style="text-align: center; padding: 5px 0">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width: 80px">Submit</a> 
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width: 80px">Clear</a>
				</div>
			</div>
		</div>
		<div title="联系人信息" style="padding: 10px">
			<ul class="easyui-tree" data-options="url:'tree_data1.json',method:'get',animate:true"></ul>
		</div>
		<div title="银行账户" data-options="iconCls:'icon-help',closable:true" style="padding: 10px">This is the help content.</div>
	</div>
	<script>
        function submitForm(){
            $('#ff').form('submit',{
                onSubmit:function() {
                    return $(this).form('enableValidation').form('validate');
                }
            });
        }
        function clearForm(){
            $('#ff').form('clear');
        }
    </script>
</body>
</html>