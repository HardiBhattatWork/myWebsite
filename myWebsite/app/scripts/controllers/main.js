'use strict';

/**
 * @ngdoc function
 * @name routeApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the routeApp
 */
angular.module('myWebsiteApp')
  .controller('MainCtrl', function ($scope, $location, propService) {
    
    propService.getDataProperty1().then(function(response){
        $scope.mainHeader = response.data.mainHeader;
        $scope.mainMessage = response.data.mainMessage;
    });
    
    // propService.getDataExample2().then(function(response){
    //     $scope.TestStringValue = response.data.TestString;
    //     $scope.BooleanValue = response.data.BooleanValue;
    //     console.log($scope.TestStringValue);
    // });
    
    $scope.slides = [
            {image: 'images/slide1.jpg', description: 'Image 01'},
            {image: 'images/slide2.jpg', description: 'Image 02'},
            {image: 'images/slide3.jpg', description: 'Image 03'},
            {image: 'images/slide4.jpg', description: 'Image 04'}
    ];

    $scope.go = function ( path ) {
      console.log($location.path( path ));
    };

    $scope.currentIndex = 0;

    $scope.setCurrentSlideIndex = function (index) {
        $scope.currentIndex = index;
    };

    $scope.isCurrentSlideIndex = function (index) {
        return $scope.currentIndex === index;
    };
    $scope.prevSlide = function () {
        $scope.currentIndex = ($scope.currentIndex < $scope.slides.length - 1) ? ++$scope.currentIndex : 0;
    };

    $scope.nextSlide = function () {
        $scope.currentIndex = ($scope.currentIndex > 0) ? --$scope.currentIndex : $scope.slides.length - 1;
    };

    $scope.navClass = function (page) {
        var currentRoute = $location.path().substring(1) || 'home';
        return page === currentRoute ? 'active' : '';
    };        

  })
  /*.animation('.slide-animation', function () {
        return {
            addClass: function (element, className, done) {
                if (className == 'ng-hide') {
                    TweenMax.to(element, 0.5, {left: -element.parent().width(), onComplete: done });
                }
                else {
                    done();
                }
            },
            removeClass: function (element, className, done) {
                if (className == 'ng-hide') {
                    element.removeClass('ng-hide');

                    TweenMax.set(element, { left: element.parent().width() });
                    TweenMax.to(element, 0.5, {left: 0, onComplete: done });
                }
                else {
                    done();
                }
            }
        };
    });
*/