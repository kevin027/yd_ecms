// 部门选择树对象
var _orgTreeObj;

$(document).ready(function() {
  // 加载组织机构树
  loadOrgTree();
});

function loadOrgTree() {
  $.get('org/listOrgTreeForOrgSelectWithoutCheckBox', function(zTreeNodes) {
    // 树显示的一些设置
    var setting = {
      view: {
        selectedMulti: false
      }
      ,callback: {
        onClick: function(event, treeId, treeNode) {
          $('#_orgFullName').html(treeNode.name);
          
          var _group = $('#_staffList').find('#' + treeNode.id);
          if (0 != _group.length) {
        	  $('#_staffList').find('._deptGroup').not(_group).hide();
        	  
        	  _group.show();
        	  return;
          } 
          
          $('#_staffList').find('._deptGroup').hide();
          
          $.getJSON('staff/listStaffForSelect?orgId=' + treeNode.id, function(result) {
        	 var tempUl = $('<ul style="list-style:none;margin:0px" class="_deptGroup" id="' + treeNode.id + '" />');
             $.each(result.rows, function() {
                var chk = $('<input type="checkbox" name="sc_' + treeNode.id + '"/>')
                	.val(this.id)
                	.attr('checked', 0 < $('#_staffList').find('input:checkbox:checked[value=' + this.id + ']').length);
                var name = $('<span />').text(this.name);
                var labelN = $('<label class="checkbox inline" />').append(chk).append(name);
                var tempLi = $('<li style="text-align:left;padding-left:10px"/>').append(labelN);
                tempUl.append(tempLi);
             });
             
             tempUl.find('input:checkbox').on('change', function(ev) {
               $('#_staffList').find('input:checkbox[value=' + this.value + ']').not(this).attr('checked', this.checked);
               ev.stopPropagation();
             });
             $('#_staffList').append(tempUl);
          });
        }
      }
    };
    _orgTreeObj = $.fn.zTree.init($("#_orgTree"), setting, zTreeNodes);
  }, 'json');
}
