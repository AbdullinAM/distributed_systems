/**
 * Created by kivi on 15.12.17.
 */

function ProjectController($routeParams) {
    this.name = $routeParams.projectName
}

app.controller('ProjectController', ProjectController);