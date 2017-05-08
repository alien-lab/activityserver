(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('WechatMaterialDialogController', WechatMaterialDialogController);

    WechatMaterialDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WechatMaterial'];

    function WechatMaterialDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WechatMaterial) {
        var vm = this;

        vm.wechatMaterial = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.wechatMaterial.id !== null) {
                WechatMaterial.update(vm.wechatMaterial, onSaveSuccess, onSaveError);
            } else {
                WechatMaterial.save(vm.wechatMaterial, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('activityserverApp:wechatMaterialUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.craeteTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
