<?php

    include 'connect.php';

    if (isset($_GET['id'])) {
        $category_id = $_GET['id'];
    } else {
        die("ID not provided.");
    }

    $stmt = $con->prepare("DELETE FROM Categories WHERE id = ?");
    $stmt->bind_param("i", $category_id);

    if ($stmt->execute() === TRUE) {
        echo "Delete thành công";
    } else {
        echo "Lỗi: " . $con->error;
    }

    $stmt->close();
    $con->close();
?>
