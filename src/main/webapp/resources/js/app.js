'use strict';

var AngularSpringApp = {};

var app = angular.module('AngularSpringApp', []).run(function ($rootScope, $http) {
    $rootScope.creditIdInArray = 0;

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

    $rootScope.familyCreditor = {
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

    $rootScope.summAmountPersonalRoot = 0;
    $rootScope.summPaidAmountPersonalRoot = 0;
    $rootScope.remainsToPayPersonalRoot = 0;
    $rootScope.totalCreditsPersonalRoot = 0;
    $rootScope.allDebtPersonalRoot = 0;
    $rootScope.totalPaidCreditsPersonalRoot = 0;
    var getPersonalCreditsURL = 'personalCredit/getPersonalCredits';
    $rootScope.fetchCreditListPersonalRoot = function () {
        $http.get(getPersonalCreditsURL).success(function (creditList) {
            $rootScope.personalCreditRoot = creditList;
            for (var i = 0; i < $rootScope.personalCreditRoot.length; i++) {
                $rootScope.summAmountPersonalRoot += $rootScope.personalCreditRoot[i].amount;
                $rootScope.summPaidAmountPersonalRoot += $rootScope.personalCreditRoot[i].paidAmount;
                $rootScope.allDebtPersonalRoot += $rootScope.personalCreditRoot[i].debt.amountDebt;
                $rootScope.remainsToPayPersonalRoot += $rootScope.personalCreditRoot[i].remainsToPay;
                if ($rootScope.personalCreditRoot[i].isPaid === "YES") $rootScope.totalPaidCreditsPersonalRoot++;
                if ($rootScope.personalCreditRoot[i].isCommodity === false) $rootScope.personalCreditRoot[i].isCommodity = "NO";
                if ($rootScope.personalCreditRoot[i].isCommodity === true) $rootScope.personalCreditRoot[i].isCommodity = "YES";
            }
            $rootScope.totalCreditsPersonalRoot = $rootScope.personalCreditRoot.length;
        });
    };
    $rootScope.fetchCreditListPersonalRoot();

    $rootScope.summAmountFamilyRoot = 0;
    $rootScope.summPaidAmountFamilyRoot = 0;
    $rootScope.remainsToPayFamilyRoot = 0;
    $rootScope.totalCreditsFamilyRoot = 0;
    $rootScope.allDebtFamilyRoot = 0;
    $rootScope.totalPaidCreditsFamilyRoot = 0;
    var getFamilyCreditsURL = 'familyCredit/getFamilyCredits';
    $rootScope.fetchCreditListFamilyRoot = function () {
        $http.get(getFamilyCreditsURL).success(function (creditList) {
            $rootScope.familyCreditRoot = creditList;
            for (var i = 0; i < $rootScope.familyCreditRoot.length; i++) {
                $rootScope.summAmountFamilyRoot += $rootScope.familyCreditRoot[i].amount;
                $rootScope.summPaidAmountFamilyRoot += $rootScope.familyCreditRoot[i].paidAmount;
                $rootScope.allDebtFamilyRoot += $rootScope.familyCreditRoot[i].debt.amountDebt;
                $rootScope.remainsToPayFamilyRoot += $rootScope.familyCreditRoot[i].remainsToPay;
                if ($rootScope.familyCreditRoot[i].isPaid === "YES") $rootScope.totalPaidCreditsFamilyRoot++;
                if ($rootScope.familyCreditRoot[i].isCommodity === false) $rootScope.familyCreditRoot[i].isCommodity = "NO";
                if ($rootScope.familyCreditRoot[i].isCommodity === true) $rootScope.familyCreditRoot[i].isCommodity = "YES";
            }
            $rootScope.totalCreditsFamilyRoot = $rootScope.familyCreditRoot.length;
        });
    };
    $rootScope.fetchCreditListFamilyRoot();

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

