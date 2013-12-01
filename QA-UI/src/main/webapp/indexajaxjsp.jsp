<%@page import="java.sql.*"%>
<html>
<head>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
 </script>
<script type="text/javascript">
function showData(value){ 
$.ajax({
    url : "http://localhost:8983/solr/people/type-ahead?q=Ivan"+value,
    type : "GET",
    async : false,
    success : function(data) {
//Do something with the data here
alert("Hello");
alert(data);
    },
	error : function(xhr,ajaxOptions,thrownError) {
	//Do something with the data here
	alert("Hi1");
	alert(xhr.status);
	alert("Hi2");
	alert(thrownError);
	alert("Hi3");
	    }
});
alert("Hi"+data)
}


</script>
</head>
<body>
<form name="employee">
<div class="ui-widget">
  <input type="text"  id="name" onkeyup="showData(this.value);">
</div>
 
<br>


</table>
</body>
</html>