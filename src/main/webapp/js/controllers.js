'use strict';

/* Controllers */

function UserListCtrl($scope, $location, User) {
	$scope.users = User.query();
	
	$scope.deleteUser = function (id) {
		$scope.users = User.remove({userId: id});
		$location.path('');
	};
}

//UserListCtrl.$inject = ['$scope', 'User'];

function UserDetailCtrl($scope, $location, $routeParams, User) {
	$scope.user = User.get({userId: $routeParams.userId});
}

//UserDetailCtrl.$inject = ['$scope', '$routeParams', 'User'];
