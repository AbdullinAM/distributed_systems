/**
 * Created by kivi on 15.12.17.
 */

function ProjectController($routeParams, UserShareService) {
    this.name = $routeParams.projectName;
    this.user = UserShareService.getUser();
}

app.controller('ProjectController', ProjectController);