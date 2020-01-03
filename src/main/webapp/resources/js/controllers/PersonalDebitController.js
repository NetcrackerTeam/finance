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

    $scope.summ = {};

    $scope.predict = function(summ){
        $http.get('prediction/2/predict', summ).success(function() {
            $scope.summ.id = '';
            $scope.summ.duration = '';
            $scope.summ.type = '';
        }).error(function() {
            alert('Wrong prediction parameters');
        });
    };

};

