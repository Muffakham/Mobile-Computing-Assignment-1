<?php $file_path = "./Assignment1/";


$asu_id = $_POST['id'] . "/";

$pass = isset($_POST['id'])  && !empty($_POST['id']);

if(!$pass){
    echo "fail";
    return;
}

$file_path = $file_path . $asu_id;


if(!file_exists($file_path)){
    mkdir($file_path, 0777, true);
}

$file = $file_path . basename($_FILES['uploaded_file']['name']);

if (move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file)) {
    echo "success";
} else {
    echo "fail";
}