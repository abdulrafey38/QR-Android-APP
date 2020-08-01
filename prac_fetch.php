<?php

$host='localhost';
$username='root';
$pwd='';
$db='fyp_qr(1)';
$email=$_GET['email'];

$con=mysqli_connect($host,$username,$pwd,$db);

if(!$con)
{
	die("error in connection".mysqli_connect_error());
}
$response =array();
$sql_query="select name,id,S_UID from users where S_UID='$email'";
//$sql_query ="select name from users";
$result = mysqli_query($con,$sql_query);

if(mysqli_num_rows($result)>0)
{
	while($row=mysqli_fetch_assoc($result))
	{
		array_push($response,$row);
	}
}
else
{
	$response['success']=0;
	$response['message']='no data';
}

echo json_encode($response);
mysqli_close($con);

?>