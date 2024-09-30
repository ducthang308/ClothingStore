<?php
    include 'connect.php';

    $user_id = $_POST['user_id'];
    $total_money = $_POST['total_money'];
    $note = $_POST['note'];
    $order_date = $_POST['order_date'];
    $payment_method = $_POST['payment_method'];

    $date_regex = '/^\d{4}-\d{2}-\d{2}$/';

    if (!preg_match($date_regex, $order_date)) {
        die("Invalid date format. Please use YYYY-MM-DD.");
    }

    $sql = "INSERT INTO Orders (user_id, total_money, note, order_date, payment_method) 
            VALUES ('$user_id', '$total_money', '$note','$order_date', '$payment_method')";

    if ($con->query($sql) === TRUE) {
        echo "Insert successfully";
    } else {
        echo "Error: " . $con->error;
    }

    $con->close();
?>
