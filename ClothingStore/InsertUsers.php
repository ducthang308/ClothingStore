<?php
    include 'connect.php';

    // Get user input from POST request
    $fullname = $_POST['fullname'];
    $address = $_POST['address'];
    $phone_number = $_POST['phone_number'];
    $email = $_POST['email'];
    $date_of_birth = $_POST['date_of_birth'];
    $password = $_POST['password'];

    $date_regex = '/^\d{4}-\d{2}-\d{2}$/'; // Pattern for YYYY-MM-DD

    if (!preg_match($date_regex, $date_of_birth)) {
        die("Invalid date format. Please use YYYY-MM-DD.");
    }

    $sql = "INSERT INTO Users (fullname, address, phone_number, email, date_of_birth, password) 
            VALUES ('$fullname', '$address', '$phone_number', '$email', '$date_of_birth', '$password')";

    // Execute the query
    if ($con->query($sql) === TRUE) {
        echo "Insert thành công";
    } else {
        echo "Lỗi: " . $con->error;
    }

    // Close the database connection
    $con->close();
?>
