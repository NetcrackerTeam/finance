app.controller("RepCtrl", ['$scope', '$http', '$templateCache',
    function($scope, $http, $templateCache) {

    $scope.getReportURL = 'debitPersonal/report';
    $scope.sendReportURL = 'debitPersonal/sendReport';

    $scope.duration = null;
    $scope.balance = null;
    $scope.totalIncome = null;
    $scope.totalExpense= null;

    $scope.incomes = null;
    $scope.expenses = null;



    $scope.sendCondition = null;

    $scope.getReport = function() {
        $scope.text = null;
        $scope.month = $("#datetimepickerPerRep").val() + "-01";

        $http({
            method: 'GET',
            url: $scope.getReportURL,
            params: {
                 date: $scope.month
            }
        }).then(function(response) {
            $scope.duration = response.data.dateTo.date.year + "." + response.data.dateTo.date.month + "." + response.data.dateTo.date.day
                +  "-" + response.data.dateFrom.date.year + "." + response.data.dateFrom.date.month + "." + response.data.dateFrom.date.day;
            $scope.balance = response.data.balance;
            $scope.totalIncome = response.data.totalIncome;
            $scope.totalExpense = response.data.totalExpense;
            $scope.incomes = response.data.categoryIncome;
            $scope.expenses = response.data.categoryExpense;
            $scope.sendCondition = 'ok';
        }, function(response) {
            $scope.text = 'No information about this report';
            $scope.sendCondition = null;
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
                $scope.text = response.message;
            }).error(function (response) {
                $scope.text = 'Report was not sent';
            });
        };


    $scope.showContent = function($fileContent){
        $scope.content = $fileContent;
    };

    $scope.clear = function () {
        $scope.month = null;
        $scope.content = null;
        $scope.text = null;
        $scope.duration = null;
        $scope.balance = null;
        $scope.totalIncome = null;
        $scope.totalExpense = null;
        $scope.incomes = null;
        $scope.expenses = null;
    };
}]);

app.controller("RepFamilyCtrl", ['$scope', '$http', '$templateCache',
    function($scope, $http, $templateCache) {

        $scope.getReportURL = 'debitFamily/report';
        $scope.sendReportURL = 'debitFamily/sendReport';

        $scope.sendCondition = null;

        $scope.month = null;

        $scope.getReport = function() {

        $scope.text = null;
            $scope.month = $("#datetimepickerPerRepFam").val() + "-01";

            $http({
                method: 'GET',
                url: $scope.getReportURL,
                params: {
                    date: $scope.month
                }
            }).then(function(response) {
                $scope.duration = response.data.dateTo.date.year + "." + response.data.dateTo.date.month + "." + response.data.dateTo.date.day
                    +  "-" + response.data.dateFrom.date.year + "." + response.data.dateFrom.date.month + "." + response.data.dateFrom.date.day;
                $scope.balance = response.data.balance;
                $scope.totalIncome = response.data.totalIncome;
                $scope.totalExpense = response.data.totalExpense;
                $scope.incomes = response.data.categoryIncome;
                $scope.expenses = response.data.categoryExpense;
                $scope.sendCondition = 'ok';
            }, function(response) {
                $scope.text = 'No information about this report';
                $scope.status = response.status;
                $scope.sendCondition = null;
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
                 $scope.text = response.message;
            }).error(function (response) {
                 $scope.text = 'Report was not sent';
            });
        };


        $scope.showContent = function($fileContent){
            $scope.content = $fileContent;
        };

        $scope.clear = function () {
            $scope.month = null;
            $scope.content = null;
            $scope.text = null;
            $scope.duration = null;
            $scope.balance = null;
            $scope.totalIncome = null;
            $scope.totalExpense = null;
            $scope.incomes = null;
            $scope.expenses = null;
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