'use strict';

/**
 * PersonalDebitController
 * @constructor
 */
var PersonalDebitController = function($scope, $http) {
    $scope.fetchPersonalAccount = function() {
        $http.get('debitPersonal/layout').success(function(personalAccount){
            $scope.personalAccount = personalAccount;
        });
    };
    $scope.fetchPersonalHistory = function(){
        $http.get('debitPersonal/history').success(function(personalHistory) {
            $scope.personalHistory = personalHistory;
        });
    };
    $scope.fetchCreateCredit = function(){
        $http.get('debitPersonal/createCredit').success(function(createCredit) {
            $scope.createCredit = createCredit;
        });
    };

    $scope.fetchPersonalAccount();
    $scope.fetchPersonalHistory();
    $scope.fetchCreateCredit();
};