'use strict';

/**
 * PersonalDebitController
 * @constructor
 */
var PersonalDebitController = function($scope, $http) {

    $scope.fetchPersonalHistory = function(date){
        $http.get('debitPersonal/history', {params: {date:date.toLocaleString()}}).success(function (response) {
            $scope.personalHistory = response;
        });
    };

    $scope.fetchPersonalAutoOperationHistory = function () {
        $http.get('debitPersonal/autoOperationHistory').success(function (autoOper) {
            $scope.personalAutoOperationHistory = autoOper;
        });
    };

    $scope.fetchPersonalAutoOperationHistory();
    $scope.fetchPersonalHistory('2019-01-11');

};

