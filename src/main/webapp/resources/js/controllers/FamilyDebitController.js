var FamilyDebitController = function ($scope, $http, $rootScope) {
    $scope.familyHistoryPeriod = 'debitFamily/familyHistoryByPeriod';
    $scope.noInfoChart = false;

    $scope.fetchFamilyHistory = function () {
        $http.get('debitFamily/history').success(function (historyList) {
            $scope.familyHistory = historyList;
        });
    };

    $scope.goToFam = function(){
        var dateFromFrom = $("#datetimepickerDateFrFamily").val();
        var dateFrom = moment(dateFromFrom).format('YYYY-MM-DD');
        var dateToTo = $("#datetimepickerDateToFamily").val();
        var dateTo = moment(dateToTo).format('YYYY-MM-DD');
        sessionStorage.setItem('dateFrom', dateFrom);
        sessionStorage.setItem('dateTo', dateTo);
        window.location = "#/familyHistoryPeriod";
        window.location.reload();
    };

    $scope.fetchFamilyHistoryPeriod = function(){
        $scope.dateFromFamily = sessionStorage.getItem('dateFrom');
        $scope.dateToFamily = sessionStorage.getItem('dateTo');
        $http({
            method: 'GET',
            url:  $scope.familyHistoryPeriod,
            params: {
                'dateFrom': $scope.dateFromFamily.toLocaleString(),
                'dateTo': $scope.dateToFamily.toLocaleString()
            },
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res){
            $scope.familyHistoryPer = res;
        });
    };

$scope.fetchFamilyAutoOperationHistory = function () {
        $http.get('debitFamily/autoOperationHistory').success(function (autoOper) {
            $scope.familyAutoOperationHistory = autoOper;
        });
    };

    $scope.fetchFamilyInfo = function () {
        $http.get('debitFamily/info').success(function (accountInfo) {
            $scope.accountInfo = accountInfo;
        });
    };

    $scope.nameFamilyAccount = "";
    $scope.createFamilyAccount = function () {
        var method = "POST";
        var url = 'debitFamily/createAccount';

        $http({
            method: method,
            url: url,
            params: {nameAccount: $scope.nameFamilyAccount},
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response) {
            if (response.status === false)
                $scope.ErrorFamilyName = response.message;
            else {
                $('.modal').modal('hide');
                window.location.reload();
            }
        });
    };

    $scope.deleteFamilyAccount = function () {
        var method = "GET";

        var url = 'debitFamily/deactivation';

        $http({
            method: method,
            url: url
        }).success(function (response) {
            if (response.status === true)
                window.location.reload();
            else
                alert(response.message);
        }).error(function () {
            alert("unsuccess")
        });
    };
    $scope.loginParticipant = "";
    $scope.addParticipant = function () {
        var method = "POST";

        var url = 'debitFamily/addUser';

        $http({
            method: method,
            url: url,
            params: {'userLogin': $scope.loginParticipant},
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response) {
            if (response.status === false)
                $scope.ErrorParticipantName = response.message;
            else {
                $('.modal').modal('hide');
                window.location.reload();
            }
        });
    };

    $scope.deleteParticipant = function (userLogin) {
        var method = "GET";

        var url = 'debitFamily/deleteUser';

        $http({
            method: method,
            url: url,
            params: {'userLogin': userLogin},
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function () {
            window.location.reload();
        }).error(function () {
            alert("unsuccess " + $scope.loginParticipant)
        });
    };

    $scope.deleteAutoOperation = function (id) {
        var method = "GET";

        var url = 'debitFamily/deleteAuto';

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

    $scope.fetchParticipants = function () {
        $http.get('debitFamily/getParticipants').success(function (response) {
            $scope.participants = response;
        });
    };

    $scope.fetchUserInfo = function() {
        $http.get('getUserInfo').success(function (userInfo) {
            $scope.userInfo = userInfo;
        });
    };

    $scope.checkUserRole = function () {
        $http.get('debitFamily/role').success(function (response) {
            if (response.status === false) {
                window.location.reload();
            }

        })
    };

   // $scope.fetchFamilyPeriodHistory();
    $scope.checkUserRole();
    $scope.fetchFamilyInfo();
    $scope.fetchUserInfo();

    $scope.fetchChartInfo = function () {
        $http.get('debitFamily/chartInfo').success(function (response) {
            $scope.incomes = [];
            $scope.expenses = [];
            $scope.incomesPred = [];
            $scope.expensesPred = [];
            $scope.noInfoChart = false;
            $scope.dates = [];
            $scope.chartInfo = response;
            if ($scope.chartInfo.length === 1 && $scope.chartInfo[0].amountInc === 0 && $scope.chartInfo[0].amountExp === 0) {
                $scope.noInfoChart = true;
            } else {
                var lengthInfo = $scope.chartInfo.length;
                if (lengthInfo > 2) {
                    for (var i = 0; i < lengthInfo; i++) {
                        $scope.dates.push($scope.chartInfo[i].month);
                        if (i < lengthInfo - 3) {
                            $scope.incomes.push($scope.chartInfo[i].amountInc);
                            $scope.expenses.push($scope.chartInfo[i].amountExp);
                            if (i === lengthInfo - 4) {
                                $scope.incomesPred.push($scope.chartInfo[i].amountInc);
                                $scope.expensesPred.push($scope.chartInfo[i].amountExp);
                            } else {
                                $scope.incomesPred.push(null);
                                $scope.expensesPred.push(null);
                            }
                        } else if (i === lengthInfo - 3) {
                            $scope.incomes.push($scope.chartInfo[i].amountInc);
                            $scope.expenses.push($scope.chartInfo[i].amountExp);
                            $scope.incomesPred.push($scope.chartInfo[i].amountIncPred);
                            $scope.expensesPred.push($scope.chartInfo[i].amountExpPred);
                        } else {
                            $scope.incomesPred.push($scope.chartInfo[i].amountIncPred);
                            $scope.expensesPred.push($scope.chartInfo[i].amountExpPred);
                            $scope.incomes.push(null);
                            $scope.expenses.push(null);
                        }

                    }
                } else {
                    for (i = 0; i < lengthInfo; i++) {
                        $scope.dates.push($scope.chartInfo[i].month);
                        $scope.incomes.push($scope.chartInfo[i].amountInc);
                        $scope.expenses.push($scope.chartInfo[i].amountExp);
                    }
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

                var dataIncomesPred = {
                    label: "Expenses predicted",
                    data: $scope.incomesPred,
                    fill: false,
                    borderColor: '#baffd4',
                    pointBackgroundColor: 'rgba(186,255,212,0.5)',
                    pointBorderColor: '#baffd4',
                    backgroundColor: 'transparent',
                    pointRadius: 5,
                    pointHoverRadius: 10
                };

                var dataExpxensesPred = {
                    label: "Expenses predicted",
                    data: $scope.expensesPred,
                    fill: false,
                    borderColor: '#ffd6c3',
                    pointBackgroundColor: 'rgba(255,214,195,0.5)',
                    pointBorderColor: '#ffd6c3',
                    backgroundColor: 'transparent',
                    pointRadius: 5,
                    pointHoverRadius: 10
                };

                var InExData = {
                    labels: $scope.dates,
                    datasets: [dataIncomes, dataExpenses, dataIncomesPred, dataExpxensesPred]
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

        $http.get('debitFamily/incomeInfo').success(function (response) {
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

        $http.get('debitFamily/expenseInfo').success(function (response) {
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