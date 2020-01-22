'use strict';
/**
 * UserController
 * @constructor
 */
var UserController = function ($scope, $http) {
    $scope.newEmail = null;
    $scope.messageUpdateEmailSuccess = null;
    $scope.messageUpdateEmailError = null;
    $scope.updateEmail = function () {
        var newEmail = $scope.newEmail;
        $http({
            method: 'POST',
            url: 'updateEmail',
            data: angular.toJson(newEmail),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response) {
            if (response.status === false) {
                $scope.messageUpdateEmailSuccess = null;
                $scope.messageUpdateEmailError = response.message;
            } else {
                $scope.messageUpdateEmailError = null;
                $scope.messageUpdateEmailSuccess = response.message;
                $scope.newEmail = null;
            }
        });
    };

    $scope.clearEmail = function () {
        $scope.newEmail = null;
        $scope.messageUpdateEmailSuccess = null;
        $scope.messageUpdateEmailError = null;
    };

    $scope.user = {
        password: null,
        confirmPassword: null
    };
    $scope.messageUpdatePasswordSuccess = null;
    $scope.messageUpdatePasswordError = null;
    $scope.updatePassword = function () {
        $http({
            method: 'POST',
            url: 'updatePassword',
            data: angular.toJson($scope.user),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (response) {
            if (response.status === false) {
                $scope.messageUpdatePasswordSuccess = null;
                $scope.messageUpdatePasswordError = response.message;
            } else {
                $scope.messageUpdatePasswordError = null;
                $scope.messageUpdatePasswordSuccess = response.message;
                $scope.user.password = null;
                $scope.user.confirmPassword = null;
            }
        });
    };

    $scope.clearPassword = function () {
        $scope.user.password = null;
        $scope.user.confirmPassword = null;
        $scope.messageUpdatePasswordSuccess = null;
        $scope.messageUpdatePasswordError = null;
    };
};