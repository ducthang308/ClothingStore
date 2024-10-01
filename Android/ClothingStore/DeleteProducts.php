<?php

    include 'connect.php';

    if (isset($_GET['id'])) {
        $product_id = $_GET['id'];
    } else {
        die("ID not provided.");
    }

    $stmt = $con->prepare("DELETE FROM Product WHERE id = ?");
    $stmt->bind_param("i", $product_id);

    if ($stmt->execute() === TRUE) {
        echo "Delete successfully";
    } else {
        echo "Error: " . $con->error;
    }

    $stmt->close();
    $con->close();
?>
