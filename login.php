<?php
$con=mysqli_connect("localhost","root","","fyp_qr(1)");

$email = $_POST["email"];
$password = $_POST["password"];

$sql = "SELECT password FROM users WHERE  S_UID = '$email'";


$result = mysqli_query($con,$sql);


while($row=mysqli_fetch_assoc($result))
{
    $hash = $row['password'];
}

if(password_verify($password,$hash)){
    echo "logged in successfully";
}else{
    echo "     No Record Found \nEnter Correct Credentials...";
}
?>