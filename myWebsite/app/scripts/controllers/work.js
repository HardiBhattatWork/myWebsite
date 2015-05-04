'use strict';

/**
 * @ngdoc function
 * @name myWebsiteApp.controller:WorkCtrl
 * @description
 * # WorkCtrl
 * Controller of the myWebsiteApp
 */
angular.module('myWebsiteApp')
  .controller('WorkCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
