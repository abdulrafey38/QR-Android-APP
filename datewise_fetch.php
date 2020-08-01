<?php

$host='localhost';
$username='root';
$pwd='';
$db='fyp_qr(1)';

$course_name=$_GET['course_name'];
$student_id=$_GET['student_id'];

$con=mysqli_connect($host,$username,$pwd,$db);

if(!$con)
{
	die("error in connection".mysqli_connect_error());
}
$response =array();

$course_query="select id from courses where name='$course_name'";


$result_id = mysqli_query($con,$course_query); 

 if(mysqli_num_rows($result_id) > 0 ){

	while( $row = mysqli_fetch_assoc($result_id))
	{ 
    $course_id =  $row['id'];
    }
}



$sql_query="select course_id,attendance,date from attendances 
where course_id = $course_id and student_id = '$student_id'";
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