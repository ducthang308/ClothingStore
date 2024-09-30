<?php
    include 'connect.php';

    header('Content-Type: application/json');

    $sql = "SELECT * FROM Categories";
    $result = $con->query($sql);

    //Mảng này sẽ lưu trữ tất cả các hàng được lấy từ cơ sở dữ liệu.
    $categories = array();

    if ($result->num_rows > 0) {
        //Đọc từng dòng
        while ($row = $result->fetch_assoc()) {
            $categories[] = $row;
        }
        echo json_encode($categories);
    } else {
        echo json_encode([]);
    }

    //Cách 2: Không dùng JSON
    // $res = array();

    // if ($result->num_rows > 0) {
    //     $res['categories'] = array();
    //     while ($row = $result->fetch_assoc()) {
    //         $categories = array();
    //         $categories['id'] = $row['id'];
    //         $categories['category_name'] = $row['category_name'];
    //         array_push($res['categories'], $categories); //Đẩy dữ liệu đọc được vào mảng
    //     }
    //     echo json_encode($res);
    // }
    // else{
    //     echo json_encode($res);
    // }

    $con->close();
?>
