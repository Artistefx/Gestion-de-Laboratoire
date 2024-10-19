import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); // Initial binding
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should have initial message as "Hello, World!"', () => {
    expect(component.message).toBe('Hello, World!');
  });

  it('should update the message when button is clicked', () => {
    const button = fixture.nativeElement.querySelector('button');
    button.click(); // Simulate button click
    fixture.detectChanges(); // Update the view
    expect(component.message).toBe('Hello, Jest!'); // Check the updated message
    expect(fixture.nativeElement.querySelector('h1').textContent).toBe('Hello, Jest!'); // Check the displayed message
  });
});
