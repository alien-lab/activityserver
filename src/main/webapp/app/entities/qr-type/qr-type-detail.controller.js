(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('QrTypeDetailController', QrTypeDetailController);

    QrTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QrType'];

    function QrTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, QrType) {
        var vm = this;

        vm.qrType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('activityserverApp:qrTypeUpdate', function(event, result) {
            vm.qrType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
