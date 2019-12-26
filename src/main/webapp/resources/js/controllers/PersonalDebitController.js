'use strict';

/**
 * PersonalDebitController
 * @constructor
 */
var PersonalDebitController = function($scope, $http) {
    $scope.credit = {};
    $scope.fetchPersonalHistory = function(date){
            $http.get('debitPersonal/2/history', {params: {date:date.toLocaleString()}}).success(function (response) {
            $scope.personalHistory = response;
        });
    };

    $scope.addCredit = function(credit){
        $http.post('debitPersonal/2/addCredit', credit).success(function() {
            $scope.credit.name = '';
            $scope.credit.amount = '';
            $scope.credit.paidAmount = '';
            $scope.credit.date ='';
        }).error(function() {
            alert('Could not add a new train');
        });
    }
    $scope.fetchPersonalHistory('2000-01-11')
};