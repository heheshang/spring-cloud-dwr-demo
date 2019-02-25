<html>
<head>
	<title></title>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/interface/DemoService.js'></script>
</head>
hello
<script>
	DemoService.echo('回声测试', function (str) {
		alert(str);
	});

</script>
</html>