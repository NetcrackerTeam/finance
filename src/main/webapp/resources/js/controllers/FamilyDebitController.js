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
};