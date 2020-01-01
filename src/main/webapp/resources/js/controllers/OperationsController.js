var app = angular.module('AngularSpringApp');


app.controller("AddIncomeController", function ($scope, $http) {
    debugger;
    $scope.form = {
        amount: 0.00,
        categoryIncome: "DEFAULT"
    };

    $scope.submitOperation = function () {
        method = "POST";
        url = 'debitPersonal/2/income';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers : {
                'Content-Type' : 'application/json'
            }
        });

    }

});

app.controller("AddExpenseController", function ($scope, $http) {
    debugger;
    $scope.form = {
        amount: 0.00,
        categoryExpense: "DEFAULT"
    };

    $scope.submitOperation = function () {
        method = "POST";
        url = 'debitPersonal/2/expense';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers : {
                'Content-Type' : 'application/json'
            }
        });

    }

});

