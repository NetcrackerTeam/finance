'use strict';

var AngularSpringApp = {};
var rootUserId = '101';
var routeProvider,
app = angular.module('AngularSpringApp', []).run(function ($rootScope, $http) {
    $rootScope.user = {
        debitId: "100"
    };

    $rootScope.fetchUserDebitId = function(){
        $http.get('getUserDebitId').success(function (response) {
            $rootScope.user.debitId = response;
        });
    };
    $rootScope.fetchUserDebitId();

    rootUserId = $rootScope.user.debitId;
});

var way = 'debitPersonal/' + rootUserId + '/createCredit';
// Declare app level module which depends on filters, and services
app.config(['$routeProvider', function ($routeProvider) {
    routeProvider = $routeProvider;
    $routeProvider.when('/personalDebit', {
        templateUrl: 'debitPersonal/layout',
        controller: PersonalDebitController
    }).when('/personalCredit', {
        templateUrl: way,
        controller: 'UserController'
    }).when('/prediction', {
        templateUrl: 'prediction/predict',
        controller: PersonalDebitController
    }).when('/personalReport', {
        templateUrl: 'debitPersonal/getReportView',
        controller: PersonalDebitController
    });

    $routeProvider.otherwise({redirectTo: '/'});
}]);

app.controller('UserController', function($scope, $rootScope, $location){
    var idParam = $rootScope.user.debitId;
    routeProvider.when('/personalCredit', {templateUrl: 'debitPersonal/' + idParam + '/createCredit'});
    $location.path('debitPersonal/' + idParam + '/createCredit');
});

app.controller('AppCtrl', function() {
    this.myDate = new Date();
    this.isOpen = false;
});
