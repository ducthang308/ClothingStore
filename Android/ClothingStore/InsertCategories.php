<?php

    include 'connect.php';

    $category_name = $_POST['category_name'];
    $sql = "Insert into Categories (category_name) VALUES ('$category_name')";

    if($con->query($sql)==TRUE){
        echo"Insert successfully";
    }
    else{
        echo"Error: ".$con->error;
    }
    $con->close();
?>