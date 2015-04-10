$(function(){
	
	 // 将登录用户信息放进localStorage
	 var lastLoginAccounts = $('#loginAccounts').val();
	 if (lastLoginAccounts && window.localStorage) {
		 localStorage.setItem('lastLoginAccounts', lastLoginAccounts);
	 }
	 
	//刷新和返回主页
	$("#tabs").tabs({
	    	tools:[{ 
	    		id:'refresh',
	            iconCls:'icon-reload',    
	            handler:function(){
	               $.messager.show({
	    			title : '提示',
	    			msg : '刷新成功！'
	    		   });
	               var _tabs = $("#tabs").tabs('getSelected');
	               var frame = $('iframe',_tabs); 
	               try {
	    			if (frame.length > 0) {
	    				//重新获取iframe地址
	    				for ( var i = 0; i < frame.length; i++) {
	    					frame[i].contentWindow.document.write('');
	    					frame[i].contentWindow.close();
	    					frame[i].src = frame[i].src;
	    				}
	    				try {
	    					CollectGarbage();//JS清理垃圾，内存释放
	    				} catch (e) {
	    				}
	    			}
	    		} catch (e) {
	    		}   
	            }    
	        },{
		        id:'close',
		        iconCls:'icon-no',
		        handler:function(){
		           $('#tabs').find('.tabs-title').each(function() {
			           	if($(this).text() != '系统桌面' && $(this).text() != '全部任务' && $(this).text() != '参与任务' && $(this).text() != '当前任务'){
			           		$('#tabs').tabs('close', $(this).text());
			           	}
		           });
		           $('#tabs').tabs('select', '系统桌面');
		        }
		    },{
	        id:'desk',
	        iconCls:'icon-desk1',
	        handler:function(){
	           if($('#tabs').tabs('getSelected')== $('#tabs').tabs('getTab', '系统桌面')){
	    			$.messager.show({
	    			title : '提示',
	    			msg : '此处已是系统桌面！'
	    		   });    
	           }else{
	        	   $.messager.show({
		    			title : '提示',
		    			msg : '已切换到系统桌面！'
		    		   });    
	        	   $('#tabs').tabs('select', '系统桌面');
	           }
	           
	        }
	        }] 
	    });
	  
	   $.easyui.tooltip.init($("#desk"), { content: "返回桌面", trackMouse: true });
	   $.easyui.tooltip.init($("#refresh"), { content: "刷新", trackMouse: true });
	   $.easyui.tooltip.init($("#close"), { content: "关闭全部", trackMouse: true });
	
   	   //显示日期季节
       var timerSpan = $("#timerSpan");
       interval = function () { timerSpan.text($.date.toLongDateTimeString(new Date())); };
       interval();
       window.setInterval(interval, 1000);
       
       //引导动画
       startFlag=true;
       if(!$.cookie('startUseOa')){
    	   startFlag = $.cookie('startUseOa');
    	   console.log(startFlag);
       }
       //startLeader(startFlag);
		
		
       //收起顶栏
       $("#btnHideNorth").click(function(){
       	$("#main").layout('collapse','north');
       });
       
       //锁定窗口
       $("#lockWin").click(function(){
    	$.post('lockAccount.do', function(result){
    		$("#loginDialog").window({ 
           		title:'锁定窗口',
           	    width:400,    
           	    height:130,    
           	    modal:true,
           	    iconCls:"icon-userPwd_edit",
           	    collapsible:false,
           	    minimizable:false,
           	    maximizable:false,
           	    resizable:false,
           	    draggable:false,
           	    closable:false
           	});
    	}, 'json');
       	return false;
       });
       
       $('[name="loginForm.password"]').on('keypress', function(ev) {
    	   if (13 == ev.keyCode) {
    		   unlock();
    		   return false;
    	   }
    	   ev.stopPropagation();
       });
       
       if ('false' != $('#lockFlag').val()) {
         $("#lockWin").trigger('click');
       }
       
      });
 
    //用户信息
    function userInfo(){
    	$("#userinfo").window({ 
    		title:'用户信息',
    	    width:600,    
    	    height:400,    
    	    modal:true,
    	    collapsible:false,
    	    minimizable:false,
    	    maximizable:false,
    	    resizable:false,
    	    content:"<iframe src='info.jsp' style='width:100%;height:98%;border:none'></iframe>"
    	}); 
    	
    }
    //修改密码
    function editPwd(){
    	$("#editPwd").window({ 
    		title:'修改密码',
    	    width:400,    
    	    height:280,    
    	    modal:true,
    	    iconCls:"icon-userPwd_edit",
    	    collapsible:false,
    	    minimizable:false,
    	    maximizable:false,
    	    resizable:false
    	}); 
    }
    
    // 解锁
    function unlock() {
      $("#loginDialogForm").form('submit', {
        'success' : function(result) {
          result = JSON.parse(result);
          
          if (result.success) {
            $("#loginDialog").window('close');
            $("#loginDialogForm").form("reset");
            return;
          }
          
          $.messager.show({
            title:'提示'
            ,msg: result.error
            ,timeout:2000
            ,showType:'slide'
          });
        }
      });
    }
    
    
    //清空表单
    function clearFormPwd() {
      $("#pwdForm").form("reset");
    }
    
    function saveFormPwd() {
      $("#pwdForm").form('submit', {
        'success' : function(result) {
    	  result = JSON.parse(result);
    	  var resultMessage = result.success ? result.success : result.error;
    	  $.messager.show({
    	    title:'提示'
    	    ,msg: resultMessage
    	    ,timeout:2000
    	    ,showType:'slide'
    	  });
    	  
    	  if (result.success) {
    	    $("#editPwd").window('close');
    	    $("#pwdForm").form("reset");
    	  }
        }
      });
    }
    
    //拓展两次密码验证
    $.extend($.fn.validatebox.defaults.rules, {
        equals: {
            validator: function(value,param){
                return value == $(param[0]).val();
            },
            message: '两次密码不一致.'
        }
    });
    
    //引导动画
    function startLeader(startUseOa){
    	if(!startUseOa){
    	$(".window-mask").show();
    	$('#grumble1').grumble(
				{
					text: '这里是导航栏，在这里你可以方便，高效的管理你的项目!',
					angle: 90,
					type: 'alt-',
					distance: 80,
					showAfter: 2000,
					hideAfter: 3000,
					hasHideButton: true, 
					buttonHideText: '关闭!'
				}
			);
		$('#grumble2').grumble(
				{
					text: '在这里你可以个性化自己的设置!',
					angle: 200,
					type: 'alt-',
					distance: 3,
					showAfter: 5000,
					hideAfter: 3000,
					hasHideButton: true, 
					buttonHideText: '关闭!'
				}
			);
		$('#grumble3').grumble(
				{
					text: '日常的通知提醒在系统桌面显示,代办，邮件，任务信息一目了然!',
					angle: 125,
					type: 'alt-',
					distance: 0,
					showAfter: 8000,
					hideAfter: 3000,
					hasHideButton: true, 
					buttonHideText: '关闭!'
				}
			);
		
		$("#startOa").slideUp( 300 ).delay( 20000 ).fadeIn( 400 ).click(function(){
			$(this).hide();
			$(".window-mask").hide();
			//登录提示信息
		    $.messager.show({
		           title:'欢迎使用',
		           msg:'欢迎使用本系统，祝你今天工作愉快！',
		           timeout:5000,
		           showType:'slide',
		           iconCls:'icon-auditLogo'
		       });
		    
		    //保存状态到cookie
		    //保存30天
		    startUseOa=false;
			$.cookie('startUseOa', startUseOa, {
				expires : 30
			});
		  });
    	}else{
    		$(".window-mask").hide();
    		//登录提示信息
		    $.messager.show({
		           title:'欢迎使用',
		           msg:'欢迎使用本系统，祝你今天工作愉快！',
		           timeout:5000,
		           showType:'slide',
		           iconCls:'icon-auditLogo'
		       });
    	}
    }