<?php



$servername = "localhost";

// REPLACE with your Database name
$dbname = "data";
// REPLACE with Database user
$username = "root";
// REPLACE with Database user password
$password = "";
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
} 
$sql = "SELECT * FROM alert";

$result = $conn->query($sql);
$row = $result->fetch_assoc();
echo $row["alert_value"];
$conn->close();


?>