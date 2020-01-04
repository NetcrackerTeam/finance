

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
                $scope.url = 'prediction/3/family/income';
            } else {
                $scope.url = 'prediction/3/family/expense';
            }

            $http({
                method: $scope.method,
                url: $scope.url,
                params: {duration: $scope.predict.duration}
            }).
            then(function(response) {
                $scope.status = response.data;
            }, function(response) {
                $scope.data = response.data || 'Request failed';
                $scope.status = response.status;
            });
        };

        $scope.updateModel = function(method, url) {
            $scope.method = method;
            $scope.url = url;
        };
    }]);
