'use strict';

/**
 * @ngdoc function
 * @name myWebsiteApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the myWebsiteApp
 */
angular.module('myWebsiteApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
