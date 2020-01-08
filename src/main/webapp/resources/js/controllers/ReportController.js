app.controller("RepCtrl", ['$scope', '$http', '$templateCache',
    function($scope, $http, $templateCache) {

    $scope.getReportURL = 'debitPersonal/report';

    $scope.month = null;

    $scope.getReport = function() {


        $scope.dateFrom = $scope.month + "-01";

        var temp = new Date(Date.parse($scope.dateFrom));

        $scope.parsedYear = temp.getFullYear();

        if(temp.getMonth() < 9) {
            $scope.parsedMonth =  temp.getMonth()  + 2;
            $scope.parsedMonth = "0" + $scope.parsedMonth;
        } else {
            $scope.parsedMonth = temp.getMonth() + 2;
        }

        $scope.dateTo = $scope.parsedYear + "-" + $scope.parsedMonth + "-01";

        alert($scope.dateFrom);
        alert($scope.dateTo);

        $http({
            method: 'GET',
            url: $scope.getReportURL,
            params: {
                 dateFrom: $scope.dateFrom,
                 dateTo: $scope.dateTo
            }
        }).then(function(response) {
            $scope.content = response.data;
        }, function(response) {
            $scope.content = 'No information about this report';
            $scope.status = response.status;
        });
    };


    $scope.showContent = function($fileContent){
        $scope.content = $fileContent;
    };

    $scope.clear = function () {
        $scope.month = null;
        $scope.content = null;
    };
}]);

app.controller("RepFamilyCtrl", ['$scope', '$http', '$templateCache',
    function($scope, $http, $templateCache) {

        $scope.getReportURL = 'debitFamily/report';

        $scope.month = null;

        $scope.getReport = function() {


            $scope.dateFrom = $scope.month + "-01";

            var temp = new Date(Date.parse($scope.dateFrom));

            $scope.parsedYear = temp.getFullYear();

            if(temp.getMonth() < 9) {
                $scope.parsedMonth =  temp.getMonth()  + 2;
                $scope.parsedMonth = "0" + $scope.parsedMonth;
            } else {
                $scope.parsedMonth = temp.getMonth() + 2;
            }

            $scope.dateTo = $scope.parsedYear + "-" + $scope.parsedMonth + "-01";

            $http({
                method: 'GET',
                url: $scope.getReportURL,
                params: {
                    dateFrom: $scope.dateFrom,
                    dateTo: $scope.dateTo
                }
            }).then(function(response) {
                $scope.content = response.data;
            }, function(response) {
                $scope.content = 'No information about this report';
                $scope.status = response.status;
            });
        };


        $scope.showContent = function($fileContent){
            $scope.content = $fileContent;
        };

        $scope.clear = function () {
            $scope.month = null;
            $scope.content = null;
        };
    }]);

app.directive('onReadFile', function ($parse) {
    return {
        restrict: 'A',
        scope: false,
        link: function(scope, element, attrs) {
            var fn = $parse(attrs.onReadFile);

            element.on('change', function(onChangeEvent) {
                var reader = new FileReader();

                reader.onload = function(onLoadEvent) {
                    scope.$apply(function() {
                        fn(scope, {$fileContent:onLoadEvent.target.result});
                    });
                };

                reader.readAsText((onChangeEvent.srcElement || onChangeEvent.target).files[0]);
            });
        }
    };
});