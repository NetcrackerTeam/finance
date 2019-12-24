'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', []);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/personalDebit', {
        templateUrl: 'debitPersonal/layout',
        controller: PersonalDebitController
    });

    $routeProvider.otherwise({redirectTo: '/'});
}]);
