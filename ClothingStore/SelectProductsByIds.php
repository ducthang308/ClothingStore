<?php
    include 'connect.php';

    header('Content-Type: application/json');

    // Kiểm tra xem tham số ids có tồn tại không
    if (isset($_GET['ids'])) {
        // Lấy danh sách ids và tách thành mảng
        $ids = explode(',', $_GET['ids']);
        // Chuyển đổi mảng thành các giá trị số nguyên
        $ids = array_map('intval', $ids);
        // Chuyển mảng thành chuỗi tham số cho câu truy vấn
        $placeholders = implode(',', array_fill(0, count($ids), '?'));
    } else {
        die(json_encode(['error' => 'IDs not provided.']));
    }

    $sql = "SELECT * FROM Product WHERE id IN ($placeholders)";
    $stmt = $con->prepare($sql);

    if ($stmt === false) {
        die(json_encode(['error' => 'Error preparing statement: ' . $con->error]));
    }

    // Chuẩn bị kiểu dữ liệu cho bind_param. Tất cả đều là số nguyên nên 'i' được lặp lại cho mỗi id
    $types = str_repeat('i', count($ids));
    // Biến tham chiếu để bind_param sử dụng
    $params = array_merge([$types], $ids);

    // Sử dụng hàm call_user_func_array để truyền tham chiếu các tham số vào bind_param
    $stmt->bind_param(...$params);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $products = [];
        while ($row = $result->fetch_assoc()) {
            $products[] = $row;
        }
        echo json_encode($products);
    } else {
        echo json_encode([]);
    }

    $stmt->close();
    $con->close();
?>
