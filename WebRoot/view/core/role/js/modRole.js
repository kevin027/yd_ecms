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
  $('#u_form').form('submit', {
	url:"role/updateRole"
	,success : function(data) {
      data = JSON.parse(data);
      var resultMessage;
      if (data.success) {
	    resultMessage = data.success;
      } else {
	    resultMessage = data.error;
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
}