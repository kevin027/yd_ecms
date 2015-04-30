// 功能选择树
var funTreeObj;

(function($) {

  //选中的树节点
  var selNode = null;
  
  //树显示的一些设置
  var funTreeSetting = {
    view: {
      selectedMulti: false
    }
    ,check: {
      enable: true
      ,chkStyle: 'checkbox' 
      ,chkboxType: {chkboxType: { "Y": "p", "N": "s" }}
    }
    ,callback: {
      onExpand : function(event, treeId, treeNode) { // 展开时关闭其它同级节点
        if (funTreeObj) {
          var siblings = funTreeObj.getNodesByParam("pid", treeNode.pid, null);
          $.each(siblings, function() {
            if (treeNode.id != this.id && this.open) {
            	funTreeObj.expandNode(this, false, true);	
            }
          });
        }
      }
      , onClick: function (event, treeId, treeNode) {
    	selNode = treeNode;
        $('#orgInfo').load('org/orgTreeNodeInfo', {'orgId' : treeNode.id});
      }
    }
  };
  
  function loadZtree() {
    $.get('fun/listFunctionForSelect', function(zTreeNodes) {
      funTreeObj = $.fn.zTree.init($("#funTree"), funTreeSetting, zTreeNodes);
    }, 'json');
  }
  
  loadZtree();
  
  function registerWindowEvent() {
	// 注册添加功能按钮事件
    $('#addFunction').on('click', function() {
    	if (!selNode) {
    	  showTips('请选择一个节点进行操作。');
    	  return false;
    	}
    	var url = 'fun/addFunctionPage?parentId=' + selNode.id;
    	$.layer({
      		type : 2,
      		shade : [0],
      		title : ['新增功能',true],
      		iframe : {src : url},
      		shade : [0.5 , '#000' , true],
      		area : ['800px' , '600px'],
      		offset : ['80px', '50%'],
      		end : function() {
      			window.location.href = window.location.href;
      		}
      	});
    });
    
    // 添加修改功能按钮事件
    $('#modFunction').on('click', function() {
    	if (!selNode) {
    		showTips('请选择一个节点进行操作。');
        	return false;
        }
      	var url = 'fun/editFunctionPage?id=' + selNode.id;
      	$.layer({
      		type : 2,
      		shade : [0],
      		title : ['修改功能',true],
      		iframe : {src : url},
      		shade : [0.5 , '#000' , true],
      		area : ['800px' , '600px'],
      		offset : ['50%', '50%'],
      		end : function() {
      			window.location.href = window.location.href;
      		}
      	});
    });
    
    // 添加删除功能按钮事件
    $('#delFunction').on('click', function() {
    	if (!selNode) {
    		showTips('请选择一个节点进行操作。');
        	return false;
        }
    	if (selNode.children && 0 < selNode.children.length) {
    		showTips('要删除该节点请先删除该节点下的所有子节点。');
        	return false;
    	}
    	var url = 'fun/deleteFunction';
    	showConfirm('删除数据后将不可回恢，确定删除吗?', function() {
    		$.post(url, {'function.id' : selNode.id}, function(json) {
    			if (json.success) {
    				showInfo(json.success, function(){
    					window.location.href = window.location.href;
    				});
    			} else if (json.error) {
    				showInfo(json.error);
    			}
    		}, 'json');
    	}, "提醒");
    });
  }
  
  // 加载弹出层插件，并回调注册按钮的事件。
  DynamicLoader.load(
    'js/jquery/layer-1.6/layer.min.js' // 这货通过修改其js已经配置了自动加载样式的功能。
    , registerWindowEvent
  );
  
})(jQuery);