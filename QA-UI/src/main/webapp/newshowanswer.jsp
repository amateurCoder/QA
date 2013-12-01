<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>UB Ask </title>
<link rel="stylesheet"
      href="./css/styles.css"
      type="text/css"/>
<!-- <link rel="stylesheet" href="./bootstrap/css/bootstrap.css" /> 
<link rel="stylesheet" href="./bootstrap/css/bootstrap-responsive.css" />  -->

<link href='http://fonts.googleapis.com/css?family=Raleway:400' rel='stylesheet' type='text/css'>
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
<script>
var string ='${suggestion}';
var suggest = [];
var link;
var linkname;
suggest = string.split(",");
//for(var i = 0; i < suggest.length; i++) {alert(suggest[i]);}
var spellstring='${spellsuggestion}';
var spellsuggest = [];
var spelllink;
var spelllinkname;
spellsuggest = spellstring.split(",");
window.onload = function() {
	if(suggest!="")
	   	{
	       for(var i = 0; i < 6; i++) 
	       {
	       link = "FirstServlet?question="+suggest[i];
	       linkname="demo"+i;
	       //alert(linkname);
	       document.getElementById(linkname).href=link;
	   	   document.getElementById(linkname).innerHTML=suggest[i];
	       document.getElementById("label1").innerHTML = "<i>More like these:</i> ";
	       }
	
	     
	}
	if(spellsuggest!="")
   	{
       for(var i = 0; i < 2; i++) 
       {
       spelllink = "FirstServlet?question="+spellsuggest[i];
       spelllinkname="dym"+i;
       //alert(linkname);
       document.getElementById(spelllinkname).href=spelllink;
   	   document.getElementById(spelllinkname).innerHTML=spellsuggest[i];
       document.getElementById("label1").innerHTML = "<i>Did you mean:</i> ";
       }
      
}

}
</script>
</head>
<body>
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
			<input style="height:28px;font-size:.9em" id="name" type="text" id="question" value = "<%= request.getAttribute("question").toString() %> " onkeyup="showData(this.value);" name="question" size="70" value="" x-webkit-speech />
	
        	<button style="height:35px" type="submit">Ask Question <i class="icon-chevron-right icon-white" style="vertical-align:text-bottom;"></i></button>
  		 </div>
  		 </div>
  </div>     
 </form>
 </div>
 <br>
 <h4><i>Showing results for: </i><%= request.getAttribute("answerfor").toString() %></h4>
<h1>Answer: <%= request.getAttribute("answer").toString() %> </h1>
<br>

<label id="label2">${description}</label>
<br>
<br>
<!-- <h4><i>More like these:</i> </h4> -->
<label id="label1"></label>
 <a id="demo0"></a>
 <a id="demo1"></a>
 <a id="demo2"></a>
 <a id="demo3"></a>
 <br>
 <!-- <h4><i>Did you mean:</i> </h4> -->
<label id="label2"></label>
 <a id="dym0"></a>

</body>
</html>