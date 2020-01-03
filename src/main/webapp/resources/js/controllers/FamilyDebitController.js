var FamilyDebitController = function($scope, $http, $rootScope) {

    $scope.fetchFamilyHistory = function(date){
        $http.get('debitFamily/history', {params: {date:date.toLocaleString()}}).success(function (historyList) {
            $scope.familyHistory = historyList;
        });
    };

    $scope.fetchPersonalAutoOperationHistory = function () {
        $http.get('debitFamily/autoOperationHistory').success(function (autoOper) {
            $scope.familyAutoOperationHistory = autoOper;
        });
    };

    $scope.fetchPersonalAutoOperationHistory();
    $scope.fetchFamilyHistory('2019-01-11');
};