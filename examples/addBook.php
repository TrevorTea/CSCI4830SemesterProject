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

	$addBook = "insert into books (title, author, genre, isbn, summary)
		values (\"" . $_POST['title'] . "\",\"" . $_POST['author'] . "\",\"" . $_POST['genre'] . "\",\"" . $_POST['isbn'] . "\",\"" . $_POST['summary'] . "\")";
#	echo $addBook;

	if($conn->query($addBook) === TRUE) {
		echo "Book added";
	} else {
		echo "Error: " . $addBook . "<br>" . $conn->error;
	}
	echo "<br>"; 

$conn->close();
?>
