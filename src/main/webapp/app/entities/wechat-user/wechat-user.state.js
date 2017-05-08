(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('wechat-user', {
            parent: 'entity',
            url: '/wechat-user?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WechatUsers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wechat-user/wechat-users.html',
                    controller: 'WechatUserController',
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
        .state('wechat-user-detail', {
            parent: 'entity',
            url: '/wechat-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WechatUser'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wechat-user/wechat-user-detail.html',
                    controller: 'WechatUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WechatUser', function($stateParams, WechatUser) {
                    return WechatUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'wechat-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('wechat-user-detail.edit', {
            parent: 'wechat-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-user/wechat-user-dialog.html',
                    controller: 'WechatUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WechatUser', function(WechatUser) {
                            return WechatUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wechat-user.new', {
            parent: 'wechat-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-user/wechat-user-dialog.html',
                    controller: 'WechatUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                wechatUnionid: null,
                                wechatNickname: null,
                                wechatImage: null,
                                wechatArea: null,
                                userType: null,
                                wechatOpenid: null,
                                wechatQrkey: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('wechat-user', null, { reload: 'wechat-user' });
                }, function() {
                    $state.go('wechat-user');
                });
            }]
        })
        .state('wechat-user.edit', {
            parent: 'wechat-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-user/wechat-user-dialog.html',
                    controller: 'WechatUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WechatUser', function(WechatUser) {
                            return WechatUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wechat-user', null, { reload: 'wechat-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wechat-user.delete', {
            parent: 'wechat-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-user/wechat-user-delete-dialog.html',
                    controller: 'WechatUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WechatUser', function(WechatUser) {
                            return WechatUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wechat-user', null, { reload: 'wechat-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
