'use strict';

var AngularSpringApp = {};

var app = angular.module('AngularSpringApp', []).run(function ($rootScope, $http) {
    $rootScope.personalCreditPayment = {
        amount: 10
    };

    $rootScope.personalCreditor = {
        name: "",
        amount: 0,
        paidAmount: 0,
        date: {},
        creditRate: {},
        dateTo: "",
        monthDay: "",
        isPaid: "NO",
        isCommodity: "false"
    };

    $rootScope.optionSelect = {
        idCredit: "01"
    };

    $rootScope.user = {
        debitId: "100",
        id: "",
        familyDebitId: "",
        role: ''
    };

    $rootScope.gottenPersonalCredit = "";

    $rootScope.fetchUserId = function(){
        $http.get('getUserId').success(function (response) {
            $rootScope.user.id = response;
        });
    };

    $rootScope.fetchUserId();

    $rootScope.fetchUserInfo = function() {
        $http.get('getUserInfo').success(function (userInfo) {
            $rootScope.userInfo = userInfo;
        });
    };

    $rootScope.fetchUserInfo();

    $rootScope.fetchUserDebitId = function(){
        $http.get('getUserDebitId').success(function (response) {
            $rootScope.user.debitId = response;
        });
    };

    $rootScope.fetchUserDebitId();

    $rootScope.fetchFamilyDebitId = function () {
      $http.get('getFamilyDebitId').success(function (response) {
          $rootScope.user.familyDebitId = response;
      });
    };

    $rootScope.fetchFamilyDebitId();

    $rootScope.fetchUserRole = function(){
        $http.get('getUserRole').success(function (response) {
            $rootScope.user.role = response;
        });
    };

    $rootScope.fetchUserRole();

});

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'debitPersonal/layout',
        controller: PersonalDebitController
    }).when('/prediction', {
        templateUrl: 'prediction/predict',
        controller: PersonalDebitController
    }).when('/personalReport', {
        templateUrl: 'debitPersonal/getReportView',
        controller: PersonalDebitController
    });
    $routeProvider.when('/familyAccount', {
        templateUrl: 'debitFamily/layout',
        controller: FamilyDebitController
    }).when('/userControl', {
        templateUrl: 'debitFamily/getUserControl',
        controller: FamilyDebitController
    });

 //   $routeProvider.otherwise({redirectTo: '/'});
}]);

app.controller('AppCtrl', function() {
    this.myDate = new Date();
    this.isOpen = false;
});

