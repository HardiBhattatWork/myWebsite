'use strict';

/**
 * @ngdoc function
 * @name routeApp.controller:FooterCtrl
 * @description
 * # FooterCtrl
 * Controller of the routeApp
 */
angular.module('myWebsiteApp')
  .controller('FooterCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
