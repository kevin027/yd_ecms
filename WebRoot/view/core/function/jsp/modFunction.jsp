<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form action="${ctx}fun/updateFunction" method="post" style="padding:50px;padding-bottom:0px;" id="funModForm">
    <input type="hidden" name="parent" value="${function.parent.id}"/>
    <input type="hidden" name="id" value="${function.id}"/>
	<table class="table">
		<tr>
			<td>功能名称：</td>
			<td>
                <input type="text" id="name" name="name" value="${function.name}" style="width: 90%;"/>
            </td>
		</tr>
		
		<tr>
			<td>功能别名：</td>
			<td>
                <input type="text" id="code" name="code" value="${function.code}" style="width: 90%;" title="系统编码识别用"/>
            </td>
		</tr>
		
		<tr>
			<td>功能类型：</td>
			<td style="text-align:left">
                <select id="type" name="type">
                    <c:forEach items="${functionTypeList}" var="ft" varStatus="st">
                        <option value="${ft}" <c:if test="${function.type eq ft }">selected="selected"</c:if>>${ft.chinese}</option>
                    </c:forEach>
                </select>
			</td>
		</tr>

        <tr>
            <td style="color:red">是否隐藏：</td>
            <td style="text-align:left">
                <input name="isHide" type="radio" value="true" <c:if test="${true == function.isHide}" >checked="checked"</c:if> /><span>否</span>
                <input name="isHide" type="radio" value="false" <c:if test="${false == function.isHide}" >checked="checked"</c:if> /><span>是</span>
            </td>
        </tr>

		<tr>
			<td>功能链接：</td>
			<td style="text-align:left">
                <input type="text" id="href" name="href" value="${function.href}" style="width: 90%;" title="用于菜单类型"/>
            </td>
		</tr>
		
		<tr>
			<td>功能图标：</td>
			<td style="text-align:left">
                <input type="text" id="icon" name="icon" value="${function.icon}" style="width: 90%;" title="用于菜单和按钮类型"/>
            </td>
		</tr>
			
		<tr>
			<td>功能排序：</td>
			<td>
                <input type="text" id="sortCode" name="sortCode" value="${function.sortCode}" style="width: 90%;" title="" maxlength="2"/>
            </td>
		</tr>
		
		<tr>
			<td>功能分组：</td>
			<td>
                <input type="text" id="groups" name="groups" value="${function.groups}" style="width: 90%;" title=""/>
            </td>
		</tr>
		
		<tr>
			<td colspan="2"><input type="button" class="btn pull-right" value="提交" name="saveBtn" /></td>
		</tr>
	</table>
</form>
<script type="text/javascript" src="${ctx}view/core/function/js/modFunction.js"></script>