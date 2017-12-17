/**
 * Created by kivi on 06.12.17.
 */
var app = angular.module('app', ['ngRoute','ngResource']);
app.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when('/', {
            redirectTo: '/login'
        })
        .when('/login', {
            templateUrl: 'view/loginPage.html',
            controller: 'LoginController',
            controllerAs: 'loginCtrl'
        })
        .when('/user/:login', {
            templateUrl: 'view/userPage.html',
            controller: 'UserController',
            controllerAs: 'user'
        })
        .when('/project/:projectName', {
            templateUrl: 'view/projectPage.html',
            controller: 'ProjectController',
            controllerAs: 'project'
        })
        .otherwise(
            { redirectTo: '/'}
        );
}]);
