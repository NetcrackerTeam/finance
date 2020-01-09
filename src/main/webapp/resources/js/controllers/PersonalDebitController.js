'use strict';

/**
 * PersonalDebitController
 * @constructor
 */
var PersonalDebitController = function($scope, $http) {

    $scope.fetchPersonalHistory = function(){
        $http.get('debitPersonal/history', {params: { period: 1}}).success(function (response) {
            $scope.personalHistory = response;
        });
    };

    $scope.fetchPersonalAutoOperationHistory = function () {
        $http.get('debitPersonal/autoOperationHistory').success(function (autoOper) {
            $scope.personalAutoOperationHistory = autoOper;
        });
    };

    $scope.fetchPersonalInfo = function () {
        $http.get('debitPersonal/info').success(function (accountInfo) {
            $scope.accountInfo = accountInfo;
        });
    };
    $scope.fetchPersonalInfo();

    $scope.fetchUserInfo = function() {
        $http.get('getUserInfo').success(function (userInfo) {
            $scope.userInfo = userInfo;
        });
    };

    $scope.fetchPersonalAutoOperationHistory();
    $scope.fetchPersonalHistory();
    $scope.fetchPersonalInfo();
    $scope.fetchUserInfo();
};