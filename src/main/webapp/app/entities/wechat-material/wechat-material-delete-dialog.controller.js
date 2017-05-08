(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('WechatMaterialDeleteController',WechatMaterialDeleteController);

    WechatMaterialDeleteController.$inject = ['$uibModalInstance', 'entity', 'WechatMaterial'];

    function WechatMaterialDeleteController($uibModalInstance, entity, WechatMaterial) {
        var vm = this;

        vm.wechatMaterial = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WechatMaterial.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
