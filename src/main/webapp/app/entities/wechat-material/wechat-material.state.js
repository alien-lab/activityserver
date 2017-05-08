(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('wechat-material', {
            parent: 'entity',
            url: '/wechat-material?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WechatMaterials'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wechat-material/wechat-materials.html',
                    controller: 'WechatMaterialController',
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
        .state('wechat-material-detail', {
            parent: 'entity',
            url: '/wechat-material/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WechatMaterial'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wechat-material/wechat-material-detail.html',
                    controller: 'WechatMaterialDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WechatMaterial', function($stateParams, WechatMaterial) {
                    return WechatMaterial.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'wechat-material',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('wechat-material-detail.edit', {
            parent: 'wechat-material-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-material/wechat-material-dialog.html',
                    controller: 'WechatMaterialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WechatMaterial', function(WechatMaterial) {
                            return WechatMaterial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wechat-material.new', {
            parent: 'wechat-material',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-material/wechat-material-dialog.html',
                    controller: 'WechatMaterialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                btnId: null,
                                mediaId: null,
                                craeteTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('wechat-material', null, { reload: 'wechat-material' });
                }, function() {
                    $state.go('wechat-material');
                });
            }]
        })
        .state('wechat-material.edit', {
            parent: 'wechat-material',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-material/wechat-material-dialog.html',
                    controller: 'WechatMaterialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WechatMaterial', function(WechatMaterial) {
                            return WechatMaterial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wechat-material', null, { reload: 'wechat-material' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wechat-material.delete', {
            parent: 'wechat-material',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-material/wechat-material-delete-dialog.html',
                    controller: 'WechatMaterialDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WechatMaterial', function(WechatMaterial) {
                            return WechatMaterial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wechat-material', null, { reload: 'wechat-material' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
