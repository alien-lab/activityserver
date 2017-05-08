(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('WechatMaterialDetailController', WechatMaterialDetailController);

    WechatMaterialDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WechatMaterial'];

    function WechatMaterialDetailController($scope, $rootScope, $stateParams, previousState, entity, WechatMaterial) {
        var vm = this;

        vm.wechatMaterial = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('activityserverApp:wechatMaterialUpdate', function(event, result) {
            vm.wechatMaterial = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
