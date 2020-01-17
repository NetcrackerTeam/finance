app.controller("RepCtrl", ['$scope', '$http', '$templateCache',
    function($scope, $http, $templateCache) {

    $scope.getReportURL = 'debitPersonal/report';
    $scope.sendReportURL = 'debitPersonal/sendReport';

    $scope.sendCondition = null;

    $scope.getReport = function() {

        $scope.month = $("#datetimepickerPerRep").val() + "-01";
        alert($scope.month)
        $http({
            method: 'GET',
            url: $scope.getReportURL,
            params: {
                 date: $scope.month
            }
        }).then(function(response) {
            $scope.content = response.data.message;
            $scope.sendCondition = 'ok';
        }, function(response) {
            $scope.content = 'No information about this report';
        });
    };

        $scope.sendReport = function() {
            $http({
                method: 'GET',
                url: $scope.sendReportURL,
                params: {
                    date: $scope.month
                }
            }).success(function(response){
                $scope.text = response.data.message;
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
        $scope.sendReportURL = 'debitFamily/sendReport';

        $scope.sendCondition = null;

        $scope.month = null;

        $scope.getReport = function() {


            $scope.month = $("#datetimepickerPerRepFam").val() + "-01";

            alert($scope.month);

            $http({
                method: 'GET',
                url: $scope.getReportURL,
                params: {
                    date: $scope.month
                }
            }).then(function(response) {
                $scope.content = response.data.message;
                $scope.sendCondition = 'ok';
            }, function(response) {
                $scope.content = 'No information about this report';
                $scope.status = response.status;
            });
        };

        $scope.sendReport = function() {
            $http({
                method: 'GET',
                url: $scope.sendReportURL,
                params: {
                    date: $scope.month
                }
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