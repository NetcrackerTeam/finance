'use strict';

var AngularSpringApp = {};

var app = angular.module('AngularSpringApp', []);

// Declare app level module which depends on filters, and services
app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/personalDebit', {
        templateUrl: 'debitPersonal/2/layout',
        controller: PersonalDebitController
    }).when('/personalCredit', {
        templateUrl: 'debitPersonal/2/createCredit',
        controller: PersonalDebitController
    }).when('/modalDialog', {
        templateUrl : 'debitPersonal/2/modalDialog',
        controller: PersonalDebitController
    });

    $routeProvider.otherwise({redirectTo: '/'});
}]);
