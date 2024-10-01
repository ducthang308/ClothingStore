<?php
    include 'connect.php';

    header('Content-Type: application/json');

    $sql = "SELECT * FROM Orders";
    $result = $con->query($sql);

    //Mảng này sẽ lưu trữ tất cả các hàng được lấy từ cơ sở dữ liệu.
    $orders = array();

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
