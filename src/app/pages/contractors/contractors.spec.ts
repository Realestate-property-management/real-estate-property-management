import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Contractors } from './contractors';

describe('Contractors', () => {
  let component: Contractors;
  let fixture: ComponentFixture<Contractors>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Contractors],
    }).compileComponents();

    fixture = TestBed.createComponent(Contractors);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
