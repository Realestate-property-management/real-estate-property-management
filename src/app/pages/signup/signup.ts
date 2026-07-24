import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './signup.html',
  styleUrl: './signup.css'
})
export class Signup {

  signupForm: FormGroup;
  showPassword = false;

  constructor(private fb: FormBuilder) {

    this.signupForm = this.fb.group({

      fullName: ['', Validators.required],

      email: ['', [
        Validators.required,
        Validators.email
      ]],

      phone: ['', Validators.required],

      role: ['PROPERTY_MANAGER', Validators.required],

      password: ['', [
        Validators.required,
        Validators.minLength(6)
      ]],

      confirmPassword: ['', Validators.required],

      terms: [false, Validators.requiredTrue]

    });
  }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {

    if (this.signupForm.invalid) {
      this.signupForm.markAllAsTouched();
      return;
    }

    if (
      this.signupForm.value.password !==
      this.signupForm.value.confirmPassword
    ) {
      alert('Passwords do not match.');
      return;
    }

    console.log('Signup Data:', this.signupForm.value);

    // Backend registration API will be connected later.
  }
}