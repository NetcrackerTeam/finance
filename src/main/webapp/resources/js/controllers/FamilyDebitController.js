var FamilyDebitController = function ($scope, $http, $rootScope) {

        $scope.fetchFamilyHistory = function (date) {
            $http.get('debitFamily/history', {params: {date: date.toLocaleString()}}).success(function (historyList) {
                $scope.familyHistory = historyList;
            });
        };

        $scope.fetchPersonalAutoOperationHistory = function () {
            $http.get('debitFamily/autoOperationHistory').success(function (autoOper) {
                $scope.familyAutoOperationHistory = autoOper;
            });
        };

        $scope.nameFamilyAccount = "";
        $scope.createFamilyAccount = function () {
            var method = "POST";

            var url = 'debitFamily/createAccount';

            $http({
                method: method,
                url: url,
                params: {'nameAccount':angular.toJson($scope.nameFamilyAccount)},
                headers: {
                    'Content-Type': 'application/json'
                }
            }).success(function () {
                alert("success");
                window.location.reload();
            }).error(function () {
                alert("unsuccess")
            });
        };

        $scope.fetchPersonalAutoOperationHistory();
        $scope.fetchFamilyHistory('2019-01-11');
    };