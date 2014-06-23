<head>
    <title>Aliada</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Script-Type" content="text/javascript; charset=UTF-8"></meta>
    <link rel="stylesheet" href="css/styles.jsp" type="text/css"></link>	
  </head>  
  <body class="whitebackground">	
	<div id="aliadaHeader"><img src="images/aliada-header.png"/>	</div>		
	<div id="headerContentGreenBorder"></div>
	<div id="pageTitle">Configure your institution</div>
	<div id="form" >
		<div id="content" style="float:left;">
				<div id="row" class="input">
					<label class="label">Institution name:</label>
					<input type="text" class="inputPage"  value="" size="15">
				</div>
				<div id="row" class="input">
					<label class="label">Files location (server details):</label>
					<input type="text" class="inputPage"  value="" size="15">
				</div>
				<div id="row" class="input">
					<label class="label">URI format -- domain:</label>
					<input type="text" class="inputPage"  value="" size="15">
				</div>				
				<div id="row" class="input">
					<label class="label">URI format -- resource identification:</label>
					<input type="text" class="inputPage"  value="" size="15">
				</div>
				<div id="row" class="input">
					<label class="label">Logo:</label>
					<div id="fileBrowser" style="float:right">
						<input type="text" class="inputPageFile"  value="" size="15">
						<input id="usersButton" class="pageButton" type="submit" value="Browse"></input>
					</div>			
				</div>
				<div id="row" class="input">
					<label class="label">OPAC/catalogue URL:</label><input type="text" class="inputPage"  value="" size="15">			
				</div>
		</div>
		<div id="menu">
			<div class="sepButton"><input id="configureButton" class="menuButton" type="submit"  value="Configure your institution"></input></div>
			<div class="sepButton"><input id="addButton" class="menuButton" type="submit"  value="Add Files to publish in the LDC"></input></div>
			<div class="sepButton"><input id="updateButton" class="menuButton" type="submit"  value="Update published dataset"></input></div>
		</div>	
	</div>
	<div id="row" class="buttons" >
		<input id="usersButton" class="submitButton" type="submit" value="Users"></input>
		<input id="saveButton" class="submitButton" type="submit" value="Save"></input>
		<input id="backButton" class="submitButton" type="submit" value="Back"></input>
	</div>
	<div id="headerContentGreenBorder"></div>	
<div class="copyrightPage">Copyright © 2014 Aliada Consortium</div>
	</body>
</html>

