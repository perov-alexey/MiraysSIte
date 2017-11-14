(function(angular) {

    function CommissionController($http) {
        var ctrl = this;

        ctrl.save = function(commission) {
            $http.post("/commission/add", commission).then(function(response) {
                ctrl.onUpdate({});
            });
        }
    }

    angular.module('main').component('commission', {
        templateUrl: '/scripts/components/commission/commission.html',
        controller: CommissionController,
        bindings: {
            commission : '=',
            isAuthorized: '=',
            onUpdate: '&'
        }
    });
})(window.angular);