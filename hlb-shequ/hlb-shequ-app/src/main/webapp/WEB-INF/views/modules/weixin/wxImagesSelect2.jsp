<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>layer演示之iframe自定义风格</title>
<style>
*{margin:0; padding:0;}
body{font-size:12px;}
pre{display:none;}
p{line-height:24px;}
button{height:30px; line-height:30px; padding:0 10px; margin-right:10px;}
</style>
</head>
<body>
<div style="padding:10px;">
    <p>这是一个iframe页面，通过配置border:[0]、title:false、closeBtn: false，使得该层保持了最原始的风格。这意味着你可以任意书写它的风格。</p>
    <p>另外，鉴于很多人不知道怎么在iframe中关闭自身，以及传值给父页面，下面贴出代码演示一下。</p>
    <pre>
&lt;button id="closebtn"&gt;关闭层&lt;/button&gt; &lt;button id="refresh"&gt;刷新父页面&lt;/button&gt; &lt;button id="transmit"&gt;给父页面传值&lt;/button&gt;
&lt;script&gt;<span class="run">
//比如在iframe中关闭自身
var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
$('#closebtn').on('click', function(){
    parent.layer.close(index); //执行关闭
});
//再比如刷新父页面
$('#refresh').on('click', function(){
    parent.location.reload();
});
//还比如给父页面传值
$('#transmit').on('click', function(){
    parent.$('#parentIframe').text('我从iframe页而来');
    parent.layer.tips('Look here', '#parentIframe', 2);
    parent.layer.close(index);
});
</span>&lt;/script&gt;
    </pre>
    <p>
    <button id="closebtn">关闭层</button><button id="refresh">刷新父页面</button><button id="transmit">给父页面传值</button></p>
</div>
<script src="http://res.xiami.net/pc/lay/lib.js"></script>
<script src="http://res.layui.com/lay/lib/laycode/laycode.min.js"></script>
<script>
new Function($('.run').text())();
$('pre').show().laycode({height:210, title:'在iframe中对父页面的任何操作，都是借助于parent对象。'});
</script>


<script src='http://w.cnzz.com/c.php?id=30060348'></script>
</body>
</html>
