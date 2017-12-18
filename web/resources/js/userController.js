/**
 * Created by kivi on 13.12.17.
 */

function UserService($resource) {
    return $resource('rest/user/:login', { login: '@login' });
}

function MessageService($resource) {
    return $resource('rest/user/:login/messages', { login: '@login' });
}

function UserProjectService($resource) {
    return $resource('rest/user/:login/projects?type=:type', {login: '@login', type: '@type'});
}

function UserController($scope, $routeParams, UserService, MessageService, UserProjectService, InfoShareService) {
    var url = function () {
        return {login:$routeParams.login};
    };
    var project_url = function (proj_type) {
        return {login:$routeParams.login, type: proj_type};
    };
    this.isMe = InfoShareService.getUser().login == $routeParams.login;

    this.instance = UserService.get(url());
    this.messages = MessageService.query(url());
    this.managedProjects = UserProjectService.query(project_url("manager"));
    this.leadedProjects = UserProjectService.query(project_url("teamlead"));
    this.developedProjects = UserProjectService.query(project_url("dev"));
    this.testedProjects = UserProjectService.query(project_url("tester"));

    this.updateProjects = function () {
        this.managedProjects = UserProjectService.query(project_url("manager"));
    }.bind(this);

    this.createProject = function () {
        if ($scope.projectName) {
            var project = new UserProjectService(project_url("manager"));
            project.name = $scope.projectName;
            project.$save(url(), function () {
                $scope.projectName = "";
                this.updateProjects();
            }.bind(this), function (error) {
                alert(error);
            });
        } else {
            alert("Enter the name of the project");
        }
    }
}

angular
    .module('app')
    .factory('UserService', UserService)
    .factory('MessageService', MessageService)
    .factory('UserProjectService', UserProjectService)
    .controller('UserController', UserController);