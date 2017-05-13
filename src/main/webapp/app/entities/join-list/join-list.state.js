(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('join-list', {
            parent: 'entity',
            url: '/join-list?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JoinLists'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/join-list/join-lists.html',
                    controller: 'JoinListController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('join-list-detail', {
            parent: 'entity',
            url: '/join-list/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JoinList'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/join-list/join-list-detail.html',
                    controller: 'JoinListDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'JoinList', function($stateParams, JoinList) {
                    return JoinList.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'join-list',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('join-list-detail.edit', {
            parent: 'join-list-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/join-list/join-list-dialog.html',
                    controller: 'JoinListDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JoinList', function(JoinList) {
                            return JoinList.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('join-list.new', {
            parent: 'join-list',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/join-list/join-list-dialog.html',
                    controller: 'JoinListDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                joinName: null,
                                joinTime: null,
                                joinOpenid: null,
                                joinPhone: null,
                                joinNick: null,
                                joinIcon: null,
                                joinStatus: null,
                                joinPrice1: null,
                                joinPrice2: null,
                                joinEntercode: null,
                                activity: null,
                                joinForm: null,
                                orderNo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('join-list', null, { reload: 'join-list' });
                }, function() {
                    $state.go('join-list');
                });
            }]
        })
        .state('join-list.edit', {
            parent: 'join-list',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/join-list/join-list-dialog.html',
                    controller: 'JoinListDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JoinList', function(JoinList) {
                            return JoinList.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('join-list', null, { reload: 'join-list' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('join-list.delete', {
            parent: 'join-list',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/join-list/join-list-delete-dialog.html',
                    controller: 'JoinListDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['JoinList', function(JoinList) {
                            return JoinList.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('join-list', null, { reload: 'join-list' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
