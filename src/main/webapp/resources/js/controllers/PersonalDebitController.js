'use strict';

/**
 * PersonalDebitController
 * @constructor
 */
var PersonalDebitController = function($scope, $http) {

    $scope.fetchPersonalHistory = function(){
        $http.get('debitPersonal/history').success(function (response) {
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

    $scope.deleteAutoOperation = function (id) {
        var method = "GET";

        var url = 'debitPersonal/deleteAuto';

        $http({
            method: method,
            url: url,
            params: {'id': id},
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function () {
            window.location.reload();
        }).error(function () {
            alert("unsuccess ")
        });
    };

    $scope.fetchPersonalInfo();
    $scope.fetchUserInfo();
};