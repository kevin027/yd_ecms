<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page isErrorPage="true" %>

<!DOCTYPE HTML> 
<html> 
<head> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>错误信息页面</title>
    <link rel="stylesheet" type="text/css" href="${ctx}errorCss/error.css">
</head> 
<body>
<div id="wrapper"><a class="logo" href="/"></a>
    <div id="main">
        <header id="header">
            <h1><span class="icon">!</span>Error<span class="sub">page not found</span></h1>
        </header>
        <div id="content">
            <h2>您打开的这个的页面不存在！</h2>
            <p>当您看到这个页面,表示您的访问出错,这个错误是您打开的页面不存在,请确认您输入的地址是正确的,如果是在本站点击后出现这个页面,请联系管理员及相关人员进行处理,感谢您的支持!</p>
            <div class="utilities">
                <form  name="formsearch" action="/plus/search.php" id="formkeyword">
                    <div class="input-container">
                        <input type="text" class="left" name="q" size="24"  value="在这里搜索..." onfocus="if(this.value=='在这里搜索...'){this.value='';}"  onblur="if(this.value==''){this.value='在这里搜索...';}" id="inputString" onkeyup="lookup(this.value);" onblur="fill();" placeholder="搜索..." />
                        <button id="search"></button>
                    </div>
                </form>
                <a class="button right" href="#" onClick="history.go(-1);return true;">返回...</a>
                <a class="button right" href="#">联系管理员</a>
                <div class="clear"></div>
            </div>
        </div>
        <div id="footer">
            <ul>
                <li>QQ：asinait@163.com</li>
            </ul>
        </div>
    </div>
</div>
</body> 
</html> 