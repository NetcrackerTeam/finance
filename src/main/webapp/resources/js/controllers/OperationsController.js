var app = angular.module('AngularSpringApp');


app.controller("AddIncomeController", function ($scope, $http) {
    debugger;
    $scope.form = {
        amount: 0.00,
        categoryIncome: "DEFAULT",
        checkbox: false,
        dayOfMonth: "5"
    };

    $scope.submitOperation = function () {
        method = "POST";
        url = 'debitPersonal/income';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function () {
            alert("success")
        }).error(function () {
            alert("unsuccess")
        });
    }

        $scope.submitAutoOperation = function () {
            method = "POST";
            url = 'debitPersonal/createAutoIncome';

            $http({
                method: method,
                url: url,
                data: angular.toJson($scope.form),
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).success(function () {
                alert("success")
            }).error(function () {
                alert("unsuccess")
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
        url = 'debitPersonal/expense';

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

