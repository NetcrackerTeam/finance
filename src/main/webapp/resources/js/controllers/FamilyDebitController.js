var FamilyDebitController = function ($scope, $http, $rootScope) {

    $scope.fetchFamilyHistory = function () {
        $http.get('debitFamily/history').success(function (historyList) {
            $scope.familyHistory = historyList;
        });
    };

    $scope.fetchPersonalAutoOperationHistory = function () {
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
         //   $scope.ErrorFamilyName = response.message;
            alert(response.message);
            window.location.reload();
        }).error(function () {
            alert("unsuccess")
        });
    };

    $scope.deleteFamilyAccount = function () {
        var method = "GET";

        var url = 'debitFamily/deactivation';

        $http({
            method: method,
            url: url
        }).success(function (response) {
            alert(response.message);
            window.location.reload();
        })
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
            alert(response.message);
            window.location.reload();
        })
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
            alert("success");
            window.location.reload();
        }).error(function () {
            alert("unsuccess " + $scope.loginParticipant)
        });
    };

    $scope.fetchParticipants = function () {
        $http.get('debitFamily/getParticipants').success(function (response) {
            $scope.participants = response;
        });
    };

   // $scope.fetchFamilyPeriodHistory();
    $scope.fetchPersonalAutoOperationHistory();
    $scope.fetchFamilyHistory();
    $scope.fetchFamilyInfo();
};