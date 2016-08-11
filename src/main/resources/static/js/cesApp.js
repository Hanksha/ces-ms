(function() {

    var app = angular.module('cesApp', ['ngRoute', 'ui.bootstrap', 'ngAnimate', 'satellizer', 'ipsum']);

    app.controller('MainCtrl', function($scope, $log, $location) {
    });

    app.controller('HomeCtrl', function ($scope, $log, MemberService) {

    });

    app.controller('MemberCtrl', function ($scope, $routeParams, MemberService, ParticipationService, ActivityService) {
        $scope.params = $routeParams;

        MemberService.findOne($scope.params.id).success( function (data) { $scope.member = data } );

        ParticipationService.findAllForMember($scope.params.id).success( function(data) {
            $scope.participations = data;

            angular.forEach($scope.participations, function (value) {
                ParticipationService.findInvolvements(value.id).success(function(data) {
                    value.involvements = data;
                });

                ActivityService.findOne(value.activityId).success(function(data) {
                    value.activity = data;
                })
            });
        } );
    });

    app.factory('MemberService', function($http) {

        return {
            findAll: findAll,
            findOne: findOne,
            deleteOne: deleteOne,
            save: save,
            update: update
        };

        function findAll() {
            return $http.get('api/members');
        }

        function findOne(id) {
            return $http.get('api/members/' + id);
        }

        function deleteOne(id) {
            return $http.delete('api/members/' + id);
        }

        function save(member) {
            return $http.post('api/members', member);
        }

        function update(member) {
            return $http.put('api/members/' + member.id, member);
        }
    });

    app.factory('ParticipationService', function($http) {

        return {
            findOne: findOne,
            findAll: findAll,
            findAllForMember: findAllForMember,
            save: save,
            update: update,
            deleteOne: deleteOne,
            findInvolvements: findInvolvements,
            saveInvolvement: saveInvolvement,
            deleteInvolvement: saveInvolvement
        };

        function findOne(id) {
            return $http.get('api/participations/' + id);
        }

        function findAll() {
            return $http.get('api/participations');
        }

        function findAllForMember(id) {
            return $http.get('api/participations/member/' + id);
        }

        function save(participation) {
            return $http.post('api/participations', participation);
        }

        function update(participation) {
            return $http.put('api/participations/' + participation.id, participation);
        }

        function deleteOne(id) {
            return $http.delete('api/participations/' + id);
        }

        function findInvolvements(id) {
            return $http.get('api/participations/' + id + '/involvements');
        }
        
        function saveInvolvement(involvement) {
            return $http.post('api/participations/' + involvement.participationId + '/involvements', involvement);
        }

        function deleteInvolvement(involvement) {
            return $http.delete('api/participations/' + involvement.participationId + '/involvements/' + involvement.type);
        }

    });

    app.factory('ActivityService', function($http) {

        return {
            findOne: findOne,
            findAll: findAll,
            save: save,
            update: update,
            deleteOne: deleteOne
        };

        function findOne(id) {
            return $http.get('api/activities/' + id);
        }

        function findAll(query) {
            query = query || '';
            return $http.get('api/activities', {params: {q: query}});
        }

        function save(activity) {
            return $http.post('api/activities', activity);
        }

        function update(activity) {
            return $http.put('api/activities/' + activity.id, activity);
        }

        function deleteOne(id) {
            return $http.delete('api/activities/' + id);
        }
    });

    app.factory('InvolvementTypeService', function($http) {

        return {
            findOne: findOne,
            findAll: findAll,
            deleteOne: deleteOne,
            save: save,
            update: update
        };

        function findOne(name) {
            return $http.get('api/involvement-types/' + name);
        }

        function findAll() {
            return $http.get('api/involvement-types');
        }
        
        function deleteOne(name) {
            return $http.delete('api/involvement-types/' + name);
        }

        function save(involvementType) {
            return $http.post('api/involvement-types', involvementType);
        }

        function update(involvementType) {
            return $http.put('api/involvement-types/' + name, involvementType);
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

    app.filter('formatInvolvements', function() {
        return function(involvements) {

            if (!angular.isArray(involvements))
                return '';

            return involvements.map(function(value){return value.type;}).join(', ');
        };
    });

    app.config(function($routeProvider, $authProvider) {

        $routeProvider
            .when('/', {
                templateUrl: 'js/views/home.html',
                controller: 'HomeCtrl'
            })
            .when('/about', {
                templateUrl: 'js/views/about.html'
            })
            .when('/contact', {
                templateUrl: 'js/views/contact.html'
            })
            .when('/member/:id', {
                templateUrl: 'js/views/member.html',
                controller: 'MemberCtrl'
            })
            .otherwise({redirectTo: '/'});

    });

})();