<html>

<head>
    <title>Binary Calculator</title>

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.1/mdb.min.css" rel="stylesheet" />
</head>

<body>
    <section class="vh-100">
        <div class="container py-5">
            <h1>Simple Binary Calculator</h1>
            <div class="row">
                <div class="col-xl-4 col-md-6 col-lg-4 col-sm-8 col-8 m-5">
                    <?php
                    // print_r($_GET);                    
                    if (isset($_POST['n1']) && isset($_POST['n2'])) {
                        $n1 = $_POST['n1'];
                        $n2 = $_POST['n2'];
                        $sum = $n1 + $n2;
                        echo `<div>Number 1 = $n1</div>
                <div>Number 2 = $n2</div>
                <h3 class="pt-4">Sum = $sum</h3>`;
                    } else if (isset($_GET['n1']) && isset($_GET['n2'])) {
                        $n1 = $_GET['n1'];
                        $n2 = $_GET['n2'];
                        $sum = $n1 + $n2;
                        echo "<div>Number 1 = $n1</div>
                <div>Number 2 = $n2</div>
                <h3 class='pt-4'>Sum = $sum</h3>";
                    }
                    ?>
                </div>
            </div>
        </div>
    </section>

    <!-- MDB -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.1/mdb.min.js"></script>
</body>

</html>