<!DOCTYPE html>
<!--<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Q and A System</title>
		<script src="./prototype.js" type="text/javascript">
		</script>
		<script src="./scriptaculous.js?load=effects,controls"
			type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
				href="autocomplete.css" />
	</head>
	<body>
		<form action="FirstServlet">
		<h1>Enter your Question</h1>
		<br>
		<input name="question" type="text" id="search"/>
		<div id="update" class="autocomplete"/>
		<script type="text/javascript">
				new Ajax.Autocompleter('search','update',
				'/solr/people/type-ahead',
				{paramName: "q",method:"get"});
		</script>
		<input type="submit"/>
		</form>
	</body>
</html>-->

<html>
<head>
<script src="./prototype.js" type="text/javascript">
</script>
<script src="./scriptaculous.js?load=effects,controls"
type="text/javascript">
</script>
<link rel="stylesheet" type="text/css"
href="autocomplete.css" />
</head>
<body>
<input type="text" id="search"
name="autocomplete_parameter"/>
<div id="update" class="autocomplete"/>
<script type="text/javascript">
new Ajax.Autocompleter('search','update',
'/solr/people/type-ahead',
{paramName: "q",method:"get"});
</script>
</body>
</html>