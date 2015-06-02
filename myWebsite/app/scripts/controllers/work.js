'use strict';

/**
 * @ngdoc function
 * @name myWebsiteApp.controller:WorkCtrl
 * @description
 * # WorkCtrl
 * Controller of the myWebsiteApp
 */
angular.module('myWebsiteApp')
  .controller('WorkCtrl', function ($scope, propService) {
  	propService.getDataProperty1().then(function(response){ 
  		$scope.workGameHeader = response.data.workGameHeader;
  		$scope.workGameMessage = response.data.workGameMessage;
  		$scope.workGameMessageLI1 = response.data.workGameMessageLI1;
  		$scope.workGameMessageLI2 = response.data.workGameMessageLI2;
  		$scope.workGameMessageLI3 = response.data.workGameMessageLI3;
  		$scope.workGameMessageLI4 = response.data.workGameMessageLI4;
  		$scope.workCodeHeader = response.data.workCodeHeader;
  		$scope.workCodeMessage = response.data.workCodeMessage;
  		$scope.workCodeMessageLI1 = response.data.workCodeMessageLI1;
  		$scope.workCodeMessageLI2 = response.data.workCodeMessageLI2;
  		$scope.workCodeMessageLI3 = response.data.workCodeMessageLI3;
  		$scope.workCodeMessageLI4 = response.data.workCodeMessageLI4;
  		$scope.workDigitalHeader = response.data.workDigitalHeader;
  		$scope.workDigitalMessage = response.data.workDigitalMessage;
  		$scope.workDigitaMessageLI1 = response.data.workDigitaMessageLI1;
  		$scope.workDigitaMessageLI2 = response.data.workDigitaMessageLI2;
  		$scope.workDigitaMessageLI3 = response.data.workDigitaMessageLI3;

  	});
  });
