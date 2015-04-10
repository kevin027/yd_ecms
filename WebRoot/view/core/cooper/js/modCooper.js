var scope = $('#saveCooperForm');

$(document).ready(function() {
  scope.find('button[name]').each(function(){
    var onClickFunction = window[this.name + 'Click'];
    if (onClickFunction) {
      $(this).on('click', onClickFunction);
    }
  });
  
  $("textarea").autosize();
});

// 提交表单
function saveBtnClick(ev) {
	scope.form('submit', {
		'success' : function(result) {
			result = JSON.parse(result);
			var resultMessage;
			if (result.success) {
				resultMessage = result.success;
			} else {
				resultMessage = result.error;
			}
			top.$.messager.show({
				title : '提示',
				msg : resultMessage,
				timeout : 2000,
				showType : 'slide'
			});
		}
	}); 
  
  return false;
}


// 人员选择
function chooseBtnClick(ev) {
	chooseStaff(top, function(target) {
		$('#handlerId').val(target.id);
		$('#handlerName').val(target.name);
	});
	return false;
}