(function() {
    'use strict';
    angular
        .module('activityserverApp')
        .factory('WechatMaterial', WechatMaterial);

    WechatMaterial.$inject = ['$resource', 'DateUtils'];

    function WechatMaterial ($resource, DateUtils) {
        var resourceUrl =  'api/wechat-materials/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.craeteTime = DateUtils.convertDateTimeFromServer(data.craeteTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
