/**
 * Created by kivi on 15.12.17.
 */

function LoginController($scope, $http) {
    function url() {
        return
    }
    this.signIn = function () {
        if (!$scope.login) {
            alert("Enter login");
        } else if (!$scope.passwd) {
            alert("Enter password");
        } else {
            $http.get('rest/' + $scope.login + '/authenticate?passwd=' + $scope.passwd)
                .then(function (responce) {
                    if (responce) {
                        window.location.href = '#/user/' + $scope.login;
                    } else {
                        alert("Incorrect login or password");
                    }
                });
        }
    };
}

app.controller('LoginController', LoginController);

