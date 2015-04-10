<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int num =0;
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
    <meta charset="utf-8" />
	<jsp:include page="/inc.jsp"></jsp:include>
	<script id="pluginsBootstrap" type="text/javascript" src="<%=basePath %>core/staff/js/bootstrap.plugins.js"></script>
	
	<style type="text/css">
	.layout_found {
    background: none repeat scroll 0 0 #fff; border:1px solid #DCEBFE;
    font-size:12px;
    padding:5px;
	}
	.row {
	    background: none repeat scroll 0 0 #FFFFFF;
	    margin: 0 auto;
	    padding: 0 0px; width:100%;
	}
	.row form,.row table{
	    margin-bottom:0px;
	}
	.row h2 {
		 background:url(<%=basePath%>/img/reg_title_bg.jpg) no-repeat #E4EDF6; height:24px;
		  margin:0px; padding:0px; border:none; line-height:24px; padding-left:5px; font-size:13px;
	}
	.row h2 span{color:red;}
	.font2{color:red;}
	.row h3 {font-size:12px; font-weight:normal; padding:10px 0; margin-left:70px;}
	.row h2 .zhucedel,.row h2 .zhucedel{float:right; color:#000; font-size:12px; width:200px; text-align:right; background:none; font-weight:normal; padding-top:0px!important; padding-top:5px;}
	
	.selected{
	   background:#FF6500;  
	}
	</style>
  </head>
  <body >
  <div  class="easyui-layout" fit="true">   
    <div data-options="region:'north',split:false" style="height:35px;">
     <input type="button" class="btn btn-small btn-info pull-right" style="margin:4px 15px" value="保存"/>
    </div>   
    <div data-options="region:'center',split:false" >
       <div class="layout_found">
		<form name=""  method="post">
        <div class="row">
		<h2>人员信息（<span>*</span>为必填项）</h2>
	    <table  style="margin-top:5px" class="table table-condensed table-bordered">
	  <tbody>
		 <tr >
		 	<td >登陆帐号：<span class="font2">*</span></td>
			<td >
				<input type="text" name="emplHis.userName" maxlength="50" value=""  id="userName"  class="input-small">			
			</td>
			<td>
			初始密码：
			</td>
			<td colspan="2">
				<input type="password" name="emplHis.userPassword" maxlength="50" value="" id="userPassword"  class="input-small">
			</td>
		    <td  rowspan="3" style="text-align:center">
		     <div class="controls" >
								<div data-provides="fileupload"
									class="fileupload fileupload-new">
									<input type="hidden" />
									<div style="width: 120px; height: 120px;margin-left:30px"
										class="fileupload-new thumbnail">
										<img src="http://www.placehold.it/120x120/EFEFEF/AAAAAA"id="idImg" alt="" />
									</div>
									<div id="preImg"
										style="width: 160px; height: 120px; line-height: 120px;margin-left:30px"
										class="fileupload-preview fileupload-exists thumbnail">

									</div>
									<div style="margin-top:10px;margin-left:30px">
										<span class="btn btn-file"> <span
											class="fileupload-new">选择图片</span> <span
											class="fileupload-exists">更换图片</span> <input type="file"
											id="fileinput" class="file" name="fileinput" />
										</span> <a data-dismiss="fileupload" class="btn fileupload-exists"
											href="#">移除</a>
									</div>
								</div>
							</div>
		    
		    </td>
		 </tr>
		 <tr>
			<td >姓名：<span class="font2">*</span></td>
	  		<td >
	  			<input type="text" name="emplHis.employeeName" maxlength="50" value="" id="employeeName"  class="input-small">
			</td>
			<td >性别：</td>
			<td colspan="2">
				<select name="emplHis.employeeGender" style="width:104px"><option value="男">男</option>
					<option value="女">女</option></select>
			</td>
		 </tr>
		 <tr>
		  	<td >
				<span >户口所在地：</span>
			</td>
			<td >
				<input type="text" name="emplHis.residenceAddress" maxlength="50" value=""  class="input-small">
			</td>
			<td >户口性质：</td>
			<td colspan="2">
				<input type="text" name="emplHis.residenceProperty" value=""  class="input-small">
			</td>
		 </tr>
		 <tr>
	  		<td >
				<span >现住住址：</span>
			</td>
			<td colspan="5" >
			    <textarea rows="1" style="width:95%"></textarea>
			</td>
	  	</tr>
	  	<tr>
			<td ><span >出生年月：</span></td>
			<td > 
				<input type="text" name="employeeBirthday" data-options="editable:false" style="height:30px;line-height:30px" class="input-small easyui-datebox"   value="" >
			</td>
		    <td  >入职时间：<span class="font2">*</span></td>
			<td  >
				 <input type="text" name="" value=""  id="entryTime" data-options="editable:false" style="height:30px;line-height:30px"  class="input-small easyui-datebox">
			</td>
		  	 <td ><span >民族：</span></td>
			 <td colspan="2">
				<input type="text" name="emplHis.employeeFolk" maxlength="50" value=""  class="input-small">
			</td>
		</tr>
	  	<tr>
			<td ><span >婚姻状况：</span></td>
			<td >
				<select name="emplHis.employeeMarry" style="width:104px"><option value="已婚">已婚</option>
					<option value="未婚">未婚</option></select>
			</td>
			<td >
				<span >政治面貌：</span>
			</td>
			<td >
				<select name="emplHis.employeeZhengzhimianmao" style="width:104px"><option value="群众">群众</option>
					<option value="团员">团员</option>
					<option value="党员">党员</option>
					<option value="民主派">民主派</option></select>
			</td>
		  	<td >
			身份证号码：<span class="font2">*</span>
			</td>
			<td  >
				<input type="text" name="emplHis.employeeIdno" maxlength="50" value=""  class="input-small">
			</td>
	  	</tr>
	  	<tr>
			<td >
				<span >籍贯：</span>
			</td>
			<td >
				<input type="text" name="emplHis.employeeJiguan" maxlength="50" value=""  class="input-small">
			</td>
		  	<td >
				<span >最高学历：</span>
			</td>
			<td >
				<select name="emplHis.employeeWenhuachengdu" style="width:104px"><option value="小学">-请选择-</option>
					<option value="中学">中学</option>
					<option value="中专">中专</option>
					<option value="大专">大专</option>
					<option value="本科">本科</option>
					<option value="硕士">硕士</option>
					<option value="博士">博士</option></select>
			</td>
			 <td >
				<span >所学专业:
			</span></td>
			<td >
				<input type="text" name="emplHis.empSpecialty" value=""  class="input-small">
			</td>
	  	</tr>
	  	<tr>
			<td >
				<span >技术职称：</span>
			</td>
			<td >
				<input type="text" name="emplHis.employeeZhicheng" value=""  class="input-small">
			</td>
	  		<td >毕业院校:</td>
	  		<td  ><input type="text" name="emplHis.graduationSchool" value=""  class="input-small"> </td>
	  		<td >毕业时间:</td>
	  		<td >
	  		<input type="text" name="graduationTime" class="input-small easyui-datebox"  data-options="editable:false" style="height:30px;line-height:30px"  onfocus="new calendar();" value=""> </td>
	  	</tr>
	  	<tr>
		  	<td >档案所在地:</td>
	  		<td colspan="5">
	  			 <textarea rows="1" style="width:95%"></textarea>
	  		</td>
		</tr>
	  	<tr>
	  		<td >干部身份:</td>
	  		<td colspan="2">
	  			<select name="emplHis.cadres" style="width:104px" class="input-small"><option value="无">无</option>
	  				<option value="有">有</option></select>
	  		</td>
	  	<td >健康状况:</td>
	  		<td colspan="2">
	  			<input type="text" name="emplHis.healthState" value=""  class="input-small">
	  		</td>
		</tr>
	  	<tr>
	  		<td >病史或受伤:</td>
	  		<td colspan="5">
	  			<textarea rows="1" style="width:95%"></textarea>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td >影响工作:</td>
	  		<td colspan="2">
	  			<select name="emplHis.affectWork"  class="input-small" style="width:104px"><option value="否">否</option>
	  				<option value="是">是</option></select>
	  		</td>
	  	<td >
			所属部门：<span class="font2">*</span>
			<input type="hidden" name="emplHis.departmentId" value="" id="departmentId">
		</td>
		<td colspan="2">
		    <div class="input-append">
			<input type="text" name="emplHis.departmentName" value="" readonly="readonly" id="departmentName"  class="input-large">	
			<input type="button" class="btn" value="选择" >
		    </div>
		</td>
	  	</tr>
	  	<tr>
		<td >职位：</td>
	  	 <td >
	      <select name="emplHis.jobid" id="jobID" style="width:104px" >
	            <option value="0">-未选择-</option>
			    <option value="119">部门负责人</option>
				<option value="116">审核工程师</option>
				<option value="115">助理工程师</option>
				<option value="117">主审工程师</option>
		  </select>
	   </td>
	  	 <td >
			<span >用工方式</span>
		</td>
		<td >
		<select name="emplHis.empType" style="width:104px"><option value="正式员工">正式员工</option>
		  <option value="试用员工">试用员工</option>
		  <option value="离职员工">离职员工</option></select>	
		</td>
		<td >合同签订时间：</td>
		<td>
			<input type="text" name="contractSignTime" class="input-small"  onfocus="new calendar()" readonly="readonly" value="">
		</td>
	   </tr>
	  	<tr>
		<td >
			合同到期时间:<span class="font2">*</span>
		</td>
		<td >
			<input type="text" name="emplHis.contractTime" value="" onfocus="new calendar()" id="contractTime"  class="input-small">
		</td>
	  	  <td >办公电话：</td>
		<td >
			<input type="text" name="emplHis.employeeOfifcePhone" value=""  class="input-small">
		</td>
	    <td >分机号：</td>
		<td >
			<input type="text" name="emplHis.extensionNum" value=""  class="input-small">
		</td>
	  	</tr>
	  	<tr>
	    <td width="12%" >传真：</td>
		<td width="20%" >
			<input type="text" name="emplHis.fax" maxlength="50" value="" id="fax"  class="input-small">
		</td>
	  	   <td >
				<span >手机</span>
		   </td>
			<td >
				<input type="text" name="emplHis.employeeMobie" value=""  class="input-small">
			</td>
		   <td >
			<span >短号</span>
			</td>
			<td >
				<input type="text" name="emplHis.shortNum" value=""  class="input-small">
			</td>
		</tr>
	  	<tr>
			<td >
				<span >Q Q号</span><span >：</span>
			</td>
			<td >
				<input type="text" name="emplHis.qqNum" value=""  class="input-small">
			</td>
		    <td >
				<span >社保编号:</span>
			</td>
			<td >
				<input type="text" name="emplHis.sheBaoNum" value=""  class="input-small">
			</td>
		    <td >IKEY：</td>
		    <td colspan="2" >
		        <div class="input-append">
		    	<input type="text" name="emplHis.employeeIkey" value="" readonly="readonly" id="" class="input-small">
		    	<span style="cursor:pointer;margin-right:2px;font-size:12px" class="btn " >读取</span>
		        </div>
		    	
		    </td>
	  	</tr>
	  </tbody></table>
        </div>
		</form>
</div>
  <div class="layout_found">
		<form name=""  method="post">
        <div class="row">
		<h2>执业证书
			<a class="pull-right pcDel" style="cursor:pointer;margin-right:2px;font-size:12px">[删除]</a>
			<a class="pull-right pcAdd" style="cursor:pointer;margin-right:2px;font-size:12px">[新增]</a>
		</h2>
	    <table width="80%" style="margin-top:5px" class="table table-condensed table-bordered ">
		  <tbody>
			 <tr >
			    <th style="text-align:center"></th>
			    <th style="text-align:center">序号</th>
			 	<th style="text-align:center">证书编号</th>
				<th style="text-align:center">专业类别</th>
				<th style="text-align:center" >批准日期</th>
				<th style="text-align:center">签发日期</th>
				<th style="text-align:center">签发单位</th>
			 </tr>
			 <tr >
			    <td style="text-align:center"><input type="checkbox" /></td>
			    <td style="text-align:center">1</td>
			 	<td style="text-align:center"><input type="text" style="width:80%" class=" "/></td>
			 	<td style="text-align:center"><select type="text"  class="input-small">
			 	  <option></option>
			 	  <option></option>
			 	  <option></option>
			 	</select></td>
			 	<td style="text-align:center"><input type="text" class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px" /></td>
			 	<td style="text-align:center"><input type="text" class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px"/></td>
			 	<td style="text-align:center"><input type="text" style="width:80%" class=""/></td>
			 </tr>
		  </tbody>
	  </table>
        </div>
		</form>
</div>
  <div class="layout_found">
		<form name=""  method="post">
        <div class="row">
		<h2>注册证书
			
			<a class="pull-right" style="cursor:pointer;margin-right:2px;font-size:12px">[删除]</a><a class="pull-right" style="cursor:pointer;margin-right:2px;font-size:12px">[新增]</a>
		</h2>
	    <table width="80%" style="margin-top:5px" class="table table-condensed table-bordered ">
		  <tbody>
			 <tr>
			    <th style="text-align:center"></th>
			    <th style="text-align:center">序号</th>				
			 	<th style="text-align:center">证书编号</th>
				<th style="text-align:center">初始注册日期</th>
				<th style="text-align:center">发证日期</th>
				<th style="text-align:center">有效期至</th>
				<th style="text-align:center">注册机关</th>
			 </tr>
			 <tr>
			    <td style="text-align:center"><input type="checkbox"/></td>
			    <td style="text-align:center">1</td>
			 	<td style="text-align:center"><input type="text" style="width:80%" class=" "/></td>
			 	<td style="text-align:center"><input type="text" class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px" /></td>
			 	<td style="text-align:center"><input type="text" class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px" /></td>
			 	<td style="text-align:center"><input type="text" class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px"/></td>
			 	<td style="text-align:center"><input type="text" style="width:80%" class=""/></td>
			 </tr>
		  </tbody>
	  </table>
        </div>
		</form>
</div>
  <div class="layout_found">
		<form name=""  method="post">
        <div class="row">
		<h2>驾证
			
			<a class="pull-right" style="cursor:pointer;margin-right:2px;font-size:12px">[删除]</a><a class="pull-right" style="cursor:pointer;margin-right:2px;font-size:12px">[新增]</a>
		</h2>
	    <table width="80%" style="margin-top:5px" class="table table-condensed table-bordered ">
		  <tbody>
			 <tr>
			    <th style="text-align:center"></th>
			    <th style="text-align:center">序号</th>				
			 	<th style="text-align:center">驾驶证号</th>
				<th style="text-align:center">准驾车型</th>
				<th style="text-align:center">初次领证日期</th>					
				<th style="text-align:center">有效起始日期</th>
				<th style="text-align:center">截止日期</th>
				<th style="text-align:center">有效期限（年）</th>
			 </tr>
			 <tr>
			    <td style="text-align:center"><input type="checkbox"/></td>
			    <td style="text-align:center">1</td>
			 	<td style="text-align:center"><input type="text" style="width:80%" class=" "/></td>
			 	<td style="text-align:center"><select type="text"  class="input-small">
			 	  <option>--请选择--</option>
			 	  <option>A1(大型客车)</option>
			 	  <option>A2(牵引车)</option>
			 	  <option>A3(城市公交车)</option>
			 	  <option>B1(中型客车)</option>
			 	  <option>B2(大型货车)</option>
			 	  <option>C1(小型汽车)</option>
			 	  <option>C2(小型自动挡汽车)</option>
			 	  <option>C3(低速载货汽车)</option>
			 	  <option>C4(三轮汽车)</option>
			 	  <option>D(普通三轮摩托车)</option>
			 	  <option>E(普通二轮摩托车)</option>
			 	  <option>F(轻便摩托车)</option>
			 	  <option>M(轮式自行机械车)</option>
			 	  <option>N(有轨电车)</option>
			 	  <option>M(无轨电车)</option>
			 	</select></td>
			 	<td style="text-align:center"><input type="text" class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px" /></td>
			 	<td style="text-align:center"><input type="text" class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px"/></td>
			 	<td style="text-align:center"><input type="text"  class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px"/></td>
			 	<td style="text-align:center"><input type="text" style="width:80%" class="input-mini"/></td>
			 </tr>
			 
		  </tbody>
	  </table>
        </div>
		</form>
</div>
  <div class="layout_found">
		<form name=""  method="post">
        <div class="row">
		<h2>专业技术情况
			
			<a class="pull-right" style="cursor:pointer;margin-right:2px;font-size:12px">[删除]</a><a class="pull-right" style="cursor:pointer;margin-right:2px;font-size:12px">[新增]</a>
		</h2>
	    <table width="80%" style="margin-top:5px" class="table table-condensed table-bordered ">
		  <tbody>
			 <tr>
			    <th style="text-align:center"></th>      
			    <th style="text-align:center">序号</th>				
			 	<th style="text-align:center">职称等级</th>
				<th style="text-align:center">专业</th>
				<th style="text-align:center">职称证书号</th>					
				<th style="text-align:center">职称等级(专业技术职称)</th>
				<th style="text-align:center">发证时间</th>
				<th style="text-align:center">有效期</th>
			 </tr>
			 <tr>
			    <td style="text-align:center"><input type="checkbox"/></td>
			    <td style="text-align:center">1</td>
			 	<td style="text-align:center"><select type="text"  class="input-small">
			 	  <OPTION selected value="">--请选择--</OPTION><OPTION value=1_教授级高级工程师>教授级高级工程师</OPTION><OPTION value=2_高级工程师>高级工程师</OPTION><OPTION value=3_工程师>工程师</OPTION><OPTION value=4_助理工程师>助理工程师</OPTION><OPTION value=5_技术员>技术员</OPTION><OPTION value=2_高级经济师>高级经济师</OPTION><OPTION value=3_经济师>经济师</OPTION><OPTION value=4_助理经济师>助理经济师</OPTION><OPTION value=3_中级会计师>中级会计师</OPTION><OPTION value=4_技师>技师</OPTION><OPTION value=_高级会计师>高级会计师</OPTION><OPTION value=_助理会计师>助理会计师</OPTION>
			 	</select></td>
			 	<td style="text-align:center"><input type="text" style="width:80%" class=" "/></td>
			 	<td style="text-align:center"><input type="text"  class="input-small" /></td>
			 	<td style="text-align:center"><input type="text" class="input-small" /></td>
			 	<td style="text-align:center"><input type="text"  class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px"/></td>
			 	<td style="text-align:center"><input type="text" class="input-mini"/></td>
			 </tr>
		  </tbody>
	  </table>
        </div>
		</form>
</div>
  <div class="layout_found">
		<form name=""  method="post">
        <div class="row">
		<h2>学历
			<a class="pull-right" style="cursor:pointer;margin-right:2px;font-size:12px">[删除]</a><a class="pull-right" style="cursor:pointer;margin-right:2px;font-size:12px">[新增]</a>
		</h2>
	    <table width="80%" style="margin-top:5px" class="table table-condensed table-bordered ">
		  <tbody>
			 <tr>
			    <th style="text-align:center"></th>      
			    <th style="text-align:center">序号</th>     				
			 	<th style="text-align:center">毕业学校</th>
				<th style="text-align:center">专业</th>
				<th style="text-align:center">毕业证号码</th>					
				<th style="text-align:center">发证时间</th>
				<th style="text-align:center">有效期</th>
			 </tr>
			 <tr >
			    <td style="text-align:center"><input type="checkbox"/></td>
			    <td style="text-align:center">1</td>
			 	<td style="text-align:center"><input type="text" style="width:80%"  /></td>
			 	<td style="text-align:center"><input type="text" style="width:80%" class=" "/></td>
			 	<td style="text-align:center"><input type="text"  class="input-small" /></td>
			 	<td style="text-align:center"><input type="text"  class="input-small easyui-datebox" data-options="editable:false"  style="height:30px;line-height:30px"/></td>
			 	<td style="text-align:center"><input type="text" class="input-mini"/></td>
			 </tr>
		  </tbody>
	  </table>
        </div>
		</form>
</div>
    </div>   
</div>
<!-- 引入jQuery -->
<script type="text/javascript" src="<%=basePath %>core/staff/js/addStaffInfo.js"></script>

  </body>
</html>
