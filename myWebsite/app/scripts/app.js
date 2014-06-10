'use strict';

/**
 * @ngdoc overview
 * @name myWebsiteApp
 * @description
 * # myWebsiteApp
 *
 * Main module of the application.
 */
angular
  .module('myWebsiteApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .when('/header', {
        templateUrl: 'views/header.html',
        controller: 'HeaderCtrl'
      })
      .when('/footer', {
        templateUrl: 'views/footer.html',
        controller: 'FooterCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
