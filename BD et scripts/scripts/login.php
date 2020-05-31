<?php
	
$db = new mysqli("localhost", "root", "", "dawini_database");

$login = $_POST["USER_LOGIN_TAG"];
$password = $_POST["USER_PASSWORD_TAG"];

$query = $db->query("SELECT login, password FROM users WHERE login = '$login' AND password = '$password'");

$userList = [];

while($currentUser = $query->fetch_assoc())
	$userList['User'][] = $currentUser;

echo json_encode($userList);

mysqli_close($db);
?>