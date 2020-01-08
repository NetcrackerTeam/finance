'use strict';
/**
 * PersonalCreditController
 * @constructor
 */
var PersonalCreditController = function($scope, $http, $rootScope) {
    $scope.check = null;
    $scope.credits = [];
    $scope.credit = {
        name: "",
        amount: "",
        paidAmount: 0,
        date: {},
        creditRate: {},
        dateTo: "",
        monthDay: "",
        isPaid: "NO",
        isCommodity: "false"
    };

    var getPersonalCreditsURL = 'personalCredit/getPersonalCredits';
    $scope.fetchCreditList = function(){
        $http.get(getPersonalCreditsURL).success(function (creditList) {
            $scope.personalCredit = creditList;
        });
    };
    $scope.fetchCreditList();

    var addCreditURL = 'personalCredit/addCredit';
    $scope.addPersonalCredit = function() {
        // var dateFrom = $("#datetimepicker").datepicker('getDate');
        var dateFromStr = $("#datetimepicker").val();
        var dateFrom = moment(dateFromStr).format('YYYY-MM-DD');
        var duration = $scope.duration;
        var dateTo = moment(dateFrom).clone().add(duration, 'months').format('YYYY-MM-DD') ;
        // var dateTo = new Date(dateFrom.setMonth(dateFrom.getMonth() + 5))
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
        $scope.credit.isCommodity = "false";
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

    $scope.fetchPersonalCredit = function(creditId) {
        $http.get('/personalCredit/getPersonalCredit', {params: creditId}).success(function (response) {
            $rootScope.gottenPersonalCredit = response;
        });
    };
    $scope.fetchPersonalCredit($rootScope.optionSelect.idCredit);

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
    outputEdit.innerHTML = 'Credit rate: ' + sliderEdit.value + ' %';
    sliderEdit.oninput = function() {
        outputEdit.innerHTML = 'Credit rate: ' + this.value + ' %';
    };

    var updateCreditURL = 'personalCredit/updatePersonalCredit';
    $scope.updatePersonalCredit = function () {
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
    };

    $scope.checkPersonalCredit = function () {

        alert($scope.duration);
        $http({
            method: 'GET',
            url: 'prediction/personal/checkCredit',
            params: {duration: $scope.duration, amount: $scope.credit.amount},
        }).
        then(function(response) {
            console.log(response);
            if(response.status === 200){
                $scope.check = "You will be able to pay for this credit ";
            } else if(response.status === 202) {
                $scope.check = "No enough data to predict";
            } else {
                $scope.check = "You will not be able to pay for this credit";
            }
        }, function() {
            $scope.check = "error";
        });
    };
};
