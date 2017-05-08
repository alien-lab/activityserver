(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('JoinListDeleteController',JoinListDeleteController);

    JoinListDeleteController.$inject = ['$uibModalInstance', 'entity', 'JoinList'];

    function JoinListDeleteController($uibModalInstance, entity, JoinList) {
        var vm = this;

        vm.joinList = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            JoinList.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
