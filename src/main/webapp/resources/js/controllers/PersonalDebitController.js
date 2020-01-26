'use strict';

/**
 * PersonalDebitController
 * @constructor
 */
var PersonalDebitController = function($scope, $http) {
    $scope.personalHistoryPeriod = 'debitPersonal/historyByPeriod';
    $scope.noInfoChart = false;

    $scope.fetchPersonalHistory = function(){
        $http.get('debitPersonal/history').success(function (response) {
            $scope.personalHistory = response;
        });
    };
    $scope.goTo = function(){
        window.location = "#/personalHistoryPeriod";
//        window.location.reload();
    };

    $scope.fetchPersonalHistoryPeriod = function(dateFrom, dateTo){
        $http({
            method: 'GET',
            url: $scope.personalHistoryPeriod,
            params: {
                'dateFrom': dateFrom.toLocaleString(),
                'dateTo': dateTo.toLocaleString()
            },
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res){
            $scope.personalHistoryPer = res;
        });
    };

    $scope.fetchPersonalAutoOperationHistory = function () {
        $http.get('debitPersonal/autoOperationHistory').success(function (autoOper) {
            $scope.personalAutoOperationHistory = autoOper;
        });
    };

    $scope.fetchPersonalInfo = function () {
        $http.get('debitPersonal/info').success(function (accountInfo) {
            $scope.accountInfo = accountInfo;
        });
    };
    $scope.fetchPersonalInfo();

    $scope.fetchUserInfo = function() {
        $http.get('getUserInfo').success(function (userInfo) {
            $scope.userInfo = userInfo;
        });
    };

    $scope.deleteAutoOperation = function (id) {
        var method = "GET";

        var url = 'debitPersonal/deleteAuto';

        $http({
            method: method,
            url: url,
            params: {'id': id},
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function () {
            window.location.reload();
        }).error(function () {
            alert("unsuccess ")
        });
    };

    $scope.fetchPersonalInfo();
    $scope.fetchUserInfo();

    $scope.fetchChartInfo = function () {
        $http.get('debitPersonal/chartInfo').success(function (response) {
            $scope.incomes = [];
            $scope.expenses = [];
            $scope.noInfoChart = false;
            $scope.dates = [];
            $scope.chartInfo = response;
            if ($scope.chartInfo.length === 1 && $scope.chartInfo[0].amountInc === 0 && $scope.chartInfo[0].amountExp === 0) {
                $scope.noInfoChart = true;
            } else {
                $scope.minExp = '';
                $scope.minInc = '';
                $scope.maxExp = '';
                $scope.maxInc = '';
                for (var i = 0; i < $scope.chartInfo.length; i++) {
                    $scope.incomes.push($scope.chartInfo[i].amountInc);
                    $scope.expenses.push($scope.chartInfo[i].amountExp);
                    $scope.dates.push($scope.chartInfo[i].month);
                }

                var dataIncomes = {
                    label: 'Incomes',
                    data: $scope.incomes,
                    fill: false,
                    borderColor: '#1CC88A',
                    pointBackgroundColor: 'rgba(28, 200, 138, 0.5)',
                    pointBorderColor: '#1CC88A',
                    backgroundColor: 'transparent',
                    pointRadius: 5,
                    pointHoverRadius: 10
                };

                var dataExpenses = {
                    label: "Expenses",
                    data: $scope.expenses,
                    fill: false,
                    borderColor: '#E74A3B',
                    pointBackgroundColor: 'rgba(232, 85, 71, 0.5)',
                    pointBorderColor: '#E74A3B',
                    backgroundColor: 'transparent',
                    pointRadius: 5,
                    pointHoverRadius: 10
                };

                var InExData = {
                    labels: $scope.dates,
                    datasets: [dataIncomes, dataExpenses]
                };

                var InExLine = document.getElementById("InEx");
                if (InExLine) {
                    new Chart(InExLine, {
                        type: 'line',
                        data: InExData,
                        options: {
                            scales: {
                                yAxes: [{
                                    ticks: {
                                        beginAtZero: false,
                                        maxTicksLimit: 10
                                    }
                                }]
                            },
                            legend: {
                                display: true,
                                position: 'top',
                                labels: {
                                    boxWidth: 60,
                                    fontColor: 'black',
                                    fontSize: 14
                                }
                            }
                        }
                    });
                }
            }
        });
    };
    $scope.fetchChartInfo();

    $scope.fetchExpInfo = function () {

        $http.get('debitPersonal/incomeInfo').success(function (response) {
            $scope.chartInfo = response;
            $scope.incPerc = [];
            $scope.names = [];
            for (var i = 0; i < $scope.chartInfo.length; i++) {
                $scope.incPerc.push($scope.chartInfo[i].amount);
                $scope.names.push($scope.chartInfo[i].categoryName);
            }

            var ctx = document.getElementById("CatInc");
            var myPieChart = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: $scope.names,
                    datasets: [{
                        data: $scope.incPerc,
                        backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc', '#6610f2', '#6f42c1'],
                        // hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
                        hoverBorderColor: "rgba(234, 236, 244, 1)",
                    }],
                },
                options: {
                    maintainAspectRatio: false,
                    tooltips: {
                        backgroundColor: "rgb(255,255,255)",
                        bodyFontColor: "#858796",
                        borderColor: '#dddfeb',
                        borderWidth: 1,
                        xPadding: 15,
                        yPadding: 15,
                        displayColors: false,
                        caretPadding: 10,
                    },
                    legend: {
                        display: false
                    },
                    cutoutPercentage: 80,
                },
            });
        });
    };
    $scope.fetchExpInfo();

    $scope.fetchExpInfo = function () {

        $http.get('debitPersonal/expenseInfo').success(function (response) {
            $scope.chartInfo = response;
            $scope.expPerc = [];
            $scope.names = [];
            for (var i = 0; i < $scope.chartInfo.length; i++) {
                $scope.expPerc.push($scope.chartInfo[i].amount);
                $scope.names.push($scope.chartInfo[i].categoryName);
            }

            var ctx = document.getElementById("CatExp");
            var myPieChart = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: $scope.names,
                    datasets: [{
                        data: $scope.expPerc,
                        backgroundColor: ['#f6c23e', '#fd7e14', '#e74a3b', '#e74a3b', '#6f42c1', '#6610f2',
                        '#4e73df', '#36b9cc', '#20c9a6', '#1cc88a', '#36b9cc', '#4e73df'],
                        // hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
                        hoverBorderColor: "rgba(234, 236, 244, 1)",
                    }],
                },
                options: {
                    maintainAspectRatio: false,
                    tooltips: {
                        backgroundColor: "rgb(255,255,255)",
                        bodyFontColor: "#858796",
                        borderColor: '#dddfeb',
                        borderWidth: 1,
                        xPadding: 15,
                        yPadding: 15,
                        displayColors: false,
                        caretPadding: 10,
                    },
                    legend: {
                        display: false
                    },
                    cutoutPercentage: 80,
                },
            });
        });
    };
    $scope.fetchExpInfo();
};