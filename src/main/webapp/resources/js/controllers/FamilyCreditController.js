'use strict';
/**
 * FamilyCreditController
 * @constructor
 */
var FamilyCreditController = function($scope, $http, $rootScope) {
    $scope.credits = [];
    $scope.credit = {
        name: "",
        amount: "",
        paidAmount: "",
        date: {},
        creditRate: {},
        dateTo: "",
        monthDay: "",
        isPaid: "NO",
        isCommodity: "false"
    };

    var getFamilyCreditsURL = 'familyCredit/getFamilyCredits';
    $scope.fetchCreditList = function(){
        $http.get(getFamilyCreditsURL).success(function (creditList) {
            $scope.familyCredit = creditList;
        });
    };
    $scope.fetchCreditList();


    var addCreditURL = 'familyCredit/addCredit';
    $scope.addFamilyCredit = function() {
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
            url : getFamilyCreditsURL
        }).then(function successCallback(response) {
            $scope.credits = response.data.credits;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    function success() {
        refreshPageData();
        clearForm();
        alert("success");
        window.location.reload();
    }

    function error(response) {
        console.log(response.statusText);
        alert("unsuccess");
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
        $scope.credit.isCommodity = "false"
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

    $scope.checkSelect = function() {
        var selectedDiv = document.getElementById("creditsSelect");
        var infoButton = document.getElementById("infoCredit");
        var deleteButton = document.getElementById("editCredit");
        var selectedOption = selectedDiv.options[selectedDiv.selectedIndex].text;
        $rootScope.optionSelect.idCredit = selectedDiv.options[selectedDiv.selectedIndex].value;
        if (selectedOption !== "none") {
            infoButton.disabled = false;
            deleteButton.disabled = false;
        }
    };

    $scope.fetchFamilyCredit = function(creditId) {
        $http.get('/familyCredit/getFamilyCredit', {params: creditId}).success(function (response) {
            $rootScope.gottenPersonalCredit = response;
        });
    };
    $scope.fetchFamilyCredit($rootScope.optionSelect.idCredit);

    $scope.deleteFamilyCredit = function () {
        var doDelete = confirm("Are you sure you want to delete the credit?");
        if (doDelete) {
            $http({
                method: "DELETE",
                url: "familyCredit/deleteFamilyCredit/" + $rootScope.optionSelect.idCredit
            }).then(success, error);
        } else alert("Credit hasn't been deleted");
    };

    var sliderEdit = document.getElementById("creditRateRangeEdit");
    var outputEdit = document.getElementById("creditRateTextEdit");
    outputEdit.innerHTML = 'Credit rate: ' + sliderEdit.value + ' %';
    sliderEdit.oninput = function() {
        outputEdit.innerHTML = 'Credit rate: ' + this.value + ' %';
    };

    var updateCreditURL = 'familyCredit/updateFamilyCredit';
    $scope.updateFamilyCredit = function () {
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
            method : "PUT",
            url : updateCreditURL,
            data : angular.toJson($scope.credit),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then( success, error );
    }

    $scope.checkPersonalCredit = function () {
        var dateFrom = new Date(Date.parse($scope.credit.date));
        var dateTo = new Date(Date.parse($scope.credit.dateTo));
        $scope.duration = dateTo.getMonth() - dateFrom.getMonth() +
            (12 * (dateTo.getFullYear() - dateFrom.getFullYear()))

        $http({
            method: 'GET',
            url: 'prediction/family/checkCredit',
            params: {duration: $scope.duration, amount: $scope.credit.amount},
        }).
        then(function(response) {
            if(response.status === 200){
                $scope.check = "You will be able to pay for this credit ";
            } else if(response.status === 400) {
                $scope.check = "No enough data to predict";
            } else {
                $scope.check = "You will not be able to pay for this credit";
            }
        }, function(response) {

            $scope.check = response.status;
        });
    };

};