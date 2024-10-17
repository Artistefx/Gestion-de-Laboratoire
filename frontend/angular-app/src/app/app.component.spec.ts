import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppComponent],
      imports: [ReactiveFormsModule],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the form with name and email controls', () => {
    expect(component.form.contains('name')).toBe(true);
    expect(component.form.contains('email')).toBe(true);
  });

  it('should make the name control required', () => {
    const control = component.form.get('name');
    control?.setValue('');
    expect(control?.valid).toBe(false);
  });

  it('should make the email control required and validate email format', () => {
    const control = component.form.get('email');
    control?.setValue('');
    expect(control?.valid).toBe(false);

    control?.setValue('not-a-valid-email');
    expect(control?.valid).toBe(false);

    control?.setValue('test@example.com');
    expect(control?.valid).toBe(true);
  });

  it('should enable the submit button when form is valid', () => {
    const formElement: HTMLElement = fixture.nativeElement;
    const submitButton = formElement.querySelector('button') as HTMLButtonElement;

    expect(submitButton.disabled).toBe(true); // Initially disabled

    component.form.get('name')?.setValue('Test Name');
    component.form.get('email')?.setValue('test@example.com');
    fixture.detectChanges();

    expect(submitButton.disabled).toBe(false); // Enabled when form is valid
  });
});
