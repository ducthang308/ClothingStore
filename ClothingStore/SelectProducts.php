<?php
    include 'connect.php';

    header('Content-Type: application/json');

    $sql = "SELECT * FROM Product";
    $result = $con->query($sql);

    //Mảng này sẽ lưu trữ tất cả các hàng được lấy từ cơ sở dữ liệu.
    $products = array();

    if ($result->num_rows > 0) {
        //Đọc từng dòng
        while ($row = $result->fetch_assoc()) {
            $products[] = $row;
        }
        echo json_encode($products);
    } else {
        echo json_encode([]);
    }

    $con->close();
?>
