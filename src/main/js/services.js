'use strict';

/* Services */

angular.module('hufServices', ['ngResource']).factory('User', function($resource) {
	return $resource('rest/users', {}, {
		query: {method:'GET', isArray:true}
	});
});

angular.module('hufServices', ['ngResource']).factory('User', function($resource) {
	return $resource('rest/users/:userId', {}, {
		query: {method:'GET', isArray:true}
	});
});

angular.module('hufServices', ['ngResource']).factory('User', function($resource) {
	return $resource('rest/users/:userId', {}, {
		remove: {method:'DELETE'}
	});
});
