(function(angular) {
    'use strict';
    angular.module('commissionPage', ['ngRoute'])
        .run(function($http) {
            $http.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        }).controller('commissionPageController', function($scope, $http) {

            $scope.checkAuthorization = function() {
                $http.get("/user").then(function (response) {
                    if (response.data.userName) $scope.isAuthorized = true;
                })
            };

            $scope.slotsAmount = 5;

            $scope.updateCommissions = function() {
                $http.get("/commission/all").then(function(response) {
                    $scope.commissions = response.data;
                    for (var index = response.data.length; index < $scope.slotsAmount; index++) {
                        $scope.commissions.push({});
                    }
                });
            };

            $scope.logout = function() {
                $http.post("/logout");
                window.location.href = "";
            };

            $scope.saveCommission = function(commission) {
                $http.post("/commission/add", commission).then(function(response) {
                    $scope.updateCommissions();
                });
            };

            $scope.checkAuthorization();
            $scope.updateCommissions()

    });
})(window.angular);