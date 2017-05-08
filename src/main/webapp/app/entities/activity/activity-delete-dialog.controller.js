(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('ActivityDeleteController',ActivityDeleteController);

    ActivityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Activity'];

    function ActivityDeleteController($uibModalInstance, entity, Activity) {
        var vm = this;

        vm.activity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Activity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
