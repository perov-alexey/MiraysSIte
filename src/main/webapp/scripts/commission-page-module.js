(function(angular) {
    'use strict';
    angular.module('commissionPage', ['ngRoute']).controller('commissionPageController', function($scope) {
        $scope.commissionsNumber = 5;
        $scope.getNumberRange = function(num) {
            return new Array(num);
        }
    });
})(window.angular);