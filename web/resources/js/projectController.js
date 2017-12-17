/**
 * Created by kivi on 15.12.17.
 */

function ProjectService($resource) {
    return $resource('rest/project/:name', {name: '@name'});
}

function ProjectReportService($resource) {
    return $resource('rest/project/:name/reports', {name: '@name'});
}

function ProjectMilestoneService($resource) {
    return $resource('rest/project/:name/milestones?user=:login', {name: '@name', login:'@login'});
}

function ProjectController($scope, $routeParams, UserShareService, ProjectService, ProjectReportService, ProjectMilestoneService) {
    var url = function () {
        return {name:$routeParams.projectName};
    };
    var add_milestone_url = function (login) {
        return {name:$routeParams.projectName, login: login};
    };

    this.instance = ProjectService.get(url());
    this.reports = ProjectReportService.query(url());
    this.milestones = ProjectMilestoneService.query(url());
    this.user = UserShareService.getUser();

    this.addReport = function () {
        if (isEmpty($scope.reportDesc)) {
            alert("Empty report message");
        } else {
            var report = new ProjectReportService();
            report.creator = this.user;
            report.description = $scope.reportDesc;
            report.$save(url(), function () {
                $scope.reportDesc = "";
                this.updateReports();
            }.bind(this));
        }
    }.bind(this);

    this.addMilestone = function () {
        var milestone = new ProjectMilestoneService();
        milestone.startingDate = $scope.startTime.getTime();
        milestone.endingDate = $scope.endTime.getTime();
        milestone.$save(add_milestone_url(this.user.login), function () {
            this.updateMilestones();
        }.bind(this));
    }.bind(this);

    this.updateReports = function () {
        this.reports = ProjectReportService.query(url());
    };

    this.updateMilestones = function () {
        this.milestones = ProjectMilestoneService.query(url());
    };
}

app.factory('ProjectService', ProjectService)
    .factory('ProjectReportService', ProjectReportService)
    .factory('ProjectMilestoneService', ProjectMilestoneService)
    .controller('ProjectController', ProjectController);