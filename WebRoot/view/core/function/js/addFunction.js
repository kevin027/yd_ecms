$(function(){
  //设置表格行高
  //$("table tr td").css("line-height","50px");
	
  $('#funAddForm').find('[name=saveBtn]').on('click', function() {
	var idInput = $('#funAddForm').find('[name="id"]');
	if ('' != idInput.val()) {
	  $.messager.show({
	    title:'提示'
	    ,msg: '你已经成功提交该表单，请关闭该窗口。'
		,timeout:2000
	    ,showType:'slide'
	  });
	  return false;
	}
    $('#funAddForm').form('submit', {
      'success' : function(result) {
	    result = JSON.parse(result);
	    var resultMessage;
        if (result.success) {
		  resultMessage = result.success;
		  idInput.val(result.saveId);
		} else {
		  resultMessage = result.error;
        }
        $.messager.show({
		  title:'提示'
		  ,msg: resultMessage
		  ,timeout:2000
          ,showType:'slide'
        });
      }
    });      		
  });
	
});

