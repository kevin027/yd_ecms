$(function(){
	
	//设置链接是否显示
	//刚开始加载时不显示
	$("#showLink1").css("display","none");
	$("#showLink select").change(function(){
		var chkText = $("#showLink select option:selected").val();
		console.log(chkText);
		if(chkText ==  'true'){
			$("#showLink1").css("display","");
		}else{
			$("#showLink1").css("display","none");
		}
	});
	
  $('#funModForm').find('[name=saveBtn]').on('click', function(){
    $('#funModForm').form('submit', {
      'success' : function(result) {
	    result = JSON.parse(result);
	    var resultMessage;
        if (result.success) {
		  resultMessage = result.success;
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
    return false;
  });
});

