(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .controller('JoinListDetailController', JoinListDetailController);

    JoinListDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JoinList'];

    function JoinListDetailController($scope, $rootScope, $stateParams, previousState, entity, JoinList) {
        var vm = this;

        vm.joinList = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('activityserverApp:joinListUpdate', function(event, result) {
            vm.joinList = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
