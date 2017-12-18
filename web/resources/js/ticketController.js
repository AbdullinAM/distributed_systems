/**
 * Created by kivi on 18.12.17.
 */

function TicketService($resource) {
    return $resource('rest/ticket/:id', {id: '@id'});
}

function TicketAssigneesService($resource) {
    return $resource('rest/ticket/:id/assignees?user=:login', {id: '@id', login: '@login'});
}

function TicketCommentService($resource) {
    return $resource('rest/ticket/:id/comments?user=:login', {id: '@id', login: '@login'});
}

function TicketController($http, $scope, $routeParams,
                          TicketService,
                          TicketCommentService,
                          TicketAssigneesService, InfoShareService) {
    function url() {
        return {id: $routeParams.id};
    }
    function url_with_login(login) {
        return {id: $routeParams.id, login: login};
    }
    this.user = InfoShareService.getUser();
    this.project = InfoShareService.getProject();
    this.instance = TicketService.get(url());
    this.assignees = TicketAssigneesService.query(url());
    this.comments = TicketCommentService.query(url());

    this.addAssignee = function () {
        var dev = new TicketAssigneesService();
        dev.login = $scope.assigneeLogin;
        dev.$save(url_with_login(this.user.login), function () {
            $scope.assigneeLogin = "";
            this.updateAssignees();
        }.bind(this), function (error) {
            alert(error);
        });
    };

    this.updateAssignees = function () {
        this.assignees = TicketAssigneesService.query(url());
    };

    this.changeStatus = function (status) {
        $http.put('rest/ticket/' + this.instance.id + '?user=' + this.user.login, status)
            .then(function () {
                this.update();
            }.bind(this), function (error) {
                alert(error);
        });
    };

    this.commentTicket = function () {
        if (isEmpty($scope.ticketComment)) {
            alert("Enter comment message");
        } else {
            var comment = new TicketCommentService();
            comment.description = $scope.ticketComment;
            comment.$save(url_with_login(this.user.login), function () {
                $scope.ticketComment = "";
                this.updateComments();
            }.bind(this), function (error) {
                alert(error);
            });
        }
    };

    this.update = function () {
        this.instance = TicketService.get(url());
    }

    this.updateComments = function () {
        this.comments = TicketCommentService.query(url());
    }
}

app
    .factory('TicketService', TicketService)
    .factory('TicketCommentService', TicketCommentService)
    .factory('TicketAssigneesService', TicketAssigneesService)
    .controller('TicketController', TicketController);