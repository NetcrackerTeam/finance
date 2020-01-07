var app = angular.module('AngularSpringApp');

//Personal Auto Operations and Simple Operations

app.controller("AddIncomeControllerPersonal", function ($scope, $http) {
    $scope.form = {
        amount: 0.00,
        categoryIncome: "DEFAULT",
        checkbox: false,
        dayOfMonth: "1"
    };

    $scope.submitOperation = function () {
        var method = "POST";

        $scope.form.checkbox ? url = 'debitPersonal/createAutoIncome' : url = 'debitPersonal/income';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            }
        }).error(function () {
            alert("unsuccess")
        });
    };
});

app.controller("AddExpenseControllerPersonal", function ($scope, $http) {
    $scope.form = {
        amount: 0.00,
        categoryExpense: "DEFAULT",
        checkbox: false,
        dayOfMonth: "1"
    };

    $scope.submitOperation = function () {
        var method = "POST";
        $scope.form.checkbox ? url = 'debitPersonal/createAutoExpense' : url = 'debitPersonal/expense';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).error(function () {
            alert("unsuccess")
        });

    };
});


//Family Auto Operations and Simple Operations

app.controller("AddIncomeControllerFamily", function ($scope, $http) {
    $scope.form = {
        amount: 0.00,
        categoryIncome: "DEFAULT",
        checkbox: false,
        dayOfMonth: "1"
    };

    $scope.submitOperation = function () {
        method = "POST";

        $scope.form.checkbox ? url = 'debitFamily/createAutoIncome' : url = 'debitFamily/income';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            }
        }).error(function () {
            alert("unsuccess")
        });
    };
});



app.controller("AddExpenseControllerFamily", function ($scope, $http) {
    $scope.form = {
        amountExp: 0.00,
        categoryExpense: "DEFAULT",
        checkbox: false,
        dayOfMonth: "1"
    };

    $scope.submitOperation = function () {
        method = "POST";
        url = 'debitFamily/expense';
        $scope.form.checkbox ? url = 'debitFamily/createAutoExpense' : url = 'debitFamily/expense';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).error(function () {
            alert("unsuccess")
        });
    };
});

