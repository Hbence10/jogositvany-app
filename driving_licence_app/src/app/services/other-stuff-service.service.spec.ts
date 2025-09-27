import { TestBed } from '@angular/core/testing';

import { OtherStuffServiceService } from './other-stuff-service.service';

describe('OtherStuffServiceService', () => {
  let service: OtherStuffServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OtherStuffServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
