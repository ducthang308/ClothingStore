<?php
    include 'connect.php';

    header('Content-Type: application/json');

    if (isset($_GET['id'])) {
        $product_id = $_GET['id'];
    } else {
        die("ID not provided.");
    }

    $sql = "SELECT * FROM Product WHERE id = ?";
    $stmt = $con->prepare($sql);
    $stmt->bind_param("i", $product_id); // 'i' đại diện cho integer

    // Thực thi câu truy vấn
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        //Đọc từng dòng
        while ($row = $result->fetch_assoc()) {
            $product[] = $row;
        }
        echo json_encode($product);
    } else {
        echo json_encode([]);
    }

    $con->close();
?>
