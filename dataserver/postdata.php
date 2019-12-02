<?php



$servername = "localhost";

// REPLACE with your Database name
$dbname = "data";
// REPLACE with Database user
$username = "root";
// REPLACE with Database user password
$password = "";

// Keep this API Key value to be compatible with the ESP32 code provided in the project page. 
// If you change this value, the ESP32 sketch needs to match
$api_key_value = "tPmAT5Ab3j7F9";

$api_key= $umac = $rmac = $data = $distance = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $api_key = $_POST["api_key"];
    if($api_key == $api_key_value) {
        $umac = $_POST["umac"];
        $rmac = $_POST["rmac"];
		$distance = $_POST["distance"];
        $data = $_POST["data"];
        
        // Create connection
        $conn = new mysqli($servername, $username, $password, $dbname);
        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        } 
        
        $sql = "INSERT INTO alldata (data_id, user_mac_address, router_mac_address,distance,data)VALUES (NULL, '$umac', '$rmac','$distance','$data')";
        
        if ($conn->query($sql) === TRUE) {
            echo "New record created successfully";
        } 
        else {
            echo "Error: " . $sql . "<br>" . $conn->error;
        }
    
        $conn->close();
    }
    else {
        echo "Wrong API Key provided.";
    }

}
else {
    echo "No data posted with HTTP POST.";
}

