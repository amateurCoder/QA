<!DOCTYPE html>
<html>
<head><title>UB Ask </title>
<link rel="stylesheet"
      href="./css/styles.css"
      type="text/css"/>
       <link rel="stylesheet" href="./bootstrap/css/bootstrap.css" /> 
<link rel="stylesheet" href="./bootstrap/css/bootstrap-responsive.css" /> 

<!-- Set the viewport width to device width for mobile -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 
<link rel="stylesheet" href="./css/bootstrap.min.css" />
-->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link href='http://fonts.googleapis.com/css?family=Raleway:400' rel='stylesheet' type='text/css'>
<script src="./js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="./bootstrap/js/bootstrap.js"></script>
<script src="./js/script.js"></script>
<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
<link rel="stylesheet" href="./css/bootstrap-extension.css" />

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
    	$("#question").autocomplete({ 
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
<meta charset="UTF-8">
<meta http-equiv="Content-type" content="text/html; charset=UTF-8"> 
</head>
<meta charset="UTF-8">
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
<body>
<meta charset="UTF-8">
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
<div style="background-image:url(img/UBimage.jpg);padding-bottom:2px;background-size:100% 100%;background-repeat:no-repeat" >
  <div class="hidden-phone" style="padding:30px 30px 0px 30px;margin-bottom:1px;">
    <div class="container">
      <table cellpadding="0" cellspacing="0">
        <tr>
          <td rowspan=2><a title="START Home" href="index.jsp" style="vertical-align:bottom;font-weight:bold;font-size:5.5em;text-decoration:none; color:white; text-shadow: black 0.025em 0.025em 0.025em; font-family:Bodoni,serif;">UB</a></td>
          <td style="vertical-align:bottom"><a title="START Home" href="index.jsp" style="font-size:3.25em;font-weight:bold;text-decoration:none; color:white; text-shadow: black 0.05em 0.05em 0.05em; font-family:Bodoni,serif;">ASK</a></td>
        </tr>
        <tr>
          <td style="padding-left:7px;padding-top:2px;font-size:1.2em;vertical-align:top; text-decoration:none; color:white; text-shadow: black 2px 2px 2px; ">Natural Language Question Answering System</td>
        </tr>
      </table>
      
      </div>
      </div>
	<form action="FirstServlet"><div class="input-append">
		 <div style="margin-bottom:17px;margin-left:45px;">  
		 	<div class="ui-widget">
		 	<label for="name"></label>
			<input style="height:28px;font-size:.9em" placeholder="ask START a question" type="text" id = "question" name="question" onkeyup="showData(this.value);" size="70" value="" x-webkit-speech />
        	<button style="height:35px" type="submit">Ask Question <i class="icon-chevron-right icon-white" style="vertical-align:text-bottom;"></i></button>
  		 </div>
  		 </div>
  </div>     
 </form>
 </div>
 <br>
<fieldset>

<legend>Questions about People</legend>
<ul>
  <li><a href="FirstServlet?linkquestion=Where was Ivan Smolovic born">Where was Ivan Smolovic born?</a></li>
 
</ul>
</fieldset>
<p/>
<fieldset>
<legend>Questions about Places</legend>
<ul>
   <li><a href="FirstServlet?linkquestion=Who is the director of Shawshank Redemption">Who is the director of Shawshank Redemption?</a></li>
  
</ul>
</fieldset>
<p/>
<fieldset>
<legend>Questions about Films</legend>
<ul>
   <li><a href="FirstServlet?linkquestion=Who is the director of Shawshank Redemption">Who is the director of Shawshank Redemption?</a></li>

</ul>
</fieldset>
<p/>
</body>
</html> 