'use strict';

/**
 * PersonalDebitController
 * @constructor
 */
var PersonalDebitController = function($scope, $http) {
    $scope.fetchPersonalHistory = function(date){
            $http.get('debitPersonal/2/history', {params: {date:date.toLocaleString()}}).success(function (response) {
            $scope.personalHistory = response;
        });
    };

    $scope.credit = {};
    $scope.addCredit = function(credit){
        $scope.credit.name = credit.name;
        var creditName = this.credit.name;
        alert('Credit name is "' + creditName + '"');
        /*$http.get('debitPersonal/2/createCredit', credit).success(function() {
            $scope.credit.name = credit.name;
            creditName.innerHTML = this.credit.name;
            alert('Credit name is "' + creditName + '"');
            $scope.credit.amount = '';
            $scope.credit.paidAmount = '';
            $scope.credit.dateFrom = '';
            $scope.credit.dateTo = '';
            $scope.credit.rate = '';
            $scope.credit.isPaid = 'NO';
            $scope.credit.dayOfMonth = '';
        }).error(function() {
            alert('Could not add a new train');
        });*/
    };

    var slider = document.getElementById("creditRateRange");
    var output = document.getElementById("creditRateText");
    output.innerHTML = 'Credit rate: ' + slider.value + ' %';
    slider.oninput = function() {
        output.innerHTML = 'Credit rate: ' + this.value + ' %';
    };

    $scope.fetchPersonalHistory('2000-01-11');

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

