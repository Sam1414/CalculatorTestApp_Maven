<!DOCTYPE html>
<html lang="en" xml:lang="en">
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
				<p>Enter two <strong>Integer</strong> values and select the operation to be performed:</p>
				<form action="calculate" method="GET">
					<!-- Name -->
                    <div class="row mb-3">
                        <label for="t1" class="col-sm-2 col-form-label">Number 1:</label>
                        <div class="col-sm-10">
                            <input type="number" step="any" class="form-control" name="t1" placeholder="Enter a Number" required>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="t2" class="col-sm-2 col-form-label">Number 2:</label>
                        <div class="col-sm-10">
                            <input type="number" step="any" class="form-control" name="t2" placeholder="Enter a Number" required>
                        </div>
                    </div>
					
					<p><strong>Operations: </strong></p>
					
					<div class="form-check">
						<input class="form-check-input" type="radio" id="add" name="operation" value="+" required>
						<label class="form-check-label" for="add">Add (+)</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="operation" value="-" required>
						<label class="form-check-label" for="add">Substract (-)</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="operation" value="*" required>
						<label class="form-check-label" for="add">Multiply (*)</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="operation" value="/" required>
						<label class="form-check-label" for="add">Divide (/)</label>
					</div>
					<br>
					<button class="btn btn-primary" type="submit">Calculate</button>
				</form>
			</div>
		</div>
	</body>
</html>
