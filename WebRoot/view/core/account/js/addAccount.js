var scope = $('#addAccount_form');

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
  var roleIds = [];
  scope.find('[name=roleSlelect] option:selected').each(function() {
    roleIds.push(this.value);
  });
  scope.find('[name="saveAccountForm.roleIds"]').val(roleIds.join(','));
	  
  scope.form('submit', {
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
}

//人员选择
function chooseBtnClick(ev) {
  chooseStaff(top, function(target) {
    $('#accountId').val(target.id);
    $('#accountName').val(target.name);	  
  });
  return false;
}

//人员选择
function cancelBtnClick(ev) {
  $('#accountId').val('');
  $('#accountName').val('');
  return false;
}