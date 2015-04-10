// 部门选择树对象
var orgTreeObj;

// dom加载完后
$(document).ready(function() {	
  // 注册事件
  $('#opBar').find('[id]').each(function() {
    var onClickFunction = window[this.id + 'Click'];
    if (onClickFunction) {
      $(this).on('click', onClickFunction);
    }
  });
  
  //加载组织机构树
  loadOrgZtree();
});

function loadOrgZtree() {
  $.getJSON('org/listOrgTreeForOrgSelect2.do?selOrgIds=' + $('#orgIds').val(), function(zTreeNodes) {
    // 树显示的一些设置
    var setting = {
      view: {
        selectedMulti: false
      }
      ,check: {
        enable:true
       ,chkStyle:"checkbox" 
       ,chkboxType: {"Y": "p", "N": "s"}
      }
      ,callback : {
        beforeCheck : function(treeId, treeNode) {
          /*if (orgTreeObj && !treeNode.checked) {
            orgTreeObj.checkAllNodes(false);
          }*/
        },
        onCheck : function(event, treeId, treeNode) {
          /*var tempOrgNames = getSelectZTreeNodeNames(orgTreeObj);
          $('#orgSelectInfo').text('当前选择的部门为：' + (0 < tempOrgNames.length ? tempOrgNames.join('-') : '暂无')); 
          if (treeObj) {
            orgTreeObj.expandNode(treeNode, true, true);
          }*/
        }
      }
    };
    orgTreeObj = $.fn.zTree.init($("#orgTree"), setting, zTreeNodes);
    
    // 加载完后，显示当前选中的组织机构。
    var tempOrgNames = getSelectZTreeNodeNames(orgTreeObj);
    $('#orgSelectInfo').text('当前选择的部门为：' + (0 < tempOrgNames.length ? tempOrgNames.join('-') : '暂无'));
  });
}

//jquery事件函数，清空按钮事件
function clearBtnClick(ev) {
  $('#u_form').form('clear');
  return false;
}

// jquery事件函数，保存按钮事件
function saveBtnClick(ev) {
	
  // 保存orgTreeObj选择的机构Id
  if (orgTreeObj) {
    var chks = orgTreeObj.getCheckedNodes(true);	
    var tempOrgIds = [];
    $.each(chks, function(){
    	tempOrgIds.push(this.id);  
    });
    $('#orgIds').val(tempOrgIds.join(','));
  } 
  
  $('#u_form').form('submit', {
	url:"staff/updateStaff.do"
	,success : function(data) {
      data = JSON.parse(data);
      var resultMessage;
      if (data['success']) {
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

// 获取ztree选中的字节名字，以数组的形式返回。
function getSelectZTreeNodeNames(ztreeObj) {
  var tempOrgNames = [];
  if (ztreeObj) {
    var chks = orgTreeObj.getCheckedNodes(true);
    $.each(chks, function() {
      tempOrgNames.push(this.name);
    });
    return tempOrgNames;
  }
  return tempOrgNames;
}