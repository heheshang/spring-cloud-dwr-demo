<html>
<head>
	<title></title>
	<script type='text/javascript' src='/dwr/engine.js'></script>

	<script type='text/javascript' src='/dwr/interface/Demo2Consumer.js'></script>

	<#--<script>
		function onpage(){
			// 页面加载直接调用这个函数，我这块使用点击按钮
			Demo2Consumer.onPageLoad("123456");
		}
		// 后端会调用这个函数
		function getmessage(data){
			alert(data);
		}
	</script>-->

	<script>
		//  激活ajax
		dwr.engine.setActiveReverseAjax(true)
		// 页面未加载的时候是否发送通知
		dwr.engine.setNotifyServerOnPageUnload(true,true)
		// 出现错误后的处理方法
		dwr.engine.setErrorHandler(function(){})

		function getmessage(data){
			if (window.eventBus) {
				window.eventBus.$emit('getDwr',data);
			}
		}
	</script>
</head>
hello
<input type="button" onclick="onpage()" value="onpage" >
</html>

