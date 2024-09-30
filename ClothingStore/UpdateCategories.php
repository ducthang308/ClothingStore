<?php

    include 'connect.php';

    if (isset($_GET['id'])) {
        $category_id = $_GET['id'];
    } else {
        die("ID not provided.");
    }

    $data = json_decode(file_get_contents("php://input"), true);

    if (isset($data['category_name'])) {
        $category_name = $data['category_name'];
    } else {
        die("Category name not provided.");
    }

    $sql = "UPDATE Categories SET category_name = '$category_name' WHERE id = '$category_id'";

    if ($con->query($sql) === TRUE) {
        echo "Update thành công";
    } else {
        echo "Lỗi: " . $con->error;
    }

    $con->close();
?>
