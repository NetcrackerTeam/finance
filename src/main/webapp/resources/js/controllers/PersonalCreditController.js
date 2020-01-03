'use strict';
/**
 * PersonalCreditController
 * @constructor
 */
var PersonalCreditController = function($scope, $http) {
    $scope.credits = [];
    $scope.credit = {
        name: "",
        amount: "",
        paidAmount: "",
        date: {},
        creditRate: {},
        dateTo: "",
        monthDay: "",
        isPaid: "NO"
    };

    var getPersonalCreditsURL = 'personalCredit/getPersonalCredits';
    $scope.fetchCreditList= function(){
        $http.get(getPersonalCreditsURL).success(function (creditList) {
            $scope.personalCredit = creditList;
        });
    };
    $scope.fetchCreditList();

    var addCreditURL = 'personalCredit/addCredit';
    $scope.addCredit = function() {
        var dateFrom = new Date(Date.parse($scope.credit.date));
        var dateTo = new Date(Date.parse($scope.credit.dateTo));
        $scope.credit.date = {
            year: dateFrom.getFullYear(),
            month: dateFrom.getMonth(),
            day: dateFrom.getDay()
        };
        $scope.credit.dateTo = {
            year: dateTo.getFullYear(),
            month: dateTo.getMonth(),
            day: dateTo.getDay()
        };
        $http({
            method: 'POST',
            url: addCreditURL,
            data: angular.toJson($scope.credit),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(success, error);
    };

    function refreshPageData() {
        $http({
            method : 'GET',
            url : getPersonalCreditsURL
        }).then(function successCallback(response) {
            $scope.credits = response.data.credits;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    function success() {
        refreshPageData();
        clearForm();
        alert("VSYO SAJEBIS'");
    }

    function error(response) {
        console.log(response.statusText);
        alert("VSYO NE OCHEN'");
    }

    function clearForm() {
        $scope.credit.name = "";
        $scope.credit.amount = "";
        $scope.credit.paidAmount = "";
        $scope.credit.date = {};
        $scope.credit.creditRate = "";
        $scope.credit.dateTo = {};
        $scope.credit.monthDay = "";
        $scope.credit.isPaid = 'NO';
    }

    $scope.isNotEmptyCredit = function(credit) {
        return !credit.name || !credit.date || !credit.amount || !credit.dateTo ||
            !credit.monthDay || credit.dateTo <= credit.date;
    };

    var slider = document.getElementById("creditRateRange");
    var output = document.getElementById("creditRateText");
    output.innerHTML = 'Credit rate: ' + slider.value + ' %';
    slider.oninput = function() {
        output.innerHTML = 'Credit rate: ' + this.value + ' %';
    };

};
