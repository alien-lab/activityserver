(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('WechatMessageLogDetailController', WechatMessageLogDetailController);

    WechatMessageLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WechatMessageLog'];

    function WechatMessageLogDetailController($scope, $rootScope, $stateParams, previousState, entity, WechatMessageLog) {
        var vm = this;

        vm.wechatMessageLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('activityserverApp:wechatMessageLogUpdate', function(event, result) {
            vm.wechatMessageLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
