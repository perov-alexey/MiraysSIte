(function(angular) {

    function CommissionController($http) {
        var ctrl = this;

        //TODO Should I use the commission as parameter?
        ctrl.save = function(commission) {
            $http.post("/commission/add", commission).then(function() {
                ctrl.onUpdate({});
            });
        };

        ctrl.saveIfEnterPressed = function(event, commission) {
            if (event && event.key === "Enter") {
                ctrl.save(commission);
            }
        };
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