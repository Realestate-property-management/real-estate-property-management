import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile {

  isEditing = false;
  savedMessage = false;

  profileForm: FormGroup;

  constructor(private fb: FormBuilder) {

    this.profileForm = this.fb.group({
      firstName: [
        'Alex',
        Validators.required
      ],

      lastName: [
        'Morgan',
        Validators.required
      ],

      email: [
        'alex@propaudit.com',
        [
          Validators.required,
          Validators.email
        ]
      ],

      phone: [
        '+91 98765 43210'
      ],

      company: [
        'PropAudit Property Management'
      ],

      role: [
        'Property Manager'
      ]
    });

    this.profileForm.disable();
  }

  enableEditing(): void {
    this.isEditing = true;
    this.savedMessage = false;
    this.profileForm.enable();
  }

  cancelEditing(): void {
    this.isEditing = false;
    this.profileForm.disable();
  }

  saveProfile(): void {

    if (this.profileForm.invalid) {
      this.profileForm.markAllAsTouched();
      return;
    }

    // Later:
    // PUT /api/users/me

    this.isEditing = false;
    this.profileForm.disable();

    this.savedMessage = true;

    setTimeout(() => {
      this.savedMessage = false;
    }, 3000);
  }

  get fullName(): string {

    return `${this.profileForm.get('firstName')?.value}
            ${this.profileForm.get('lastName')?.value}`;
  }

  get initials(): string {

    const first =
      this.profileForm.get('firstName')?.value?.[0] || '';

    const last =
      this.profileForm.get('lastName')?.value?.[0] || '';

    return (first + last).toUpperCase();
  }
}