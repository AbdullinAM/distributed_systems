/**
 * Created by kivi on 06.12.17.
 */
var app = angular.module('app', ['ngRoute','ngResource']);
app.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when('/helloPage',{
            templateUrl: 'helloPage',
            controller: 'helloController'
        })
        .otherwise(
            { redirectTo: '/'}
        );
}]);
