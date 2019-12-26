'use strict';

/**
 * PersonalDebitController
 * @constructor
 */
var personalDebitController = function($scope, $http) {
    $scope.fetchPersonalHistory = function(date){
        $http.get('debitPersonal/2/history', {params: {date:date.toLocaleString()}}).success(function(historyList) {
            $scope.personalHistory = historyList;
        });
    };
    $scope.fetchCreateCredit = function(){
        $http.get('debitPersonal/createCredit').success(function(createCredit) {
            $scope.createCredit = createCredit;
        });
    };

    $scope.fetchPersonalHistory('2019-01-11')
    $scope.fetchCreateCredit();
};