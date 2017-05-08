(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('wechat-message-log', {
            parent: 'entity',
            url: '/wechat-message-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WechatMessageLogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wechat-message-log/wechat-message-logs.html',
                    controller: 'WechatMessageLogController',
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
        .state('wechat-message-log-detail', {
            parent: 'entity',
            url: '/wechat-message-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WechatMessageLog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wechat-message-log/wechat-message-log-detail.html',
                    controller: 'WechatMessageLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WechatMessageLog', function($stateParams, WechatMessageLog) {
                    return WechatMessageLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'wechat-message-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('wechat-message-log-detail.edit', {
            parent: 'wechat-message-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-message-log/wechat-message-log-dialog.html',
                    controller: 'WechatMessageLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WechatMessageLog', function(WechatMessageLog) {
                            return WechatMessageLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wechat-message-log.new', {
            parent: 'wechat-message-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-message-log/wechat-message-log-dialog.html',
                    controller: 'WechatMessageLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                messageTime: null,
                                messageBody: null,
                                messageStatus: null,
                                wechatUser: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('wechat-message-log', null, { reload: 'wechat-message-log' });
                }, function() {
                    $state.go('wechat-message-log');
                });
            }]
        })
        .state('wechat-message-log.edit', {
            parent: 'wechat-message-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-message-log/wechat-message-log-dialog.html',
                    controller: 'WechatMessageLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WechatMessageLog', function(WechatMessageLog) {
                            return WechatMessageLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wechat-message-log', null, { reload: 'wechat-message-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wechat-message-log.delete', {
            parent: 'wechat-message-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wechat-message-log/wechat-message-log-delete-dialog.html',
                    controller: 'WechatMessageLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WechatMessageLog', function(WechatMessageLog) {
                            return WechatMessageLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wechat-message-log', null, { reload: 'wechat-message-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
