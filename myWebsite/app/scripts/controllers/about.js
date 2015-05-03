'use strict';

/**
 * @ngdoc function
 * @name routeApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the routeApp
 */
angular.module('myWebsiteApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
