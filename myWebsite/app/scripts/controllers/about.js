'use strict';

/**
 * @ngdoc function
 * @name myWebsiteApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the myWebsiteApp
 */
angular.module('myWebsiteApp')
  .controller('AboutCtrl', function ($scope, propService) {
  
   propService.getDataProperty1().then(function(response){
        $scope.aboutHeader = response.data.aboutHeader;
        $scope.aboutMessage1 = response.data.aboutMessage1;
        $scope.aboutMessage2 = response.data.aboutMessage2;
        $scope.aboutMessage3 = response.data.aboutMessage3;
        $scope.aboutMessage4 = response.data.aboutMessage4;
        $scope.aboutMessage5 = response.data.aboutMessage5;

        $scope.addQuote = response.data.addQuote;
        $scope.java = response.data.java;
        $scope.j2ee = response.data.j2ee;
        $scope.c = response.data.c;
        $scope.cPlusPlus = response.data.cPlusPlus;
        $scope.cSharp = response.data.cSharp;
        $scope.aspNet = response.data.aspNet;
        $scope.html5 = response.data.html5;
        $scope.css3 = response.data.css3;
        $scope.javaScript = response.data.javaScript;
        $scope.ajaxJSON = response.data.ajaxJSON;
        $scope.angularJS = response.data.angularJS;
        $scope.nodeJS = response.data.nodeJS;
        $scope.yui = response.data.yui;
        $scope.jquery = response.data.jquery;
        $scope.jspJstl = response.data.jspJstl;
        $scope.sqlServer0708 = response.data.sqlServer0708;
        
    });

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
