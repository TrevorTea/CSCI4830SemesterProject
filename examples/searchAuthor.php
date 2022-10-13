<?php
	$servername = "localhost";
	$username = "trevorthomas";
	$password = "pass";

	$conn = new mysqli($servername, $username, $password);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
	echo "Connected successfully" . "<br>";

	$selectDB = "use myDB";
	if($conn->query($selectDB) === TRUE) {
		echo "Connected to database" . "<br>";
	} else {
		echo "Error connecting to DB" . $conn->error . "<br>";
	}
    	$selectAuth = "select * from books where author like \"" . $_POST['author'] . "\"";
				
#	$result = conn->query($selectAuth);
#	echo $selectAuth;
	echo "<br>";
	$result = $conn->query($selectAuth);
	if ($result->num_rows > 0) {
   		while($row = $result->fetch_assoc()) {
			echo "Title: " . $row["title"]. "<br>Author: " . $row["author"]. "<br>Genre: " . $row["genre"]. "<br>ISBN: " . $row["isbn"] . "<br>Summary: " . $row["summary"];
			echo "<br>";
       		}
        } else {
		echo "0 results";
	}
			#	if($conn->query($addBook) === TRUE) {
			#	#		echo "Book added";
			#	#	} else {
			#	#		echo "Error: " . $addBook . "<br>" . $conn->error;
			#	#	}
			#	#	echo "<br>"; 
			#
	$conn->close();
?>
			
