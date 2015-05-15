'use strict';

describe('Service: propService', function () {

  // load the service's module
  beforeEach(module('myWebsiteApp'));

  // instantiate service
  var propService;
  beforeEach(inject(function (_propService_) {
    propService = _propService_;
  }));

  it('should do something', function () {
    expect(!!propService).toBe(true);
  });

});
