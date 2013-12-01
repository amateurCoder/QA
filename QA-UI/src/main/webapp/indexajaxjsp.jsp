<%@page import="java.sql.*"%>
<html>
<head>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript">

function showData(value){ 
	var val = new Array();
	val = value.split(" ");
	 var x = val.length-1;
$.ajax({
    url : "FirstTemp?question="+value,
    type : "GET",
    async : false,
    dataType: "text",
    success : function(data) {
//Do something with the data here
	var dbdata=[]; 
	dbdata=JSON.parse(data);  
    	$("#name").autocomplete({ 
    		source: dbdata
    		}); 
    },
	error : function(xhr,ajaxOptions,thrownError) {
	alert(xhr.status);
	alert(thrownError);
	    }
});
}
</script>
</head>
<body>
<div class="ui-widget">
  <label for="name">Question: </label>
  <input type="text" id="name" onkeyup="showData(this.value);">
</div>
</table>
</body>
</html>