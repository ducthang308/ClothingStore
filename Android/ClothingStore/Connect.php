<?php
    $host = "localhost";
    $user = "root";
    $pass = "";
    $database = "clothingstore";

    $con = mysqli_connect($host,$user,$pass,$database);

    mysqli_set_charset($con, 'utf8');
?>