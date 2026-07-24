import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

interface Tenant {
  id: number;
  fullName: string;
  email: string;
  phone: string;
  property: string;
  unitNumber: string;
  leaseStart: string;
  leaseEnd: string;
  monthlyRent: number;
  status: string;
}

@Component({
  selector: 'app-tenants',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './tenants.html',
  styleUrl: './tenants.css'
})
export class Tenants {

  searchTerm = '';
  selectedStatus = 'ALL';

  showModal = false;
  showDetailsModal = false;

  editingTenant: Tenant | null = null;
  selectedTenant: Tenant | null = null;

  tenantForm: FormGroup;

  tenants: Tenant[] = [
    {
      id: 1,
      fullName: 'Rahul Sharma',
      email: 'rahul@example.com',
      phone: '9876543210',
      property: 'Greenwood Apartments',
      unitNumber: 'A-204',
      leaseStart: '2026-01-01',
      leaseEnd: '2026-12-31',
      monthlyRent: 25000,
      status: 'ACTIVE'
    },
    {
      id: 2,
      fullName: 'Priya Reddy',
      email: 'priya@example.com',
      phone: '9876501234',
      property: 'Lakeview Towers',
      unitNumber: 'B-105',
      leaseStart: '2026-02-15',
      leaseEnd: '2027-02-14',
      monthlyRent: 32000,
      status: 'ACTIVE'
    },
    {
      id: 3,
      fullName: 'Arjun Kumar',
      email: 'arjun@example.com',
      phone: '9123456780',
      property: 'Palm Heights',
      unitNumber: 'C-302',
      leaseStart: '2025-08-01',
      leaseEnd: '2026-07-31',
      monthlyRent: 28000,
      status: 'EXPIRING'
    },
    {
      id: 4,
      fullName: 'Sneha Patel',
      email: 'sneha@example.com',
      phone: '9988776655',
      property: 'Sunrise Residency',
      unitNumber: 'D-110',
      leaseStart: '2026-03-01',
      leaseEnd: '2027-02-28',
      monthlyRent: 22000,
      status: 'ACTIVE'
    }
  ];

  constructor(private fb: FormBuilder) {

    this.tenantForm = this.fb.group({
      fullName: ['', Validators.required],

      email: ['', [
        Validators.required,
        Validators.email
      ]],

      phone: ['', Validators.required],

      property: ['', Validators.required],

      unitNumber: ['', Validators.required],

      leaseStart: ['', Validators.required],

      leaseEnd: ['', Validators.required],

      monthlyRent: [
        0,
        [
          Validators.required,
          Validators.min(0)
        ]
      ],

      status: ['ACTIVE', Validators.required]
    });
  }

  get filteredTenants(): Tenant[] {

    const search =
      this.searchTerm.trim().toLowerCase();

    return this.tenants.filter(tenant => {

      const matchesSearch =
        tenant.fullName.toLowerCase().includes(search) ||
        tenant.email.toLowerCase().includes(search) ||
        tenant.property.toLowerCase().includes(search) ||
        tenant.unitNumber.toLowerCase().includes(search);

      const matchesStatus =
        this.selectedStatus === 'ALL' ||
        tenant.status === this.selectedStatus;

      return matchesSearch && matchesStatus;
    });
  }

  get activeTenants(): number {
    return this.tenants.filter(
      tenant => tenant.status === 'ACTIVE'
    ).length;
  }

  get expiringTenants(): number {
    return this.tenants.filter(
      tenant => tenant.status === 'EXPIRING'
    ).length;
  }

  onSearch(event: Event): void {

    const input =
      event.target as HTMLInputElement;

    this.searchTerm = input.value;
  }

  onStatusChange(event: Event): void {

    const select =
      event.target as HTMLSelectElement;

    this.selectedStatus = select.value;
  }

  openAddModal(): void {

    this.editingTenant = null;

    this.tenantForm.reset({
      status: 'ACTIVE',
      monthlyRent: 0
    });

    this.showModal = true;
  }

  openEditModal(tenant: Tenant): void {

    this.editingTenant = tenant;

    this.tenantForm.patchValue(tenant);

    this.showModal = true;
  }

  viewTenant(tenant: Tenant): void {

    this.selectedTenant = tenant;

    this.showDetailsModal = true;
  }

  closeDetailsModal(): void {

    this.showDetailsModal = false;

    this.selectedTenant = null;
  }

  closeModal(): void {

    this.showModal = false;

    this.editingTenant = null;

    this.tenantForm.reset();
  }

  saveTenant(): void {

    if (this.tenantForm.invalid) {

      this.tenantForm.markAllAsTouched();

      return;
    }

    if (this.editingTenant) {

      const index =
        this.tenants.findIndex(
          tenant =>
            tenant.id === this.editingTenant?.id
        );

      if (index !== -1) {

        this.tenants[index] = {
          ...this.tenants[index],
          ...this.tenantForm.value
        };
      }

    } else {

      const newTenant: Tenant = {
        id: Date.now(),
        ...this.tenantForm.value
      };

      this.tenants.unshift(newTenant);
    }

    this.closeModal();
  }

  deleteTenant(tenant: Tenant): void {

    const confirmed = confirm(
      `Delete tenant "${tenant.fullName}"?`
    );

    if (!confirmed) {
      return;
    }

    this.tenants =
      this.tenants.filter(
        item => item.id !== tenant.id
      );
  }
}