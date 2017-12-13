/**
 * Created by kivi on 06.12.17.
 */

app.controller('helloController', function($scope, $http) {
        $http.get('http://localhost:8080/greeting').
        then(function(response) {
            $scope.greeting = response.data;
        });
    });