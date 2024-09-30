<?php
    include 'connect.php';

    if (isset($_GET['id'])) {
        $order_id = $_GET['id'];
    } else {
        die("ID not provided.");
    }

    $stmt = $con->prepare("DELETE FROM Orders WHERE id = ?");
    $stmt->bind_param("i", $order_id);

    if ($stmt->execute() === TRUE) {
        echo "Delete thành công";
    } else {
        echo "Lỗi: " . $con->error;
    }

    $stmt->close();
    $con->close();
?>
