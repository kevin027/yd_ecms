var tb  = $(".pcAdd").parent(":first").next();

//行号
function countNum(table){
	var i=1;
	var trNum = $("tr",table).length-1;
	$("tr:not(:first)",table).each(function(){
		if(i<=trNum){
			$("td:eq(1)",this).text(i);
			i++;
		}
	});
}
//单击选中行
function getSelectRow(table){
	$("tr:not(:first)",table).each(function(){
		$(this).click(function(e){
			if ($(this).hasClass("selected"))//判断是否选中
			{
				$(this).removeClass("selected").find(":checkbox").prop("checked",false);
			}
			else{
				$(this).addClass("selected").find(":checkbox").prop("checked",true);
			}
		});
	});
}
$(function(){
	$("textarea").autosize();
	$(".pcAdd").on('click',addPC);
	getSelectRow(tb);
});

//执业证书新增
function addPC(){
	var addRowTemplete = '<tr class="pcAddCopy" >'
						   +'<td style="text-align:center"><input type="checkbox"/></td>'
						   +'<td style="text-align:center"></td>'
						   +'<td style="text-align:center"><input type="text" style="width:80%" class=" "/></td>'
						   +'<td style="text-align:center">'
						   +'<select type="text"  class="input-small">'
						   +'<option></option>'
						   +'</select></td>'
						   +'<td style="text-align:center"><input type="text" class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px" /></td>'
						   +'<td style="text-align:center"><input type="text" class="input-small easyui-datebox" data-options="editable:false" style="height:30px;line-height:30px"/></td>'
						   +'<td style="text-align:center"><input type="text" style="width:80%" class=""/></td>'
						   +'</tr>';
	tb.append(addRowTemplete);
	$('.easyui-datebox','.pcAddCopy').datebox();
	countNum(tb);
	getSelectRow(tb);
	
}
//执业证书删除
function delPC(){
	
	
}