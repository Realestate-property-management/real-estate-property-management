import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

interface Contractor {
  id: number;
  name: string;
  company: string;
  specialization: string;
  email: string;
  phone: string;
  location: string;
  status: 'ACTIVE' | 'INACTIVE';
  rating: number;
  completedJobs: number;
  currentJobs: number;
  createdAt: string;
}

@Component({
  selector: 'app-contractors',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './contractors.html',
  styleUrl: './contractors.css'
})
export class Contractors {

  searchTerm = '';
  selectedStatus = 'ALL';

  showModal = false;
  showDetailsModal = false;

  editingContractor: Contractor | null = null;
  selectedContractor: Contractor | null = null;

  contractorForm: FormGroup;

  contractors: Contractor[] = [
    {
      id: 1001,
      name: 'Arjun Mehta',
      company: 'SafeBuild Inspections',
      specialization: 'Structural Inspection',
      email: 'arjun@safebuild.com',
      phone: '+91 98765 12001',
      location: 'Hyderabad',
      status: 'ACTIVE',
      rating: 4.8,
      completedJobs: 48,
      currentJobs: 3,
      createdAt: '2026-01-15'
    },
    {
      id: 1002,
      name: 'Rahul Verma',
      company: 'Spark Safety Services',
      specialization: 'Electrical',
      email: 'rahul@sparksafety.com',
      phone: '+91 98765 12002',
      location: 'Visakhapatnam',
      status: 'ACTIVE',
      rating: 4.6,
      completedJobs: 35,
      currentJobs: 2,
      createdAt: '2026-02-10'
    },
    {
      id: 1003,
      name: 'Kiran Rao',
      company: 'AquaCheck Services',
      specialization: 'Plumbing',
      email: 'kiran@aquacheck.com',
      phone: '+91 98765 12003',
      location: 'Vijayawada',
      status: 'ACTIVE',
      rating: 4.7,
      completedJobs: 29,
      currentJobs: 4,
      createdAt: '2026-03-02'
    },
    {
      id: 1004,
      name: 'Priya Sharma',
      company: 'SecureFire Systems',
      specialization: 'Fire Safety',
      email: 'priya@securefire.com',
      phone: '+91 98765 12004',
      location: 'Hyderabad',
      status: 'INACTIVE',
      rating: 4.5,
      completedJobs: 41,
      currentJobs: 0,
      createdAt: '2025-12-20'
    }
  ];

  constructor(
    private fb: FormBuilder
  ) {

    this.contractorForm = this.fb.group({

      name: [
        '',
        Validators.required
      ],

      company: [
        '',
        Validators.required
      ],

      specialization: [
        '',
        Validators.required
      ],

      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],

      phone: [
        '',
        Validators.required
      ],

      location: [
        '',
        Validators.required
      ],

      status: [
        'ACTIVE',
        Validators.required
      ],

      rating: [
        5
      ]

    });
  }

  get filteredContractors(): Contractor[] {

    const search =
      this.searchTerm
        .trim()
        .toLowerCase();

    return this.contractors.filter(
      contractor => {

        const matchesSearch =
          contractor.name
            .toLowerCase()
            .includes(search) ||

          contractor.company
            .toLowerCase()
            .includes(search) ||

          contractor.specialization
            .toLowerCase()
            .includes(search) ||

          contractor.location
            .toLowerCase()
            .includes(search);

        const matchesStatus =
          this.selectedStatus === 'ALL' ||
          contractor.status === this.selectedStatus;

        return matchesSearch && matchesStatus;
      }
    );
  }

  get activeCount(): number {

    return this.contractors.filter(
      contractor =>
        contractor.status === 'ACTIVE'
    ).length;
  }

  get totalCompletedJobs(): number {

    return this.contractors.reduce(
      (total, contractor) =>
        total + contractor.completedJobs,
      0
    );
  }

  get activeJobs(): number {

    return this.contractors.reduce(
      (total, contractor) =>
        total + contractor.currentJobs,
      0
    );
  }

  get averageRating(): string {

    if (!this.contractors.length) {
      return '0.0';
    }

    const total =
      this.contractors.reduce(
        (sum, contractor) =>
          sum + contractor.rating,
        0
      );

    return (
      total / this.contractors.length
    ).toFixed(1);
  }

  onSearch(event: Event): void {

    this.searchTerm =
      (
        event.target as HTMLInputElement
      ).value;
  }

  onStatusChange(event: Event): void {

    this.selectedStatus =
      (
        event.target as HTMLSelectElement
      ).value;
  }

  openAddModal(): void {

    this.editingContractor = null;

    this.contractorForm.reset({
      status: 'ACTIVE',
      rating: 5
    });

    this.showModal = true;
  }

  openEditModal(
    contractor: Contractor
  ): void {

    this.editingContractor = contractor;

    this.contractorForm.patchValue(
      contractor
    );

    this.showModal = true;
  }

  closeModal(): void {

    this.showModal = false;
    this.editingContractor = null;
  }

  viewContractor(
    contractor: Contractor
  ): void {

    this.selectedContractor =
      contractor;

    this.showDetailsModal = true;
  }

  closeDetailsModal(): void {

    this.showDetailsModal = false;
    this.selectedContractor = null;
  }

  saveContractor(): void {

    if (this.contractorForm.invalid) {

      this.contractorForm
        .markAllAsTouched();

      return;
    }

    if (this.editingContractor) {

      const index =
        this.contractors.findIndex(
          contractor =>
            contractor.id ===
            this.editingContractor?.id
        );

      if (index !== -1) {

        this.contractors[index] = {

          ...this.contractors[index],

          ...this.contractorForm.value

        };
      }

    } else {

      const contractor: Contractor = {

        id: Date.now(),

        completedJobs: 0,

        currentJobs: 0,

        createdAt:
          new Date()
            .toISOString()
            .split('T')[0],

        ...this.contractorForm.value

      };

      this.contractors.unshift(
        contractor
      );
    }

    this.closeModal();
  }

  toggleStatus(
    contractor: Contractor
  ): void {

    contractor.status =
      contractor.status === 'ACTIVE'
        ? 'INACTIVE'
        : 'ACTIVE';
  }

  deleteContractor(
    contractor: Contractor
  ): void {

    const confirmed = confirm(
      `Delete contractor "${contractor.name}"?`
    );

    if (!confirmed) {
      return;
    }

    this.contractors =
      this.contractors.filter(
        item =>
          item.id !== contractor.id
      );
  }
}