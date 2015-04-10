(function() {
  var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split(''); 
  Math.uuid = function (len, radix) {
    var chars = CHARS, uuid = [], i; 
    radix = radix || chars.length; 
  
    if (len) { 
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix]; 
    } else { 
      var r; 
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-'; 
      uuid[14] = '4'; 
  
      for (i = 0; i < 36; i++) { 
        if (!uuid[i]) { 
          r = 0 | Math.random()*16; 
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r]; 
        } 
      } 
    }
    return uuid.join(''); 
  }; 

  Array.prototype.stackTop = function (o) {
	var size = this.length;
	for (var i = 0; i < size; i++) {
		if (this[i] === o) {
			this.unshift(this.splice(i,1)[0]);
			return o;
		}
	}
	this.unshift(o);
	return o;
 };
})();

_CoolWindow = function(params) {

  var _curWindow = this;
  
  var op;
  var windowHead, headTitle;
  var windowBody, bodyContent;
		
  op = document.createElement('div');
  op.id = params['id'];
  op.tabindex = 0;
  op.className = 'coolwindow';
  op.style.display = 'none';

  //标题栏
  windowHead = document.createElement('div');
  windowHead.className = 'coolwindow-head';
  if (params['showHead']) windowHead.style.display = 'none';

  //标题栏添加标题
  headTitle = document.createElement('span');
  headTitle.className = "coolwindow-title";
  headTitle.innerHTML = params['title'];
  windowHead.appendChild (headTitle);

  //标题栏添加关闭按钮
  headClose = document.createElement('span');
  headClose.className = 'coolwindow-close';
  if (!params['showClose']) {
    headClose.style.visibility = 'hidden';
  }
  $(headClose).on('click', function() {
    $.CurCoolWindow('close');
    return false;
  });
  windowHead.appendChild (headClose);
  
  //内容栏
  windowBody = document.createElement('div');
  windowBody.className = "coolwindow-body";
  op.appendChild(windowBody);

  //添加窗体内容部分
  bodyContent = document.createElement('div');
  bodyContent.className = 'coolwindow-content';
  windowBody.appendChild(bodyContent);

  var width = params['width'];
  var height = params['height']- 30;
  width = width > params['maxWidth'] ? params['maxWidth'] : width;
  height = height > params['maxHeight'] ? params['maxHeight'] : height;

  op.appendChild(windowHead);
  op.appendChild(windowBody);

  if (height >= 527) {		
    $(windowHead).css({width:(parseInt(width)+22)+"px"});
    $(bodyContent).css({width:(parseInt(width)+17)+"px", height:height+"px"});
  } else {
    $(windowHead).css({width:(parseInt(width)+10)+"px"});
    $(bodyContent).css({width:width+"px", height:height+"px"});
  }

  var dom = document.documentElement || document.body;
  var top = Math.abs(parseInt((dom.clientHeight-height-53)/2)) + (params['openAnimate'] ? (-20) : 0) + 'px';
  var left = Math.abs(parseInt((dom.clientWidth-width-32)/2)) + 'px';
  $(op).css({
    'top' : top,
    'left' : left
		//top: parseInt((dom.clientHeight-height-53)/2) /*+ dom.scrollTop*/ + 'px',
		//left: parseInt((dom.clientWidth-width-32)/2) /*+ dom.scrollLeft*/ + 'px'
  });
  
  var rv = {};
  
  this.setReturnValue = function(key, value) {
	rv[key] = value;  
  };

  /* 窗口打开事件 */
  this.open = function () {
    if (!params['isOpen']) {
      params['isOpen'] = true;
			
      /* 设置窗口的层次 */
      var modalIndex = parseInt(new Date().getTime()/1000) + parseInt(params['zIndexPlus']);
			
      /* 设置窗口的模态效果 */
	  if (params['modal']) {
		  $('<div class="coolwindow-mask" id="mask_' + params.id + '" />')
		    .css('zIndex', modalIndex)
		    .on('keydown', function(ev){return false;})
		    .on('click', function(ev){return false;})
		    .appendTo(params['parent']).show();
	  }
			
      /* 将窗口对象添加到指定的父对象中去 */
      $(op)
	    .appendTo(params.parent)
	    .css('zIndex', modalIndex)
	    .css('opacity', params['openAnimate'] ? 0.5 : 1)
	    .fadeIn("slow")
	    .focus();
			
	  /* 是否需要动画。*/		
      if (params['openAnimate']) {
	    $(op)
		  .animate({ 
		    'opacity': 0.9
		    ,'top': '+=11px'
		  }, 500)
		  .animate({ 
		    'opacity': 1
		  }, 200).focus();
      }
      
      /* 将窗口对象放在栈顶。*/
      _curWindowStack.unshift(_curWindow);
      
      /* 加载内容 */
      _curWindow.refresh();
			
      /* 设置窗口的自动关闭时间 */
      params['timeout'] && params['timeout'] > 0 && setTimeout(close, params['timeout']*1000);
    }
  };

  /* 窗口关闭事件 */
  this.close = function () {
    if (params['isOpen']) {
			
      /* 是否打开 */
      params['isOpen'] = false;

	  /* 窗口关闭后的回调方法 */
	  var beforeClose = params['beforeClose'];
	  if (beforeClose) {
	    if (typeof(beforeClose) == "function") {
	      beforeClose.call(params.parent, rv);
	    }
      }
			
      /* 取消模态效果 */
      if (params['modal']) {
    	$(params.parent).find('#mask_' + params.id).remove();
      }

      /* 窗口栈中移除该窗口对象，并且激活栈中第一个窗口对象*/
	  _curWindowStack.shift();
	  if (_curWindowStack.length > 0) _curWindowStack[0].focus;
	  
	  /* 移除窗口 */
      $(op).fadeOut("slow").remove();
    }	
    return false;
  };

  this.refresh = function() {
    $(bodyContent).load(params['url']);
  };
  
  _curWindow.open();
};

$.fn.CoolWindow = function (custom) {
  this.defaultSetting = {
    'modal': true
    ,'width': 500
    ,'height': 400
    ,'maxWidth' : 950
    ,'maxHeight' : 600
    ,'showHead' : false
    ,'showClose' : true
    ,'drag' : false
    ,'title' : ''
  };
  var params = $.extend(
	{'parent':this.get(0), 'id':Math.uuid(16)}
	, this.defaultSetting, custom);		
  new _CoolWindow(params);
  return this;
};

// 保存当前打开窗口的栈
var _curWindowStack = [];

// 栈顶窗口操作方法
$.CurCoolWindow = function (action) {

  if ('close' == action || 'refresh' == action) {
    0 < _curWindowStack.length && _curWindowStack[0].close();
  }
  else if ("setReturnValue" == action) {
    0 < _curWindowStack.length && _curWindowStack[0][action](arguments[1], arguments[2]);
  }
};