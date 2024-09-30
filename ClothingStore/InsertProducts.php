<?php

    include 'connect.php';

    $product_name = $_POST['product_name'];
    $category_id = $_POST['category_id'];
    $color = $_POST['color'];
    $price = $_POST['price'];

    $sql = "INSERT INTO Product (product_name, category_id, color, price) VALUES ('$product_name', '$category_id', '$color', '$price')";

    if($con->query($sql)==TRUE){
        echo"insert thanh cong";
    }
    else{
        echo"loi: ".$con->error;
    }
    $con->close();
?>