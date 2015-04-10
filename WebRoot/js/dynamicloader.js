function DynamicLoader(){};
DynamicLoader.cache = [];
DynamicLoader.load = function() {
  var op = this;
  var address = arguments[0];
  var headDom = document.getElementsByTagName('head')[0];
  if ("string" == typeof address) {
    var ags = arguments;
    var aas = Array.prototype.slice.call(arguments); // 转为数组
    
    // 看看缓存中是否已经有该文件，如果有则跳过本文件加载。
	if (this.cache && -1 < this.cache.join(',').indexOf(address)) {
      aas.splice(0, 1);
	  if (0 < aas.length) { //如果还存在要加载的文件再进行下一个文件的加载。
	    ags.callee.apply(this, aas);
	  } 
	  return;
	}

    var dynamicDom = null;
    if (/js$/ig.test(address)) { //加载JS
      dynamicDom = document.createElement('script');
      dynamicDom.setAttribute('type', 'text/javascript');
      dynamicDom.setAttribute('src', address);
    }

    else if (/css$/ig.test(address)) { //加载CSS，safari不支持link的onload和onreadstatechange，所有也不支持该方法加载。
      dynamicDom = document.createElement('link');
      dynamicDom.setAttribute('type', 'text/css');
	  dynamicDom.setAttribute('rel', 'stylesheet');
      dynamicDom.setAttribute('href', address);
    }
	 
    if (!dynamicDom) return;
	dynamicDom.onload = dynamicDom.onreadystatechange = function() {
      if (!this.readyState || this.readyState=='loaded' || this.readyState=='complete') {
        this.onload = this.onreadystatechange = null;
	    op.cache.push(aas.splice(0, 1)); 
		if (0 < aas.length) { //如果还存在要加载的文件再进行下一个文件的加载。
		  ags.callee.apply(op, aas);
		} 
      }
    };
    headDom.appendChild(dynamicDom);
  }
  else if ('function' == typeof address) {
    address();
  }
};