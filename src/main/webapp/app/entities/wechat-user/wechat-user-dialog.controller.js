(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('WechatUserDialogController', WechatUserDialogController);

    WechatUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WechatUser'];

    function WechatUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WechatUser) {
        var vm = this;

        vm.wechatUser = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.wechatUser.id !== null) {
                WechatUser.update(vm.wechatUser, onSaveSuccess, onSaveError);
            } else {
                WechatUser.save(vm.wechatUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('activityserverApp:wechatUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
