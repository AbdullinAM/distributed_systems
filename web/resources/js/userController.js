/**
 * Created by kivi on 13.12.17.
 */

function UserService($resource) {
    return $resource('rest/:login', { login: '@login' });
}

function MessageService($resource) {
    return $resource('rest/:login/messages', { login: '@login' });
}

function UserProjectService($resource) {
    return $resource('rest/:login/projects?type=:type', {login: '@login', type: '@type'});
}

function UserController($routeParams, UserService, MessageService, UserProjectService) {
    var url = function () {
        return {login:$routeParams.login}
    }
    var project_url = function (type) {
        return {login:$routeParams.login, type: type};
    }

    this.instance = UserService.get(url());
    this.messages = MessageService.query(url());
    this.managedProjects = UserProjectService.query({login:$routeParams.login, type: "manager"});
    this.leadedProjects = UserProjectService.query({login:$routeParams.login, type: "teamlead"});
    this.developedProjects = UserProjectService.query({login:$routeParams.login, type: "dev"});
    this.testedProjects = UserProjectService.query({login:$routeParams.login, type: "tester"});
}

angular
    .module('app')
    .factory('UserService', UserService)
    .factory('MessageService', MessageService)
    .factory('UserProjectService', UserProjectService)
    .controller('UserController', UserController);