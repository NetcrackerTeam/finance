'use strict';

var AngularSpringApp = {};

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

});

// Declare app level module which depends on filters, and services
app.config(['$routeProvider', function ($routeProvider) {
    routeProvider = $routeProvider;
    $routeProvider.when('/personalDebit/:debitId', {
        templateUrl: 'templateURL',
        controller: 'RouteControllerLayout'
    }).when('/personalCredit/:debitId', {
        templateUrl: 'templateURL',
        controller: 'RouteControllerCreateCredit'
    }).when('/prediction', {
        templateUrl: 'prediction/predict',
        controller: PersonalDebitController
    }).when('/personalReport', {
        templateUrl: 'debitPersonal/getReportView',
        controller: PersonalDebitController
    }).when('/familyAccount', {
        templateUrl: 'debitFamily/layout',
        controller: FamilyDebitController
    });

    $routeProvider.otherwise({redirectTo: '/'});
}]);

app.controller('RouteControllerCreateCredit', function($scope, $routeParams) {
    $scope.templateUrl = 'debitPersonal/' + $routeParams.debitId + '/createCredit';
});

app.controller('RouteControllerLayout', function ($scope, $routeParams) {
   $scope.templateUrl = 'debitPersonal/' + $routeParams.debitId + '/layout';
});

app.controller('AppCtrl', function() {
    this.myDate = new Date();
    this.isOpen = false;
});
