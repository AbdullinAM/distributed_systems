/**
 * Created by kivi on 13.12.17.
 */

function UserService($resource) {
    return $resource('rest/:login', { login: '@login' });
}

function MessageService($resource) {
    return $resource('rest/:login/messages', { login: '@login' });
}

function UserController($routeParams, UserService, MessageService) {
    var url = function () {
        return {login:$routeParams.login}
    }

    this.instance = UserService.get(url());
    this.messages = MessageService.query(url());
}

angular
    .module('app')
    .factory('UserService', UserService)
    .factory('MessageService', MessageService)
    .controller('UserController', UserController);