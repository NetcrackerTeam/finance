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

    $scope.summAmount = 0;
    $scope.summPaidAmount = 0;
    $scope.remainsToPay = 0;
    $scope.totalCredits = 0;
    $scope.allDebt = 0;
    $scope.totalPaidCredits = 0;
    var getFamilyCreditsURL = 'familyCredit/getFamilyCredits';
    $scope.fetchCreditList = function(){
        $http.get(getFamilyCreditsURL).success(function (creditList) {
            $scope.familyCredit = creditList;
            for (var i = 0; i < $scope.familyCredit.length; i++) {
                $scope.summAmount += $scope.familyCredit[i].amount;
                $scope.summPaidAmount += $scope.familyCredit[i].paidAmount;
                $scope.allDebt += $scope.familyCredit[i].debt.amountDebt;
                if ($scope.familyCredit[i].isPaid === "YES") $scope.totalPaidCredits++;
                if ($scope.familyCredit[i].isCommodity === false) $scope.familyCredit[i].isCommodity = "NO";
                if ($scope.familyCredit[i].isCommodity === true) $scope.familyCredit[i].isCommodity = "YES";
            }
            $scope.totalCredits = $scope.familyCredit.length;
            $scope.remainsToPay = $scope.summAmount - $scope.summPaidAmount;
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
        alert("success'");
        window.location.reload();
    }

    function error(response) {
        console.log(response.statusText);
        alert("unsuccess'");
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
        $scope.sendCreditId($rootScope.optionSelect.idCredit);
    };

    $scope.sendCreditId = function(creditId) {
        $http({
            method: 'POST',
            url: 'familyCredit/sendCreditId',
            data: angular.toJson(creditId),
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };

    $scope.fetchFamilyCredit = function(){
        $http.get('familyCredit/getFamilyCredit').success(function (response) {
            $rootScope.personalCreditor = response;
            if ($rootScope.personalCreditor.isCommodity === false) $rootScope.personalCreditor.isCommodity = "NO";
            if ($rootScope.personalCreditor.isCommodity === true) $rootScope.personalCreditor.isCommodity = "YES";
        });
    };

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
        // var dateFrom = $("#datetimepicker").datepicker('getDate');
        var dateFromStr = $("#datetimepickerEdit").val();
        var dateFrom = moment(dateFromStr).format('YYYY-MM-DD');
        var duration = $scope.duration;
        var dateTo = moment(dateFrom).clone().add(duration, 'months').format('YYYY-MM-DD') ;
        // var dateTo = new Date(dateFrom.setMonth(dateFrom.getMonth() + 5))
        $rootScope.personalCreditor.date = {
            year: moment(dateFrom).year() + 1,
            month: moment(dateFrom).month() + 1,
            day: moment(dateFrom).date() + 1
        };
        $rootScope.personalCreditor.dateTo = {
            year: moment(dateTo).year() + 1,
            month: moment(dateTo).month() + 1,
            day: moment(dateTo).date() + 1
        };
        $http({
            method : "PUT",
            url : updateCreditURL,
            data : angular.toJson($rootScope.personalCreditor),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then( success, error );
    };



    $scope.checkFamilyCredit = function () {


        $http({
            method: 'GET',
            url: 'prediction/family/checkCredit',
            params: {duration: $scope.duration, amount: $scope.credit.amount}
        }).then(function (response) {
            console.log(response);
            if (response.status === 200) {
                $scope.check = "You will be able to pay for this credit ";
            } else if (response.status === 202) {
                $scope.check = "No enough data to predict";
            } else {
                $scope.check = "You will not be able to pay for this credit";
            }
        }, function () {
            $scope.check = "error";
        });
    };

};