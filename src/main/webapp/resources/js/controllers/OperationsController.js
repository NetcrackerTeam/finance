var app = angular.module('AngularSpringApp');

//Personal Auto Operations and Simple Operations

app.controller("AddIncomeControllerPersonal", function ($scope, $http) {
    $scope.form = {
        amount: 0.00,
        categoryIncome: "DEFAULT",
        checkbox: false,
        dayOfMonth: "1"
    };
    var pat = /^[0-9]+(\.[0-9][0-9]?)?$/;

    $scope.submitOperation = function () {
        var method = "POST";
        $scope.form.checkbox ? url = 'debitPersonal/createAutoIncome' : url = 'debitPersonal/income';

        if ($scope.form.categoryIncome === 'DEFAULT') {
            $scope.categoryCheck = 'Choose a category';
        } else {

            if (!pat.test($scope.form.amount)) {
                $scope.amountIncErrorMessage = 'Invalid amount';
            } else {

                $http({
                    method: method,
                    url: url,
                    data: angular.toJson($scope.form),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).success(function (response) {
                    if (response.status === false)
                        $scope.amountIncErrorMessage = response.message;
                    else {
                        $('.modal').modal('hide');
                        window.location.reload();
                    }
                }).error(function () {
                    alert("unsuccess");
                });

            }
        }
    };
});

app.controller("AddExpenseControllerPersonal", function ($scope, $http) {
    $scope.form = {
        amount: 0.00,
        categoryExpense: "DEFAULT",
        checkbox: false,
        dayOfMonth: "1"
    };
    var pat = /^[0-9]+(\.[0-9][0-9]?)?$/;

    $scope.submitOperation = function () {
        var method = "POST";
        $scope.form.checkbox ? url = 'debitPersonal/createAutoExpense' : url = 'debitPersonal/expense';

        if ($scope.form.categoryExpense === 'DEFAULT') {
            $scope.categoryCheckExp = 'Choose a category';
        } else {

            if (!pat.test($scope.form.amount)) {
                $scope.amountExpErrorMessage = 'Invalid amount';
            } else {

                $http({
                    method: method,
                    url: url,
                    data: angular.toJson($scope.form),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).success(function (response) {
                    if (response.status === false)
                        $scope.amountExpErrorMessage = response.message;
                    else {
                        $('.modal').modal('hide');
                        window.location.reload();
                    }
                }).error(function () {
                    alert("unsuccess");
                });

            }
        }


    };
})
;


//Family Auto Operations and Simple Operations

app.controller("AddIncomeControllerFamily", function ($scope, $http) {
    $scope.form = {
        amount: 0.00,
        categoryIncome: "DEFAULT",
        checkbox: false,
        dayOfMonth: "1"
    };
    var pat = /^[0-9]+(\.[0-9][0-9]?)?$/;

    $scope.submitOperation = function () {
        method = "POST";

        $scope.form.checkbox ? url = 'debitFamily/createAutoIncome' : url = 'debitFamily/income';

        if ($scope.form.categoryIncome === 'DEFAULT') {
            $scope.categoryCheck = 'Choose a category';
        } else {

            if (!pat.test($scope.form.amount)) {
                $scope.amountIncErrorMessage = 'Invalid amount';
            } else {
                $http({
                    method: method,
                    url: url,
                    data: angular.toJson($scope.form),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).success(function (response) {
                    if (response.status === false)
                        $scope.amountIncErrorMessage = response.message;
                    else {
                        $('.modal').modal('hide');
                        window.location.reload();
                    }
                }).error(function () {
                    alert("unsuccess");
                });

            }
        }
    }
});


app.controller("AddExpenseControllerFamily", function ($scope, $http) {
    $scope.form = {
        amount: 0.00,
        categoryExpense: "DEFAULT",
        checkbox: false,
        dayOfMonth: "1"
    };
    var pat = /^[0-9]+(\.[0-9][0-9]?)?$/;

    $scope.submitOperation = function () {
        method = "POST";
        $scope.form.checkbox ? url = 'debitFamily/createAutoExpense' : url = 'debitFamily/expense';

        if ($scope.form.categoryExpense === 'DEFAULT') {
            $scope.categoryCheckExp = 'Choose a category';
        } else {

            if (!pat.test($scope.form.amount)) {
                $scope.amountExpErrorMessage = 'Invalid amount';
            } else {
                $http({
                    method: method,
                    url: url,
                    data: angular.toJson($scope.form),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).success(function (response) {
                    if (response.status === false)
                        $scope.amountExpErrorMessage = response.message;
                    else {
                        $('.modal').modal('hide');
                        window.location.reload();
                    }
                }).error(function () {
                    alert("unsuccess");
                });
            }
        }
        ;
    }
});

