(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('WechatMessageLogDeleteController',WechatMessageLogDeleteController);

    WechatMessageLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'WechatMessageLog'];

    function WechatMessageLogDeleteController($uibModalInstance, entity, WechatMessageLog) {
        var vm = this;

        vm.wechatMessageLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WechatMessageLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
