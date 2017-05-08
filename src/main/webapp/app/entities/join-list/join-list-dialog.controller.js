(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('JoinListDialogController', JoinListDialogController);

    JoinListDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JoinList'];

    function JoinListDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, JoinList) {
        var vm = this;

        vm.joinList = entity;
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
            if (vm.joinList.id !== null) {
                JoinList.update(vm.joinList, onSaveSuccess, onSaveError);
            } else {
                JoinList.save(vm.joinList, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('activityserverApp:joinListUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.joinTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
