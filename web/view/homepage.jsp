<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
    <title>Title</title>
</head>

<body>
<div ng-view></div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-route.js"></script>
<script src="resources/js/app.js"></script>
<script src="resources/js/controller.js"></script>
<script src="resources/js/userController.js"></script>
<ul>
    <li><a href="#/helloPage">Lol</a></li>
    <li><a href="#/user/admin">User</a></li>
</ul>
</body>
</html>
