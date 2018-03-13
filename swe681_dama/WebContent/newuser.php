<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>
</head>
<body>

<?php
$servername = "dama.cc0rojk8d4jm.us-east-1.rds.amazonaws.com";
$username = "swe681";
$password = "SWEpass123";
$dbname = "DAMA";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// prepare and bind
$stmt = $conn->prepare("INSERT INTO Users (email, username, password) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $firstname, $lastname, $email);

if ( isset( $_POST['submit'] ) ) { 
    $email = $_POST['email'];
    $username = $_POST['username'];
    $password = $_POST['password1'];
    echo '<h3>Form POST Method</h3>'; 
    echo 'Your email is ' . $email . ' ' . $username; exit; 
} 

$stmt->execute();

echo "New records created successfully";

$stmt->close();
$conn->close();
?>
</body>
</html>