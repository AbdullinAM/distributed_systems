var module = angular.module('myapp', ['ngResource'])

module.factory('Message', function ($resource) {
    return $resource(':login/messages', {login: '@login'});
})
.controller('MessageController', function ($scope, Message) {
    var url = function () {
        return {login:$scope.login||'admin'};
    }

    var update = function () {
        $scope.messages = Message.query(url());
    }

    $scope.add = function () {
        var message = new Message();
        message.content = $scope.text;
        message.$save(url(), function () {
            $scope.text = "";
            update();
        })
    }

    update();
})