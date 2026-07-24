import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

interface Lease {
  id: number;
  tenantName: string;
  propertyName: string;
  unitNumber: string;
  startDate: string;
  endDate: string;
  monthlyRent: number;
  status: string;
  documentName?: string;
}

@Component({
  selector: 'app-leases',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './leases.html',
  styleUrl: './leases.css'
})
export class Leases {

  searchTerm = '';
  selectedStatus = 'ALL';

  showModal = false;
  showUploadModal = false;

  editingLease: Lease | null = null;
  selectedLease: Lease | null = null;
  selectedFile: File | null = null;

  leaseForm: FormGroup;

  leases: Lease[] = [
    {
      id: 1,
      tenantName: 'Rahul Sharma',
      propertyName: 'Greenwood Apartments',
      unitNumber: 'A-204',
      startDate: '2026-01-01',
      endDate: '2026-12-31',
      monthlyRent: 25000,
      status: 'ACTIVE',
      documentName: 'greenwood-lease.pdf'
    },
    {
      id: 2,
      tenantName: 'Priya Reddy',
      propertyName: 'Lakeview Towers',
      unitNumber: 'B-105',
      startDate: '2026-02-15',
      endDate: '2027-02-14',
      monthlyRent: 32000,
      status: 'ACTIVE'
    },
    {
      id: 3,
      tenantName: 'Arjun Kumar',
      propertyName: 'Palm Heights',
      unitNumber: 'C-302',
      startDate: '2025-08-01',
      endDate: '2026-07-31',
      monthlyRent: 28000,
      status: 'EXPIRING',
      documentName: 'palm-heights.pdf'
    },
    {
      id: 4,
      tenantName: 'Sneha Patel',
      propertyName: 'Sunrise Residency',
      unitNumber: 'D-110',
      startDate: '2025-01-01',
      endDate: '2025-12-31',
      monthlyRent: 22000,
      status: 'EXPIRED'
    }
  ];

  constructor(private fb: FormBuilder) {

    this.leaseForm = this.fb.group({
      tenantName: ['', Validators.required],
      propertyName: ['', Validators.required],
      unitNumber: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      monthlyRent: [
        0,
        [Validators.required, Validators.min(0)]
      ],
      status: ['ACTIVE', Validators.required]
    });
  }

  get filteredLeases(): Lease[] {

    const search = this.searchTerm.toLowerCase();

    return this.leases.filter(lease => {

      const matchesSearch =
        lease.tenantName.toLowerCase().includes(search) ||
        lease.propertyName.toLowerCase().includes(search) ||
        lease.unitNumber.toLowerCase().includes(search);

      const matchesStatus =
        this.selectedStatus === 'ALL' ||
        lease.status === this.selectedStatus;

      return matchesSearch && matchesStatus;
    });
  }

  get activeCount(): number {
    return this.leases.filter(
      lease => lease.status === 'ACTIVE'
    ).length;
  }

  get expiringCount(): number {
    return this.leases.filter(
      lease => lease.status === 'EXPIRING'
    ).length;
  }

  get expiredCount(): number {
    return this.leases.filter(
      lease => lease.status === 'EXPIRED'
    ).length;
  }

  onSearch(event: Event): void {
    this.searchTerm =
      (event.target as HTMLInputElement).value;
  }

  onStatusChange(event: Event): void {
    this.selectedStatus =
      (event.target as HTMLSelectElement).value;
  }

  openAddModal(): void {

    this.editingLease = null;

    this.leaseForm.reset({
      monthlyRent: 0,
      status: 'ACTIVE'
    });

    this.showModal = true;
  }

  openEditModal(lease: Lease): void {

    this.editingLease = lease;

    this.leaseForm.patchValue(lease);

    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.editingLease = null;
  }

  saveLease(): void {

    if (this.leaseForm.invalid) {
      this.leaseForm.markAllAsTouched();
      return;
    }

    if (this.editingLease) {

      const index = this.leases.findIndex(
        lease => lease.id === this.editingLease?.id
      );

      if (index !== -1) {
        this.leases[index] = {
          ...this.leases[index],
          ...this.leaseForm.value
        };
      }

    } else {

      const newLease: Lease = {
        id: Date.now(),
        ...this.leaseForm.value
      };

      this.leases.unshift(newLease);
    }

    this.closeModal();
  }

  deleteLease(lease: Lease): void {

    if (!confirm(`Delete lease for "${lease.tenantName}"?`)) {
      return;
    }

    this.leases =
      this.leases.filter(item => item.id !== lease.id);
  }

  openUploadModal(lease: Lease): void {

    this.selectedLease = lease;
    this.selectedFile = null;
    this.showUploadModal = true;
  }

  closeUploadModal(): void {

    this.showUploadModal = false;
    this.selectedLease = null;
    this.selectedFile = null;
  }

  onFileSelected(event: Event): void {

    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {

      const file = input.files[0];

      if (file.type !== 'application/pdf') {
        alert('Please select a PDF file.');
        input.value = '';
        return;
      }

      this.selectedFile = file;
    }
  }

  uploadDocument(): void {

    if (!this.selectedFile || !this.selectedLease) {
      return;
    }

    const index = this.leases.findIndex(
      lease => lease.id === this.selectedLease?.id
    );

    if (index !== -1) {
      this.leases[index].documentName =
        this.selectedFile.name;
    }

    // Later:
    // Angular -> Spring Boot -> Amazon S3
    // -> LangChain4j -> Nous Hermes analysis

    this.closeUploadModal();
  }
}