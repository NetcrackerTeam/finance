'use strict';

/**
 * PersonalDebitController
 * @constructor
 */
var PersonalDebitController = function($scope, $http, $rootScope) {
    //var personalHistoryURL = 'debitPersonal/' + $rootScope.user.debitId + '/history';
    var personalHistoryURL = 'debitPersonal/2/history';
    $scope.fetchPersonalHistory = function(date){
        $http.get(personalHistoryURL, {params: {date:date.toLocaleString()}}).success(function (response) {
            $scope.personalHistory = response;
        });
    };

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

