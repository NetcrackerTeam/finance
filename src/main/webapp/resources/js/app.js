'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['AngularSpringApp.reqires']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/personalDebit', {
        templateUrl: '/debit/personal/layout',
        controller: PersonalDebitController
    });

    $routeProvider.otherwise({redirectTo: '/'});
}]);
