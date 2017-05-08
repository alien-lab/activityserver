(function() {
    'use strict';
    angular
        .module('activityserverApp')
        .factory('WechatMessageLog', WechatMessageLog);

    WechatMessageLog.$inject = ['$resource', 'DateUtils'];

    function WechatMessageLog ($resource, DateUtils) {
        var resourceUrl =  'api/wechat-message-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.messageTime = DateUtils.convertDateTimeFromServer(data.messageTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
