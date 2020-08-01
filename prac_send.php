<?php



$host='localhost';
$username='root';
$pwd='';
$db='fyp_qr(1)';

$con=mysqli_connect($host,$username,$pwd,$db); 


date_default_timezone_set('Asia/Karachi'); 
// $price = $_POST['price'];
// $name = $_POST['name'];


$student_id=$_POST['student_id'];
$course_id=$_POST['course_id'];
$section_id=$_POST['section_id'];
$attendance=$_POST['attendance'];
$dates=$_POST['dates'];
$time_qr=$_POST['time_qr'];

// $disp = _POST['disp'];
// $str_arr = explode(",", $disp);

// $student_id=$str_arr[4];
// $course_id=$str_arr[2];
// $section_id=$str_arr[1];
// $attendance=$str_arr[3];
// $date=$str_arr[0];


$startTime = $time_qr;
$endTime = strtotime("+30 second", strtotime($startTime));



$Sql_duplicate = "Select student_id,course_id,section_id,attendance,date from attendances where date = '$dates' and student_id = '$student_id' and course_id ='$course_id' and section_id ='$section_id'

                    and attendance = '$attendance'";
$res = mysqli_query($con,$Sql_duplicate);

$Sql_Query = "INSERT  INTO attendances (student_id,course_id,section_id,attendance,date) VALUES ('$student_id','$course_id','$section_id','$attendance','$dates')";


//$Sql_Query="INSERT INTO data values ('".$price."','".$name."');";
//$query="INSERT INTO data (price,name) VALUES ('".$_POST["price"]."','".$_POST["name"]."')";

// $Sql_Query = "INSERT INTO data (price,name) VALUES ('$price','$name')";


if(Date("H:i:s") <= date('H:i:s',$endTime))
{


if($res->num_rows>0)
{
 echo "Attendance Already Marked!!";
}

else {


 if (mysqli_query($con, $Sql_Query)) {

  echo 'Attendance Marked';

 } else {

  echo 'Try Again';

 }

}
}
else
{
	echo "QR Code Has Been Expired.";
}

 mysqli_close($con);

// $date=$_POST['date'];
// $section_id=$_POST['section_id'];
// $course_id=$_POST['course_id'];
// $attendance=$_POST['attendance'];
// $student_id=$_POST['student_id'];

// $Sql_Query = "insert into attendances (student_id,course_id,section_id,attendance,date) values ('$student_id','$course_id','$section_id','$attendance','$date')";

?>

