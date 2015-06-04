'use strict';

describe('Service: contents', function () {

  // load the service's module
  beforeEach(module('myWebsiteApp'));

  // instantiate service
  var contents;
  beforeEach(inject(function (_contents_) {
    contents = _contents_;
  }));

  it('should do something', function () {
    expect(!!contents).toBe(true);
  });

});
