    <?php

        include 'connect.php';

        if (isset($_GET['id'])) {
            $orders_id = $_GET['id'];
        } else {
            die("ID not provided.");
        }

        $data = json_decode(file_get_contents("php://input"), true);

        if (isset($data['status'])) {
            $status = $data['status'];
        } else {
            die("Status not provided.");
        }

        $sql = "UPDATE Orders SET status = '$status' WHERE id = '$orders_id'";

        if ($con->query($sql) === TRUE) {
            echo "Update successful";
        } else {
            echo "Error: " . $con->error;
        }

        $con->close();
    ?>
