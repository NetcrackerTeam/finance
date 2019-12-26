'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', []);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/personalDebit', {
        templateUrl: 'debitPersonal/2/layout',
        controller: PersonalDebitController
    }).when('/createCredit', {
        templateUrl: 'debitPersonal/createCredit',
        controller: PersonalDebitController
    });


    $routeProvider.otherwise({redirectTo: '/'});
}]);
