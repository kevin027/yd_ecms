// dom加载完后
$(document).ready(function() {
  $('#opBar').find('[id]').each(function() {
    //按约定检索是否有该事件。
    var onClickFunction = window[this.id + 'Click'];
    if (onClickFunction) {
      $(this).on('click', onClickFunction);
    }
  });
});

//jquery事件函数，清空按钮事件
function clearBtnClick(ev) {
  $('#u_form').form('clear');
  return false;
}

//jquery事件函数，保存按钮事件
function saveBtnClick(ev) {
  var roleId = $('#roleId').val();
  if (!roleId) {
    $('#u_form').form('submit', {
	  url:"role/saveRole.do"
	  ,success : function(result) {
		result = JSON.parse(result);
        var resultMessage;
        if (result.success) {
	      resultMessage = result.success;
	      $('#roleId').val(result.saveId);
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
  } else {
    $('#u_form').form('submit', {
      url:"role/updateRole.do"
      ,success : function(result) {
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
  }
  return false;
}