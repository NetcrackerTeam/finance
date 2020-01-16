var app = angular.module('AngularSpringApp');

app.controller("PersonalPredictController", ['$scope', '$http', '$templateCache',
    function($scope, $http, $templateCache) {
        $scope.method = 'GET';
        $scope.url = null;

        $scope.predict = {
            duration: null,
            category: null
        };

        $scope.fetch = function() {
            $scope.code = null;
            $scope.response = null;

            if($scope.predict.category == 'Income') {
                $scope.url = 'prediction/personal/income';
            } else {
                $scope.url = 'prediction/personal/expense';
            }
            alert($scope.url);
            $http({
                method: $scope.method,
                url: $scope.url,
                params: {duration: $scope.predict.duration}
            }).
            then(function(response) {
                $scope.status = response.data.message;
            }, function() {
                $scope.status = 'Not enough data from account';
            });
        };

        $scope.clear = function() {
            $scope.predict.category = null;
            $scope.predict.duration = null;
            $scope.sum = null
        };
    }]);

app.controller("FamilyPredictController", ['$scope', '$http', '$templateCache',
    function($scope, $http, $templateCache) {
        $scope.method = 'GET';
        $scope.url = null;

        $scope.predict = {
            duration: null,
            category: null
        };

        $scope.fetch = function() {
            $scope.code = null;
            $scope.response = null;

            if($scope.predict.category == 'Income') {
                $scope.url = 'prediction/family/income';
            } else {
                $scope.url = 'prediction/family/expense';
            }

            $http({
                method: $scope.method,
                url: $scope.url,
                params: {duration: $scope.predict.duration}
            }).
            then(function(response) {
                $scope.status = response.data.message;
            }, function() {
                $scope.status = 'Not enough data from account';
            });
        };

        $scope.clear = function() {
            $scope.predict.category = null;
            $scope.predict.duration = null;
            $scope.status = null
        };

    }]);
