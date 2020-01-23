var FamilyDebitController = function ($scope, $http, $rootScope) {

    $scope.fetchFamilyHistory = function () {
        $http.get('debitFamily/history').success(function (historyList) {
            $scope.familyHistory = historyList;
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

    var incomes = [];
    var expenses = [];
    var dates = [];
    $scope.fetchChartInfo = function () {
        $http.get('debitFamily/chartInfo').success(function (response) {
            $scope.chartInfo = response;
            $scope.minExp = '';
            $scope.minInc = '';
            $scope.maxExp = '';
            $scope.maxInc = '';
            $scope.min = '';
            $scope.max = '';
            for (var i = 0; i < $scope.chartInfo.length; i++) {
                incomes.push($scope.chartInfo[i].amountExp);
                expenses.push($scope.chartInfo[i].amountInc);
                dates.push($scope.chartInfo[i].month);
                if ($scope.chartInfo[i].amountExp > $scope.chartInfo[0].amountExp) $scope.maxExp = $scope.chartInfo[i].amountExp;
                if ($scope.chartInfo[i].amountExp < $scope.chartInfo[0].amountExp) $scope.minExp = $scope.chartInfo[i].amountExp;
                if ($scope.chartInfo[i].amountInc > $scope.chartInfo[0].amountInc) $scope.maxInc = $scope.chartInfo[i].amountInc;
                if ($scope.chartInfo[i].amountInc < $scope.chartInfo[0].amountInc) $scope.minInc = $scope.chartInfo[i].amountInc;
            }
            if ($scope.maxExp > $scope.maxInc) $scope.max = $scope.maxExp;
            else $scope.max = $scope.maxInc;
            if ($scope.minExp < $scope.minInc) $scope.min = $scope.minExp;
            else $scope.min = $scope.minInc;
            $scope.max = parseInt($scope.max);
            $scope.min = parseInt($scope.min);
        });
    };
    $scope.fetchChartInfo();

    var dataIncomes = {
        label: 'Incomes',
        data: incomes,
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
        data: expenses,
        fill: false,
        borderColor: '#E74A3B',
        pointBackgroundColor: 'rgba(232, 85, 71, 0.5)',
        pointBorderColor: '#E74A3B',
        backgroundColor: 'transparent',
        pointRadius: 5,
        pointHoverRadius: 10
    };

    var InExData = {
        labels: dates,
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
                            min: $scope.min,
                            max: $scope.max,
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
};