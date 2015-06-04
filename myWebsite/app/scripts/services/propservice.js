'use strict';

/**
 * @ngdoc service
 * @name myWebsiteApp.propService
 * @description
 * # propService
 * These are 2 ways of requesting http requests.
 */
angular.module('myWebsiteApp')
  .service('propService', function ($http) {
    // AngularJS will instantiate a singleton by calling "new" on this function
    this.getDataProperty1 = function() {
	    var promise = $http({
	        method : 'GET',
	        url : 'resources/web.properties'
	    }).success(function(data, status, headers, config) {
	        return data;
	    });   

	    return promise; 
	};
		
	this.getDataProperty2 = function() {
	    var promise = $http.get('resources/web.properties').success(function(data, status, headers, config) {
	        return data;
	    });   

	    return promise; 
	};
  });
