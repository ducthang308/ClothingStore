<?php

    include 'connect.php';

    if (isset($_GET['id'])) {
        $user_id = $_GET['id'];
    } else {
        die("ID not provided.");
    }

    $data = json_decode(file_get_contents("php://input"), true);

    if (isset($data['password'])) {
        $password = $data['password'];
    } else {
        die("Category name not provided.");
    }

    $sql = "UPDATE Users SET password = '$password' WHERE id = '$user_id'";

    if ($con->query($sql) === TRUE) {
        echo "Update thành công";
    } else {
        echo "Lỗi: " . $con->error;
    }

    $con->close();
?>
