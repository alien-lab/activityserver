(function() {
    'use strict';
    angular
        .module('activityserverApp')
        .factory('QrType', QrType);

    QrType.$inject = ['$resource', 'DateUtils'];

    function QrType ($resource, DateUtils) {
        var resourceUrl =  'api/qr-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.qrTypeCttime = DateUtils.convertDateTimeFromServer(data.qrTypeCttime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
