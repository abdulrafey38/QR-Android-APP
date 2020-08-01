<?php

$host='localhost';
$username='root';
$pwd='';
$db='fyp_qr(1)';
$student_id=$_GET['student_id'];

$con=mysqli_connect($host,$username,$pwd,$db);

if(!$con)
{
	die("error in connection".mysqli_connect_error());
}
$response =array();
$sql_query="select name from courses join section_courses on courses.id =section_courses.course_id join student_sections on section_courses.id = student_sections.section_id where student_id='$student_id'";
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