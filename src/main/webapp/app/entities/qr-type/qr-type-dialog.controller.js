(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('QrTypeDialogController', QrTypeDialogController);

    QrTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'QrType'];

    function QrTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, QrType) {
        var vm = this;

        vm.qrType = entity;
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
            if (vm.qrType.id !== null) {
                QrType.update(vm.qrType, onSaveSuccess, onSaveError);
            } else {
                QrType.save(vm.qrType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('activityserverApp:qrTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.qrTypeCttime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
