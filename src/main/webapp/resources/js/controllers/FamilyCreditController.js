var FamilyCreditController = function($scope, $http) {

    $scope.fetchCreditList = function () {
        $http.get("familyCredit/getFamilyCredits").success(function (creditList) {
            $scope.familyCredit = creditList;
        });
    };
    $scope.fetchCreditList();
};