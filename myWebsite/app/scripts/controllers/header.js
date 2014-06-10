'use strict';

/**
 * @ngdoc function
 * @name routeApp.controller:HeaderCtrl
 * @description
 * # HeaderCtrl
 * Controller of the routeApp
 */
angular.module('routeApp')
  .controller('HeaderCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
