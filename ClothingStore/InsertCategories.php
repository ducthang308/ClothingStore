<?php

    include 'connect.php';

    $category_name = $_POST['category_name'];
    $sql = "Insert into Categories (category_name) VALUES ('$category_name')";

    if($con->query($sql)==TRUE){
        echo"insert thanh cong";
    }
    else{
        echo"loi: ".$con->error;
    }
    $con->close();
?>