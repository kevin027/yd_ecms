// 部门选择树对象
var _orgTreeObj;

$(document).ready(function() {
  // 加载组织机构树
  loadOrgTree();
});

function loadOrgTree() {
  $.get('org/listOrgTreeForOrgSelect.do', function(zTreeNodes) {
    // 树显示的一些设置
    var setting = {
      view: {
        selectedMulti: false
      }
      ,callback: {
        onClick: function(event, treeId, treeNode) {
          $('#_orgFullName').html(treeNode.name);
          var tmp = treeNode;
          var _selDeptFullName = treeNode.name;
          while (tmp.pid) {
            tmp = _orgTreeObj.getNodeByParam('id', tmp.pid);
            _selDeptFullName = tmp.name + '->' + _selDeptFullName;
          }
          $.getJSON('department/listDepartmentForSelect.do?query.orgId=' + treeNode.id, function(result) {
        	 var tempUl = $('<ul style="list-style:none;margin:0px"/>');
             $.each(result.rows, function() {
                var chk = $('<input type="checkbox" name="sc" />').val(this.id).attr('alt', _selDeptFullName);
                var name = $('<span />').text(this.name);
                var labelN = $('<label class="checkbox inline" />').append(chk).append(name);
                var tempLi = $('<li style="text-align:left;padding-left:10px"/>').append(labelN);
                tempUl.append(tempLi);
             });
             tempUl.find('input:checkbox').on('change', function(ev) {
                 if (this.checked) {
                   tempUl.find('input:checkbox').not(this).attr('checked', false);
                 }
                 ev.stopPropagation();
             });
             $('#_deptList').html(tempUl);
          });
        }
      }
    };
    _orgTreeObj = $.fn.zTree.init($("#_orgTree"), setting, zTreeNodes);
  }, 'json');
}
