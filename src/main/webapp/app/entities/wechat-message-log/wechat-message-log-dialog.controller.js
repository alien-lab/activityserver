(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('WechatMessageLogDialogController', WechatMessageLogDialogController);

    WechatMessageLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WechatMessageLog'];

    function WechatMessageLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WechatMessageLog) {
        var vm = this;

        vm.wechatMessageLog = entity;
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
            if (vm.wechatMessageLog.id !== null) {
                WechatMessageLog.update(vm.wechatMessageLog, onSaveSuccess, onSaveError);
            } else {
                WechatMessageLog.save(vm.wechatMessageLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('activityserverApp:wechatMessageLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.messageTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
