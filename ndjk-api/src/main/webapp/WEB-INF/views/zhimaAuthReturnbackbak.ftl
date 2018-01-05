<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统提示信息</title>
<script type="text/javascript" src="${webroot}/static/js/jquery.js"></script>
<script type="text/javascript" src="${webroot}/static/js/jquery-ui.min.js"></script>
</head>
 <style type="text/css">
   .center{width:140px;height:186px;text-align:center}
   .tips{width:138px;height:138px;text-align:center}
   .msg{color:#4C99E9;font-size:16px;text-align:center;font-weight:bold;font-family:宋体;margin-top:10px}
   </style>
<body>
<script type="text/javascript">
$(function(){
	var error_code = ${code}==200?"SUCCESS":"failed";
    setTimeout('window.location="/h5/result_status.html?'+'params=api/actzm/mine/zhima/AuthCallBack.htm&'+''+'error_code='+''+error_code+'"', 0);
})
</script>
</body>
</html>