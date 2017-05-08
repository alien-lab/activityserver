(function() {
    'use strict';

    angular
        .module('activityserverApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('qr-type', {
            parent: 'entity',
            url: '/qr-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'QrTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/qr-type/qr-types.html',
                    controller: 'QrTypeController',
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
        .state('qr-type-detail', {
            parent: 'entity',
            url: '/qr-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'QrType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/qr-type/qr-type-detail.html',
                    controller: 'QrTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'QrType', function($stateParams, QrType) {
                    return QrType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'qr-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('qr-type-detail.edit', {
            parent: 'qr-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qr-type/qr-type-dialog.html',
                    controller: 'QrTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QrType', function(QrType) {
                            return QrType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('qr-type.new', {
            parent: 'qr-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qr-type/qr-type-dialog.html',
                    controller: 'QrTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                qrTypeName: null,
                                qrTypeTable: null,
                                qrTypeIdfield: null,
                                qrTypeNamefield: null,
                                qrTypeReptype: null,
                                qrTypeUrl: null,
                                qrTypeCttime: null,
                                qrTypeStatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('qr-type', null, { reload: 'qr-type' });
                }, function() {
                    $state.go('qr-type');
                });
            }]
        })
        .state('qr-type.edit', {
            parent: 'qr-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qr-type/qr-type-dialog.html',
                    controller: 'QrTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QrType', function(QrType) {
                            return QrType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('qr-type', null, { reload: 'qr-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('qr-type.delete', {
            parent: 'qr-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qr-type/qr-type-delete-dialog.html',
                    controller: 'QrTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['QrType', function(QrType) {
                            return QrType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('qr-type', null, { reload: 'qr-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
