<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" isELIgnored="False"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
	<head>
	    <!-- Required meta tags -->
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	
	    <!-- Bootstrap CSS -->
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	
	    <title>Calculator App</title>
	    
	    <style media="screen">
	        .jumbotron {
	            background-color: #284E78;
	            /* background-color: #e84a5f; */
	        }
	        
	        .bg-color {
	            background-color: #faf3dd;
	        }
	        
	        .container-fluid {
	            padding-left: 0px;
	            padding-right: 0px;
	            font-size: clamp(14px, 2vw, 16px);
	            /* font-size: 16px; */
	            background-color: #FEF7DC;
	        }
	        
	        .responsive {
	            /* width: 100%; */
	            height: auto;
	        }
    	</style>
	</head>
	
	<body>
		<div class="container-fluid">
	
	        <!-- Header -->
	        <div class="jumbotron py-1 mb-2 text-center text-white">
	            <h1 class="display-6">Calculator Test Web App</h1>
	        </div>
	
	        <!-- Row 1 -->
	        <div class="row px-4 py-2 d-flex">
				<h4>${a} ${operation} ${b} = ${answer}</h4>
			</div>
		</div>
	</body>

</html>