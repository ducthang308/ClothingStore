<?php
    include 'connect.php';

    if (isset($_GET['id'])) {
        $product_id = $_GET['id'];
    } else {
        die("ID not provided.");
    }

    $data = json_decode(file_get_contents("php://input"), true);

    $sql = "UPDATE Product SET ";
    $updates = [];
    
    // Kiểm tra và gán giá trị cho các biến nếu tồn tại
    if (isset($data['product_name']) && !empty($data['product_name'])) {
        $product_name = $data['product_name'];
        $updates[] = "product_name = '$product_name'";
    }
    
    if (isset($data['category_id']) && !empty($data['category_id'])) {
        $category_id = $data['category_id'];
        $updates[] = "category_id = '$category_id'";
    }

    if (isset($data['color']) && !empty($data['color'])) {
        $color = $data['color'];
        $updates[] = "color = '$color'";
    }
    
    if (isset($data['price']) && !empty($data['price'])) {
        $price = $data['price'];
        $updates[] = "price = '$price'";
    }
    
    if (!empty($updates)) {
        // Nối các trường cần cập nhật vào câu lệnh SQL
        $sql .= implode(", ", $updates) . " WHERE id = '$product_id'";
    } else {
        die("No fields to update.");
    }

    if ($con->query($sql) === TRUE) {
        echo "Update successful";
    } else {
        echo "Error: " . $con->error;
    }

    // Đóng kết nối
    $con->close();
?>
