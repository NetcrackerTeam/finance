'use strict';
/**
 * PersonalCreditController
 * @constructor
 */
var PersonalCreditController = function ($scope, $http, $rootScope) {
    function InfoModalShow() {
        $("#infoCreditModal").show();
    }

    $scope.getCheckedIndex = function () {
        $http.get('getCheckedIndex').success(function (response) {
            $scope.isCheckedIndex = response;
            if ($scope.isCheckedIndex === "true") {
                $scope.fetchPersonalCredit();
                $rootScope.fetchCreditHistoryPersonal();
                InfoModalShow();
            }
        });
    };
    $scope.getCheckedIndex();

    $scope.sendCheckedIndex = function (checkedIndex) {
        $http({
            method: 'POST',
            url: 'sendCheckedIndex',
            data: angular.toJson(checkedIndex),
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };

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
        totalCreditPayment: 0,
        dateFrom: ""
    };

    $scope.summAmount = 0;
    $scope.summPaidAmount = 0;
    $scope.remainsToPay = 0;
    $scope.totalCredits = 0;
    $scope.allDebt = 0;
    $scope.totalPaidCredits = 0;
    $scope.remainsToPayOne = 0;
    $scope.activeCredits = 0;
    var getPersonalCreditsURL = 'personalCredit/getPersonalCredits';
    $scope.fetchCreditList = function () {
        $http.get(getPersonalCreditsURL).success(function (creditList) {
            $scope.personalCredit = creditList;
            $rootScope.fetchCreditListPersonalRoot(creditList);
            for (var i = 0; i < $scope.personalCredit.length; i++) {
                $scope.summAmount += $scope.personalCredit[i].amount;
                $scope.summPaidAmount += $scope.personalCredit[i].paidAmount;
                $scope.allDebt += $scope.personalCredit[i].debt.amountDebt;
                $scope.remainsToPay += $scope.personalCredit[i].remainsToPay;
                if ($scope.personalCredit[i].isPaid === "YES") $scope.totalPaidCredits++;
                if ($scope.personalCredit[i].isPaid === "NO") $scope.activeCredits++;
                if ($scope.personalCredit[i].isCommodity === false) $scope.personalCredit[i].isCommodity = "NO";
                if ($scope.personalCredit[i].isCommodity === true) $scope.personalCredit[i].isCommodity = "YES";
            }
            $scope.totalCredits = $scope.personalCredit.length;
        });
    };
    $scope.fetchCreditList();

    var addCreditURL = 'personalCredit/add';
    $scope.addPersonalCredit = function () {
        var dateFromStr = $("#datetimepickerPer").val();
        var dateFrom = moment(dateFromStr).format('YYYY-MM-DD');
        var duration = $scope.duration;
        var dateTo = moment(dateFrom).clone().add(duration, 'months').format('YYYY-MM-DD');
        var pat = /^[0-9]+(\.[0-9][0-9]?)?$/;
        if (!pat.test($scope.credit.amount) ){
            $scope.amountErrorMessage = 'Invalid amount';
        } else {
            $scope.amountErrorMessage = "";
            $scope.credit.date = {
                year: moment(dateFrom).year(),
                month: moment(dateFrom).month() + 1,
                day: moment(dateFrom).date()
            };
            $scope.credit.dateTo = {
                year: moment(dateTo).year(),
                month: moment(dateTo).month() + 1,
                day: moment(dateTo).date()
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
            url: getPersonalCreditsURL
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
        $scope.credit.isCommodity = "false";
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

    $scope.sendCreditId = function(creditId) {
        $http({
            method: 'POST',
            url: 'personalCredit/sendCreditId',
            data: angular.toJson(creditId),
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };

    $scope.fetchPersonalCredit = function(){
        $http.get('personalCredit/getPersonalCredit').success(function (response) {
            $rootScope.personalCreditor = response;
            $scope.personalCreditor.duration = moment($scope.personalCreditor.dateTo).diff(moment($scope.personalCreditor.date), 'month');
            if ($rootScope.personalCreditor.isCommodity === false) $rootScope.personalCreditor.isCommodity = "NO";
            if ($rootScope.personalCreditor.isCommodity === true) $rootScope.personalCreditor.isCommodity = "YES";
        });
    };

    $scope.checkDebtDateFrom = function () {
        var debtDateFrom = document.getElementById("debtDateFrom");
        if (debtDateFrom.innerHTML === null) return false;
    };

    $scope.deletePersonalCredit = function () {
        var doDelete = confirm("Are you sure you want to delete the credit?");
        if (doDelete) {
            $http({
                method: "DELETE",
                url: "personalCredit/deletePersonalCredit/" + $rootScope.optionSelect.idCredit
            }).then(success, error);
        } else alert("Credit hasn't been deleted");
    };

    var sliderEdit = document.getElementById("creditRateRangeEdit");
    var outputEdit = document.getElementById("creditRateTextEdit");
    //outputEdit.innerHTML = 'Credit rate: ' + sliderEdit.value + ' %';
    sliderEdit.oninput = function () {
        outputEdit.innerHTML = 'Credit rate: ' + this.value + ' %';
    };

    var updateCreditURL = 'personalCredit/updatePersonalCredit';
    $scope.updatePersonalCredit = function () {
        // var dateFrom = $("#datetimepicker").datepicker('getDate');
        // var dateFromStr = $("#datetimepickerEdit").val();
        var dateFrom = $rootScope.personalCreditor.date
        var duration = $rootScope.personalCreditor.duration;
        var dateTo = moment(dateFrom).clone().add(duration, 'months').format('YYYY-MM-DD') ;
        // var dateTo = new Date(dateFrom.setMonth(dateFrom.getMonth() + 5))
        $rootScope.personalCreditor.date = {
            year: moment(dateFrom).year(),
            month: moment(dateFrom).month(),
            day: moment(dateFrom).date()
        };
        $rootScope.personalCreditor.dateTo = {
            year: moment(dateTo).year(),
            month: moment(dateTo).month(),
            day: moment(dateTo).date()
        };
        $http({
            method : "PUT",
            url : updateCreditURL,
            data : angular.toJson($rootScope.personalCreditor),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function(){
            refreshPageData();
            clearForm();
            $scope.sendCheckedIndex(true);
            window.location.reload();
        }, error);
    };

    var addCreditPayURL = 'personalCredit/pay/';
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
                    $scope.sendCheckedIndex(true);
                    window.location.reload();
                }
            });
        }
    };

    $scope.closeInfo = function () {
        $scope.messageAmountPaySuccess = null;
        $scope.messageAmountPay = null;
        $scope.amountToPay = null;
        //window.location.reload();
    };


    $scope.checkPersonalCredit = function () {

        $http({
            method: 'GET',
            url: 'prediction/personal/checkCredit',
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

    $scope.fetchPersonalCreditFromArray = function () {
        for (var i = 0; i < $rootScope.personalCreditRoot.length; i++) {
            if ($rootScope.personalCreditRoot[i].creditId === $rootScope.creditIdInArray) {
                $rootScope.personalCreditor = $rootScope.personalCreditRoot[i];
                $scope.personalCreditor.duration = moment($scope.personalCreditor.dateTo).diff(moment($scope.personalCreditor.date), 'month');
                $scope.personalCreditor.dateFrom = $scope.personalCreditor.date.year + '-' + $scope.personalCreditor.date.month + '-' + $scope.personalCreditor.date.day;
                if ($rootScope.personalCreditor.isCommodity === false) $rootScope.personalCreditor.isCommodity = "NO";
                if ($rootScope.personalCreditor.isCommodity === true) $rootScope.personalCreditor.isCommodity = "YES";
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
