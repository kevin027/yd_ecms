var scope = $('#saveCooperPersonForm');

$(document).ready(function() {
  scope.find('button[name]').each(function(){
    var onClickFunction = window[this.name + 'Click'];
    if (onClickFunction) {
      $(this).on('click', onClickFunction);
    }
  });
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

