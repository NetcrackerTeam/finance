'use strict';
/**
 * FamilyCreditController
 * @constructor
 */
var FamilyCreditController = function ($scope, $http, $rootScope) {
    $scope.check = null;
    $scope.credits = [];
    $scope.credit = {
        name: "",
        amount: 0,
        paidAmount: 0,
        date: new Date(),
        creditRate: 30,
        dateTo: new Date(),
        monthDay: 1,
        isPaid: "NO",
        isCommodity: "false",
        monthPayment: 0,
        remainsToPay: 0,
        totalCreditPayment: 0
    };

    $scope.summAmount = 0;
    $scope.summPaidAmount = 0;
    $scope.remainsToPay = 0;
    $scope.totalCredits = 0;
    $scope.allDebt = 0;
    $scope.totalPaidCredits = 0;
    var getFamilyCreditsURL = 'familyCredit/getFamilyCredits';
    $scope.fetchCreditListFamily = function () {
        $http.get(getFamilyCreditsURL).success(function (creditList) {
            $scope.familyCredit = creditList;
            $rootScope.fetchCreditListFamilyRoot(creditList);
            for (var i = 0; i < $scope.familyCredit.length; i++) {
                $scope.summAmount += $scope.familyCredit[i].amount;
                $scope.summPaidAmount += $scope.familyCredit[i].paidAmount;
                $scope.allDebt += $scope.familyCredit[i].debt.amountDebt;
                $scope.remainsToPay += $scope.familyCredit[i].remainsToPay;
                if ($scope.familyCredit[i].isPaid === "YES") $scope.totalPaidCredits++;
                if ($scope.familyCredit[i].isCommodity === false) $scope.familyCredit[i].isCommodity = "NO";
                if ($scope.familyCredit[i].isCommodity === true) $scope.familyCredit[i].isCommodity = "YES";
            }
            $scope.totalCredits = $scope.familyCredit.length;
        });
    };
    $scope.fetchCreditListFamily();

    var addCreditURL = 'familyCredit/add';
    $scope.addFamilyCredit = function () {
        var dateFromStr = $("#datetimepicker").val();
        var dateFrom = moment(dateFromStr).format('YYYY-MM-DD');
        var duration = $scope.duration;
        var dateTo = moment(dateFrom).clone().add(duration, 'months').format('YYYY-MM-DD');
        var pat = /^[0-9]+(\.[0-9][0-9]?)?$/;
        if (!pat.test($scope.credit.amount)) {
            $scope.amountErrorMessage = 'Invalid amount';
        } else {
            $scope.amountErrorMessage = "";
            $scope.credit.date = {
                year: moment(dateFrom).year() + 1,
                month: moment(dateFrom).month() + 1,
                day: moment(dateFrom).date() + 1
            };
            $scope.credit.dateTo = {
                year: moment(dateTo).year() + 1,
                month: moment(dateTo).month() + 1,
                day: moment(dateTo).date() + 1
            };
            $http({
                method: 'POST',
                url: addCreditURL,
                data: angular.toJson($scope.credit),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).success(function (response) {
                if (response.status === false)
                    $scope.nameErrorMessage = response.message;
                if (response.status === true) {
                    $('.modal').modal('hide');
                    refreshPageData();
                    window.location.reload();
                }
            });
        }
    };

    function refreshPageData() {
        $http({
            method: 'GET',
            url: getFamilyCreditsURL
        }).then(function successCallback(response) {
            $scope.credits = response.data.credits;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    function success() {
        refreshPageData();
        clearForm();
        //alert("success'");
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

    $scope.isNotEmptyCredit = function (credit) {
        return !credit.name || !credit.date || !credit.amount || !credit.dateTo ||
            !credit.monthDay || credit.dateTo <= credit.date;
    };

    var slider = document.getElementById("creditRateRange");
    var output = document.getElementById("creditRateText");
    output.innerHTML = 'Credit rate: ' + slider.value + ' %';
    slider.oninput = function () {
        output.innerHTML = 'Credit rate: ' + this.value + ' %';
    };

    $scope.sendCreditId = function (creditId) {
        $http({
            method: 'POST',
            url: 'familyCredit/sendCreditId',
            data: angular.toJson(creditId),
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };

    $scope.fetchFamilyCredit = function () {
        $http.get('familyCredit/getFamilyCredit').success(function (response) {
            $rootScope.familyCreditor = response;
            if ($rootScope.familyCreditor.isCommodity === false) $rootScope.familyCreditor.isCommodity = "NO";
            if ($rootScope.familyCreditor.isCommodity === true) $rootScope.familyCreditor.isCommodity = "YES";
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
    //outputEdit.innerHTML = 'Credit rate: ' + sliderEdit.value + ' %';
    sliderEdit.oninput = function () {
        outputEdit.innerHTML = 'Credit rate: ' + this.value + ' %';
    };

    var updateCreditURL = 'familyCredit/updateFamilyCredit';
    $scope.updateFamilyCredit = function () {
        // var dateFrom = $("#datetimepicker").datepicker('getDate');
        var dateFromStr = $("#datetimepickerEdit").val();
        var dateFrom = moment(dateFromStr).format('YYYY-MM-DD');
        var duration = $scope.duration;
        var dateTo = moment(dateFrom).clone().add(duration, 'months').format('YYYY-MM-DD');
        // var dateTo = new Date(dateFrom.setMonth(dateFrom.getMonth() + 5))
        $rootScope.familyCreditor.date = {
            year: moment(dateFrom).year(),
            month: moment(dateFrom).month() + 1,
            day: moment(dateFrom).date()
        };
        $rootScope.familyCreditor.dateTo = {
            year: moment(dateTo).year(),
            month: moment(dateTo).month() + 1,
            day: moment(dateTo).date()
        };
        $http({
            method: "PUT",
            url: updateCreditURL,
            data: angular.toJson($rootScope.familyCreditor),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(success, error);
    };

    var addCreditPayURL = 'familyCredit/pay/';
    $scope.addCreditPayment = function(creditId) {
        var amount = $scope.amountToPay;
        var pat = /^[0-9]+(\.[0-9][0-9]?)?$/;
        if (!pat.test(amount) ){
            $scope.messageAmountPay = 'Invalid amount';
        } else {
            $http({
                method: 'POST',
                url: addCreditPayURL + creditId,
                data: angular.toJson(amount),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).success(function (response) {
                if (response.status === false) {
                    $scope.messageAmountPaySuccess = null;
                    $scope.messageAmountPay = response.message;
                } else {
                    $scope.messageAmountPay = null;
                    $scope.messageAmountPaySuccess = response.message;
                    $scope.amountToPay = null;
                }
            });
        }
    };

    $scope.checkFamilyCredit = function () {


        $http({
            method: 'GET',
            url: 'prediction/family/checkCredit',
            params: {duration: $scope.duration, amount: $scope.credit.amount, rate: $scope.credit.creditRate}
        }).then(function (response) {
            console.log(response);
            $scope.check = response.data.message;
        }, function () {
            $scope.check = "error";
        });
    };

    $scope.changeCreditIdInArray = function (creditId) {
        $rootScope.creditIdInArray = creditId;
        $scope.sendCreditId(creditId);
    };

    $scope.fetchFamilyCreditFromArray = function () {
        for (var i = 0; i < $rootScope.familyCreditRoot.length; i++) {
            if ($rootScope.familyCreditRoot[i].creditId === $rootScope.creditIdInArray) {
                $rootScope.familyCreditor = $rootScope.familyCreditRoot[i];
                if ($rootScope.familyCreditor.isCommodity === false) $rootScope.familyCreditor.isCommodity = "NO";
                if ($rootScope.familyCreditor.isCommodity === true) $rootScope.familyCreditor.isCommodity = "YES";
            }
        }
    };

    $scope.clear = function() {
        $scope.credit = {
            name: "",
            amount: null,
            paidAmount: 0,
            date: new Date(),
            dateTo: new Date(),
            monthDay: 1,
            isPaid: "NO",
            isCommodity: "false",
            monthPayment: 0,
            remainsToPay: 0,
            totalCreditPayment: 0
        };
        $scope.check = null;
        $scope.duration = null;
    };

};