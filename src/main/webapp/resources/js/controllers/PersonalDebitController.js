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

    $scope.createCredit = function(credit){
        $http.get('debitPersonal/2/createCredit', credit).success(function() {
            $scope.credit.name = '';
            $scope.credit.amount = '';
            $scope.credit.paidAmount = '';
            $scope.credit.date ='';
        }).error(function() {
            alert('Could not add a new train');
        });
    };

    $scope.showModalDialog = function() {
        var params = 'scrollbars=no,resizable=no,status=no,location=no,toolbar=no,menubar=no,' +
            'width=800,height=400,left=100,top=100';
        window.open("#/modalDialog", "Modal Window", params);
    };

    $scope.showPrompt = function(){
        var result = window.prompt("Napishi cheto umnoje");
        alert(result);
    };
    $scope.closeModalDialog = function() {
        window.close();
    };
    $scope.okModalDialog = function() {
        alert('Button OK');
        $scope.closeModalDialog();
    };
    $scope.msg = "I love nc";
    $scope.headerText = "New Window";
    $scope.bodyText = "Hello NCTeam";
    $scope.closeButtonText = "Close me, plz";
    $scope.actionButtonText = "PRESS ME";

    $scope.fetchPersonalHistory('2000-01-11')

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

