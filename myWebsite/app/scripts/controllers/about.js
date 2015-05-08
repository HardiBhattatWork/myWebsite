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
   
  $scope.items = [
  'Nobody cares how much you know, until they know how much you care.', 
  'An obstacle is often an unrecognized opportunity.', 
  'Nothing great was ever achieved without enthusiasm.',
  'I am not afraid of storms, for I am learning how to sail my own ship.'];

  $scope.addItem = function() {
    var newItemNo = $scope.items.length + 1;
    if(newItemNo == 5){
    	$scope.items.push('Even when opportunity knocks a man still has to get up off his seat and open the door.');
    } else if (newItemNo == 6) {
    	$scope.items.push('People who are unable to motivate themselves must be content with mediocrity.');
    }
    else {
    	$scope.items.push('More to come.' + newItemNo);
    }
  };
});
