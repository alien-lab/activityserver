(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('QrTypeDeleteController',QrTypeDeleteController);

    QrTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'QrType'];

    function QrTypeDeleteController($uibModalInstance, entity, QrType) {
        var vm = this;

        vm.qrType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            QrType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
