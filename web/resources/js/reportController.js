/**
 * Created by kivi on 18.12.17.
 */

function ReportService($resource) {
    return $resource('rest/report/:id', {id: '@id'});
}

function ReportCommentService($resource) {
    return $resource('rest/report/:id/comments', {id: '@id'});
}

function ReportController($scope, $http, $routeParams, ReportService, ReportCommentService, InfoShareService) {
    function url() {
        return {id: $routeParams.id};
    }

    this.user = InfoShareService.getUser();
    this.project = InfoShareService.getProject();
    this.instance = ReportService.get(url());
    this.comments = ReportCommentService.query(url());

    this.changeStatus = function (status) {
        if (status == "OPENED" && isEmpty($scope.reportComment)) {
            alert("Enter the report reopening comment");
        } else {
            $http.put('rest/report/' + this.instance.id + '?user=' + this.user.login, status)
                .then(function () {
                    this.update();
                }.bind(this), function (error) {
                    alert(error);
                });
        }
    }

    this.update = function () {
        this.instance = ReportService.get(url());
        this.comments = ReportCommentService.query(url());
    };
}

app
    .factory('ReportService', ReportService)
    .factory('ReportCommentService', ReportCommentService)
    .controller('ReportController', ReportController);