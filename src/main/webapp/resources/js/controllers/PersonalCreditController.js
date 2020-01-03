'use strict';
/**
 * PersonalCreditController
 * @constructor
 */
var PersonalCreditController = function($scope, $http, $rootScope) {
    $scope.credits = [];
    $scope.credit = {
        name: "",
        dateFrom: {},
        amount: "",
        paidAmount: "",
        rate: "",
        dateTo: {},
        dayOfMonth: "",
        isPaid: 'NO'
    };

    //var getPersonalCreditsURL = 'personalCredit/getPersonalCredits?debitId=' + $rootScope.user.debitId;
    //var getPersonalCreditsURL = 'personalCredit/getPersonalCredits?debitId=2';
    var getPersonalCreditsURL = 'personalCredit/getPersonalCredits';
    $scope.fetchCreditList= function(){
        $http.get(getPersonalCreditsURL).success(function (creditList) {
            $scope.personalCredit = creditList;
        });
    };
    $scope.fetchCreditList();

    var addCreditURL = 'personalCredit/addCredit';
    $scope.addCredit = function() {
        //$scope.initializeVariables(credit);
        $http({
            method: 'POST',
            url: addCreditURL,
            data: angular.toJson($scope.credit),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    };

    function _refreshPageData() {
        $http({
            method : 'GET',
            url : getPersonalCreditsURL
        }).then(function successCallback(response) {
            $scope.credits = response.data.credits;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    function _success(response) {
        _refreshPageData();
        _clearForm()
    }

    function _error(response) {
        console.log(response.statusText);
    }

    function _clearForm() {
        $scope.credit.name = "";
        $scope.credit.dateFrom = {};
        $scope.credit.amount = "";
        $scope.credit.paidAmount = "";
        $scope.credit.rate = "";
        $scope.credit.dateTo = {};
        $scope.credit.dayOfMonth = "";
        $scope.credit.isPaid = 'NO';
    }

    /*$scope.initializeVariables(credit);
    $http.post(addCreditURL, $scope.credit).success(function () {
        alert("Credit has been created")
    }).error(function () {
        alert("Error :((")
    });*/
    /*$scope.addCredit = function(credit){
        $scope.initializeVariables(credit);
        var creditName = this.credit.name;
        alert('Credit name is "' + creditName + '"');
    };*/

    $scope.isNotEmptyCredit = function(credit) {
        return !credit.name || !credit.dateFrom || !credit.amount || !credit.dateTo ||
            !credit.dayOfMonth || credit.dateTo <= credit.dateFrom;
    };

    $scope.initializeVariables = function (credit) {
        $scope.credit.name = credit.name;
        $scope.credit.dateFrom = credit.dateFrom;
        $scope.credit.amount = credit.amount;
        $scope.credit.paidAmount = credit.paidAmount;
        $scope.credit.rate = credit.rate;
        $scope.credit.dateTo = credit.dateTo;
        $scope.credit.dayOfMonth = credit.dayOfMonth;
    };

    var slider = document.getElementById("creditRateRange");
    var output = document.getElementById("creditRateText");
    output.innerHTML = 'Credit rate: ' + slider.value + ' %';
    slider.oninput = function() {
        output.innerHTML = 'Credit rate: ' + this.value + ' %';
    };

};
