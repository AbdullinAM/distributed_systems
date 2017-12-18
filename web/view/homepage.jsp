<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
    <title>Title</title>
</head>

<style>
    * {
        box-sizing: border-box;
    }

    body {
        margin: 0;
    }

    /* Create two unequal columns that floats next to each other */
    .column {
        float: left;
        padding: 10px;
        height: 300px; /* Should be removed. Only for demonstration */
    }

    .left {
        width: 75%;
    }

    .right {
        width: 25%;
    }

    .equal {
        width: 50%;
    }

    /* Clear floats after the columns */
    .row:after {
        content: "";
        display: table;
        clear: both;
    }

    li {
        display: inline;
    }
</style>


<body>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-route.js"></script>
<script src="resources/js/app.js"></script>
<script src="resources/js/loginController.js"></script>
<script src="resources/js/userController.js"></script>
<script src="resources/js/projectController.js"></script>
<script src="resources/js/milestoneController.js"></script>
<script src="resources/js/reportController.js"></script>
<script src="resources/js/ticketController.js"></script>

<div ng-view></div>
</body>
</html>
