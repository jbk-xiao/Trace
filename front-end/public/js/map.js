export function MP(ak) {  
    return new Promise(function (resolve, reject) {  
      window.onload = function () {  
        resolve(BMap)  
      }  
      var script = document.createElement("script");  
      script.type = "text/javascript";  
      script.src = 'http://api.map.baidu.com/api?v=2.0&ak='+ ak +'&__ec_v__=20190126&callback=onBMapCallback';  
      script.onerror = reject;  
      document.head.appendChild(script);  
    })  
  }  
  