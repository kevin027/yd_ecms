/**
 * @author 孙宇
 */

var sy = $.extend({}, sy);/* 全局对象 */

sy.changeTheme = function(themeName) {/* 更换主题 */
	var $easyuiTheme = $('#easyuiTheme');	
	var url = $easyuiTheme.attr('href');	
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);


	/*以tab方式打开中间页时使用，防止样式不一致情况*/
	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#easyuiTheme').attr('href', href);			
		}
	}
	
	

	/*保存7天*/
	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});
	
	//更改选中的iconCls
	$("#changeTheme > div").attr("iconCls","icon-io").bind('click',function(){
		$("#changeTheme > div  .menu-icon").removeClass("icon-ok");
		$(".menu-icon",this).removeClass("icon-ok1").addClass("icon-ok");
		});
};

if ($.cookie('easyuiThemeName')) {

	sy.changeTheme($.cookie('easyuiThemeName'));
}
