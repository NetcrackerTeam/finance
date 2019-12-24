'use strict';

/**
 * CarController
 * @constructor
 */
var PersonalDebitController = function($scope, $http) {
    $scope.fetchPersonalAccount = function() {
        $http.get('/debitPersonal/layout').success(function(personalAccount){
            $scope.personalAccount = personalAccount;
        });
    };

    $scope.fetchPersonalAccount();
};