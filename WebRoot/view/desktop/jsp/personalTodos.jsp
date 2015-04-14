<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
$(function(){	
	//待办数量
	var num= '0';
	getTodos(num,p1);
	if(num == 0){
		$("#personalToDo").addClass('hide');
		$(".personalToDo").removeClass('hide');
	}
});
</script>

<table class="table table-condensed " id="listTable" style="table-layout:fixed;margin:0px;border-radius:0;border-collapse:collapse;">
  <tr>
     <th class="text-success" style="text-align:center;border-right:1px solid #ddd;border-top:0px solid #ddd">--</th>
     <th class="text-success" style="text-align:center; width:120px;border-top:0px solid #ddd">--</th>
  </tr>

</table>