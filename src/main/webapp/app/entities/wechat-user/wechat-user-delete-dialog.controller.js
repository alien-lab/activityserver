(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('WechatUserDeleteController',WechatUserDeleteController);

    WechatUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'WechatUser'];

    function WechatUserDeleteController($uibModalInstance, entity, WechatUser) {
        var vm = this;

        vm.wechatUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WechatUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
