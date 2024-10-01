<?php
    include 'connect.php';

    header('Content-Type: application/json');

    if (isset($_GET['id'])) {
        $user_id = $_GET['id'];
    } else {
        die("ID not provided.");
    }

    $sql = "SELECT * FROM Orders WHERE user_id = ?";
    $stmt = $con->prepare($sql);
    $stmt->bind_param("i", $user_id); // 'i' đại diện cho integer

    // Thực thi câu truy vấn
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        //Đọc từng dòng
        while ($row = $result->fetch_assoc()) {
            $orders[] = $row;
        }
        echo json_encode($orders);
    } else {
        echo json_encode([]);
    }

    $con->close();
?>
