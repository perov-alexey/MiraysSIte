(function(angular) {

    function CommissionController($http) {
        var ctrl = this;

        ctrl.updateStage = function() {
            var formData = new FormData();
            formData.append("owner", ctrl.commission.owner);
            formData.append("commissionId", ctrl.commission.id);
            formData.append("image", ctrl.commission.currentStage.image);
            formData.append("stageName", ctrl.commission.currentStage.stageName);

            $http({
                method: 'POST',
                url: '/commission/updateStage',
                data: formData,
                headers: {
                    'Content-Type': undefined
                }
            });
        };

        ctrl.add = function() {
            $http.post("/commission/add", ctrl.commission);
        };

        ctrl.saveIfEnterPressed = function(event, commission) {
            if (event && event.key === "Enter") {
                if (ctrl.commission.id) {
                    ctrl.save();
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