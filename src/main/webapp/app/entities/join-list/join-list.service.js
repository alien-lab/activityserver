(function() {
    'use strict';
    angular
        .module('activityserverApp')
        .factory('JoinList', JoinList);

    JoinList.$inject = ['$resource', 'DateUtils'];

    function JoinList ($resource, DateUtils) {
        var resourceUrl =  'api/join-lists/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.joinTime = DateUtils.convertDateTimeFromServer(data.joinTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
