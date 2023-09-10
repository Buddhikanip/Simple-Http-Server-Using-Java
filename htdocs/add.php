<html>

<head>
    <title>Add</title>
</head>

<body>
    <!-- Here we go... -->
    <?php

    parse_str(implode('&', array_slice($argv, 1)), $_GET);
    // print_r($_GET);

    if (isset($_POST['n1']) && isset($_POST['n2'])) {
        $n1 = $_POST['n1'];
        $n2 = $_POST['n2'];
        $sum = $n1 + $n2;
        echo `Sum = $sum`;
    } else {
        if (isset($_GET['n1']) && isset($_GET['n2'])) {
            $n1 = $_GET['n1'];
            $n2 = $_GET['n2'];
            $sum = $n1 + $n2;
            echo "Sum = $sum";
        }
    }
    ?>
</body>

</html>