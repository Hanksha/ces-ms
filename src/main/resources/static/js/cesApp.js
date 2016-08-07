(function() {

    var app = angular.module('cesApp', ['ngRoute', 'ui.bootstrap', 'ngAnimate', 'satellizer', 'ipsum']);

    app.controller('MainCtrl', function($scope) {

    });

    app.controller('HomeCtrl', function ($scope) {
        $scope.name = 'vivien';
    });

    app.controller('MemberCtrl', function ($scope, $routeParams) {
        $scope.params = $routeParams;
    });

    app.factory('MemberService', function($http) {

        return {

        };

        function findAll() {
            return $http.get('/member/list');
        }

        function findOne(id) {
            return $http.get('/member/' + id + '/get');
        }

    });

    app.directive('navBar', function() {
        return {
            restrict: 'E',
            templateUrl: 'js/directives/nav-bar.html'
        }
    });

    app.directive('sideBar', function() {
        return {
            restrict: 'E',
            templateUrl: 'js/directives/side-bar.html'
        }
    });

    app.config(function($routeProvider, $authProvider) {

        $routeProvider
            .when('/', {
                templateUrl: 'js/views/home.html',
                controller: 'HomeCtrl'
            })
            .when('/member/:id', {
                templateUrl: 'js/views/member.html',
                controller: 'MemberCtrl'
            })
            .otherwise({redirectTo: '/'});

    });

})();