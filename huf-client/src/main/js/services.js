'use strict';

/* Services */

angular.module('hufServices', ['ngResource']).factory('User', function($resource) {
	return $resource('http://localhost:8080/huf-api/rest/users', {}, {
		query: {method:'GET', isArray:true}
	});
});

angular.module('hufServices', ['ngResource']).factory('User', function($resource) {
	return $resource('http://localhost:8080/huf-api/rest/users/:userId', {}, {
		query: {method:'GET', isArray:true}
	});
});

angular.module('hufServices', ['ngResource']).factory('User', function($resource) {
	return $resource('http://localhost:8080/huf-api/rest/users/:userId', {}, {
		remove: {method:'DELETE'}
	});
});
