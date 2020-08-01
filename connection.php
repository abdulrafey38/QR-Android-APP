<?php

$db = "fyp_qr(1)";
$user = $_POST["user"]; 
$pass = $_POST["pass"];
$host = "localhost";


$conn = mysqli_connect($host,"root","",$db);
if($conn)
{
	
	$q = "select * from users where email like '$user' and password like '$pass'";
	$result = mysqli_query($conn,$q);

	if(mysqli_num_rows($result)>0)
	{
		$data = ['loginmsg' =>  'Login Success...','status'=> true];
		header('Content-type: application/json');
		echo json_encode($data);
		echo "Log IN Successfull";
		
	}
	else
	{

		$data = ['loginmsg' =>  'Login Failed...','status'=> false];
		header('Content-type: application/json');
		echo json_encode($data);
		echo "Login Failed......";
	}
}

else
{
		echo "Not Connected";
}