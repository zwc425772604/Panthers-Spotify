<?php
$hostname="mysql2.cs.stonybrook.edu";
$username="panthers";
$password="changeit";
$dbname="panthers";

$connection = mysqli_connect($hostname,$username, $password, $dbname) or die("Error " . mysqli_error($connection));


echo "test ok";
?>
