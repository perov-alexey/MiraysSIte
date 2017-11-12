(function(angular) {
    'use strict';
    angular.module('commissionPage', ['ngRoute']).controller('commissionPageController', function($scope, $http) {

        $scope.slotsAmount = 5;

        $http.get("/commission/all")
            .then(function(response) {
               $scope.commissions = response.data;
               for (var index = response.data.length; index < $scope.slotsAmount; index++) {
                   $scope.commissions.push({});
               }
            });

        $scope.getNumberRange = function(num) {
            return new Array(num);
        };

    });
})(window.angular);