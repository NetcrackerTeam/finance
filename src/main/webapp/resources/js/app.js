'use strict';

var AngularSpringApp = {};

var app = angular.module('AngularSpringApp', []).run(function ($rootScope, $http) {
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

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/personalDebit', {
        templateUrl: 'debitPersonal/layout',
        controller: PersonalDebitController
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


app.controller('AppCtrl', function() {
    this.myDate = new Date();
    this.isOpen = false;
});
