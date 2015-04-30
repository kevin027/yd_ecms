<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
.ztree li span.button.auditOrg_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
.ztree li span.button.auditOrg_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
.ztree li span.button.auditOrg_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
.ztree li span.button.department_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/shape_align_left.png")}
.ztree li span.button.department_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/shape_align_left.png")}
.ztree li span.button.department_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/shape_align_left.png")}
.ztree li span.button.female_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user_female.png")}
.ztree li span.button.female_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user_female.png")}
.ztree li span.button.female_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user_female.png")}
.ztree li span.button.male_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user.png")}
.ztree li span.button.male_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user.png")}
.ztree li span.button.male_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/user/user.png")}
</style>
<form style="padding-left:10px; padding-top:10px">
	<div class="row" style="height:400px;margin-bottom:0px;margin-top:10px">
		<div class="span4 well" style="height:400px;padding:10px; overflow:auto;">
			<ul id="_orgTree" class="ztree"></ul>
		</div>
		<div class="span3 well" style="height:400px;padding:10px">
			<div class="well" id="_orgFullName" style="height:30px;"></div>
			<div class="well" id="_deptList" style="height:280px; overflow:auto;">
			</div>
	 	</div>
	</div>
</form>
<script src="${ctx}view/core/org/js/selectDeptPage.js"></script>