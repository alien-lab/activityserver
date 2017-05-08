(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('WechatUserDetailController', WechatUserDetailController);

    WechatUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WechatUser'];

    function WechatUserDetailController($scope, $rootScope, $stateParams, previousState, entity, WechatUser) {
        var vm = this;

        vm.wechatUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('activityserverApp:wechatUserUpdate', function(event, result) {
            vm.wechatUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
