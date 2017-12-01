(function(angular) {

    function CommissionController($scope, $http) {
        var ctrl = this;

        ctrl.stages = ["PAYED", "SKETCH", "COLORED", "DETAILED", "FINISHED"];

        $scope.updateStage = function(stageName) {

            var formData = new FormData();
            formData.append("owner", ctrl.commission.owner);
            formData.append("commissionId", ctrl.commission.id);
            formData.append("image", ctrl.commission.currentStage.image);
            formData.append("stageName", stageName);

            $http({
                method: 'POST',
                url: '/commission/updateStage',
                data: formData,
                headers: {
                    'Content-Type': undefined
                }
            }).then(function() {
                ctrl.onUpdate({});
            });
        };

        ctrl.add = function() {
            $http.post("/commission/add", ctrl.commission.owner).then(function() {
                ctrl.onUpdate({});
            });
        };

        ctrl.updateOwner = function() {
            $http.post("/commission/updateOwner", ctrl.commission).then(function() {
                ctrl.onUpdate({});
            });
        };

        ctrl.saveIfEnterPressed = function(event, commission) {
            if (event && event.key === "Enter") {
                if (ctrl.commission.id) {
                    ctrl.updateOwner();
                } else {
                    ctrl.add();
                }

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