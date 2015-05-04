'use strict';

/**
 * @ngdoc function
 * @name myWebsiteApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the myWebsiteApp
 */
angular.module('myWebsiteApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
