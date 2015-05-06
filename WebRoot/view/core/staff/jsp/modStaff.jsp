<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 引入bootstrap.plugins.js插件 上图预览图片-->
<script id="pluginsBootstrap" type="text/javascript" src="${ctx}view/core/staff/js/bootstrap.plugins.js"></script>
<style type="text/css">
.ztree li span.button.company_ico_open{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
.ztree li span.button.company_ico_close{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
.ztree li span.button.company_ico_docu{margin-right:2px; vertical-align:top; *vertical-align:middle; background-image:url("${ctx}js/jquery/EasyUi/jquery-easyui-1.3.4/themes/extjs_icons/server_chart.png")}
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

<!--[if lte IE 8]>

    <script src="${ctx}view/core/staff/js/imgPreview/CJL.0.1.min.js"></script>
    <script src="${ctx}view/core/staff/js/imgPreview/QuickUpload.js"></script>
    <script src="${ctx}view/core/staff/js/imgPreview/ImagePreviewd.js"></script>

    <script type="text/javascript">
	var ip = new ImagePreview( $$("fileinput"), $$("idImg"), {
		maxWidth: 160, maxHeight: 120, action: ""
	});
	ip.img.src = ImagePreview.TRANSPARENT;
	ip.file.onchange = function(){ ip.preview(); };
	
		$(function(){
		
	});
	</script>

<![endif]-->

<div class="row-fluid" style="margin:0px;">

	<form id="u_form" class="form-horizontal form_validation_ttip">
	
        <input type="hidden" id="staffId" name="staffId" value="${form.staffId}"/>
        <input type="hidden" id="orgIds" name="orgIds" value="${form.orgIds}"/>
		
		<fieldset>
			<div class="span12" style="padding-bottom:0px">
				<!-- 流式布局  用户名  用户头像 密码-->
				<div class="row-fluid" style="padding-bottom:0px">
					<!-- 用户头像 -->
					<div class="span4" style="padding-top:25px">
						<div class="control-group " style="margin-left:40px">
							<label for="fileinput" class="control-label"
								style="width:auto;margin-left:20px"><h5>用户头像</h5></label>
							<div class="controls" style="margin-left:0">
								<div data-provides="fileupload"
									class="fileupload fileupload-new">
									<input type="hidden" />
									<div style="width: 160px; height: 120px;margin-left:30px"
										class="fileupload-new thumbnail">
										<img src="http://www.placehold.it/160x120/EFEFEF/AAAAAA"id="idImg" alt="" />
									</div>
									<div id="preImg"
										style="width: 160px; height: 120px; line-height: 120px;margin-left:30px"
										class="fileupload-preview fileupload-exists thumbnail">

									</div>
									<div style="margin-top:10px;margin-left:30px">
										<span class="btn btn-file"> <span
											class="fileupload-new">选择图片</span> <span
											class="fileupload-exists">更换图片</span> <input type="file"
											id="fileinput" name="fileinput" />
										</span> <a data-dismiss="fileupload" class="btn fileupload-exists"
											href="#">移除</a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 账户名 姓名 密码 -->
					<div class="span4" style="padding-left:5px;padding-top:55px">
						<div class="control-group">
							<label class="control-label" style="width:auto">姓名<span
								class="f_req">*</span></label>
							<div class="controls " style="margin-left:0px">
                                <input type="text" name="name" style="margin-left:13px" class="easyui-validatebox input-large" data-options="required:true" value="${form.name}">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" style="width:auto">手机号码<span
								class="f_req">*</span></label>
							<div class="controls " style="margin-left:0px">
                                <input type="text" name="phone" style="margin-left:13px" class="easyui-validatebox easyui-numberbox input-large" data-options="required:true" value="${form.phone}">
							</div>
						</div>
					</div>

					<div class="span4" style="padding-left:5px;padding-top:55px">
					   <div class="control-group">
							<label class="control-label" style="width:auto">性别<span
								class="f_req">*</span></label>
							<div class="controls " style="margin-left:0px">
                                <select name="sex" style="margin-left:13px;">
                                    <option value="MALE">男</option>
                                    <option value="FEMALE">女</option>
                                </select>
							</div>
						</div>
						<div class="control-group ">
							<label for="u_password" class="control-label" style="width:auto">电子邮箱<span class="f_req">*</span></label>
							<div class="controls" style="margin-left:0px">
								<div class="sepH_b" style="margin-left:0px">
                                    <input type="text" name="email" style="margin-left:13px" class="easyui-validatebox input-large" data-options="required:true" value="${form.email}">
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- 流式布局 预留 -->
				<div class="row-fluid" style="padding-bottom:0px">
					<div class="span6"></div>
					<div class="span6"></div>
				</div>
				
				<!-- 流式布局 所属角色-->
				<div class="row-fluid" style="padding-bottom:0px;margin-bottom:0px">
					<div class="span1"></div>
					<div class="span10">
						<div class="control-group ">
						
							<div id="orgSelectInfo"></div>
							<!-- <label for="u_signature" class="control-label"><h5>所属机构</h5></label> -->
							<div class="controls">
								<!-- tab方式 -->
								<div class="tabbable tabs-left">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#1" data-toggle="tab">@所属机构</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active" id="1" style="height:150px">
											<ul id="orgTree" class="ztree"></ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="span1"></div>
				</div>
				
				<!-- 流式布局 保存清空-->
				<div class="row-fluid" style="padding-bottom:0px" id="opBar">
					<div class="span11">
						<div class="control-group">
							<label for="u_signature" class="control-label"></label>
							<div class="controls" style="margin-top:15px">
								<span id="saveBtn" class="btn btn-gebo" style="margin-left:5px;float:right">保存</span>
								<span id="clearBtn" class="btn" style="float:right">清空</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
	</form>
</div>

<script src="${ctx}view/core/staff/js/modStaff.js" />

<!--[if lte IE 8]>
<script type="text/javascript">
    /*先除去非ie支持的bootstrap图片上传插件*/
   $(function(){
   
    $("#pluginsBootstrap").remove();
    
   });
    
    //引入ie支持的图片预览插件
    </script>
<![endif]-->