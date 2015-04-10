var unImplementTipsData = {
	'showTitle' : false,
    'width' : 300,
    'height' : 80,
    'modal' : true,
    'oneTime' : true,
    'openAnimate' : true,
    'html' : '<div style="color:green; height:80px; line-height:80px;">功能已屏蔽</div>'
};

var logoutData = {
	'title' : '确定退出吗？',
	'width' : 300,
	'height' : 80,
	'modal' : true,
	'oneTime' : true,
	'openAnimate' : true,
	'html' : '<div style="color:green; height:80px; line-height:80px;">'
		+ '<a href="javascript:void(0);" onclick="window.top.logout();" style="color:green">确定</a>'
		+ '<a href="javascript:void(0);" onclick="$.CoolWindowClose();" style="margin-left:10px;color:green">取消</a>'
		+ '</div>'
};

// dwr 异常处理
if (undefined != window.dwr && window.dwr) {
	dwr.engine.setErrorHandler(function (errorString, exception) {
		
	});
}

function logout() {
	$.post('user/logout.action');
}

function redirect(url) {
	// alert('连接超时，需要重新登录。');
	window.location.href = url;
}

function showInfo(msg, callback) {
	if (window.layer) {
		layer.alert(msg, 9, "信息", callback);
	} else {
		window.alert(msg);
		if (callback && ("function" == typeof callback)) callback();
	}
}

function showTips(msg, callback) {
	if (window.layer) {
		layer.alert(msg, 0, "提醒", callback);
	} else {
		window.alert(msg);
		if (callback && ("function" == typeof callback)) callback();
	}
}

function showConfirm(msg, yesCallback, title, noCallback) {
	if (window.layer) {
		layer.confirm(msg , yesCallback , title , noCallback);
	} else {
		if (window.confirm(msg)) {
			if (yesCallback && ("function" == typeof yesCallback)) yesCallback();
		} else {
			if (noCallback && ("function" == typeof noCallback)) noCallback();
		}
	}
}