/**
 * Created by kivi on 06.12.17.
 */
var app = angular.module('app', ['ngRoute','ngResource']);
app.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when('/user/:login', {
            templateUrl: 'view/userPage.html',
            controller: 'UserController',
            controllerAs: 'user'
        })
        .otherwise(
            { redirectTo: '/'}
        );
}]);
