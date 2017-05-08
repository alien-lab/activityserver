/**
 * Created by 橘 on 2017/5/7.
 */
(function(){
    'use strict';

// Declare app level module which depends on views, and components
    angular.module('xjszrs', [
        "ui.router",'toaster',"ngAnimate"
    ]).
    config(['$stateProvider', '$locationProvider', '$urlRouterProvider',
        function($stateProvider,$locationProvider,$urlRouterProvider) {//路由定义
            $urlRouterProvider.otherwise('/regist');
            $stateProvider
                .state('regist', {
                    url: '/regist',
                    templateUrl: 'views/regist.html',
                    controller: 'registController'
                })
                .state('regsuccess', {
                    url: '/regsuccess',
                    templateUrl: 'views/success.html'
                })

            ;
        }]);
})();
