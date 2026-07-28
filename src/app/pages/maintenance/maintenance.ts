import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

interface MaintenanceRequest {
  id: number;
  title: string;
  description: string;
  property: string;
  tenant: string;
  category: string;
  priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT';
  status: 'OPEN' | 'IN_PROGRESS' | 'COMPLETED';
  contractor: string;
  scheduledDate: string;
  estimatedCost: number;
  actualCost: number;
  createdAt: string;
}

@Component({
  selector: 'app-maintenance',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './maintenance.html',
  styleUrl: './maintenance.css'
})
export class Maintenance {

  searchTerm = '';
  selectedStatus = 'ALL';

  showModal = false;
  showDetailsModal = false;

  editingRequest: MaintenanceRequest | null = null;
  selectedRequest: MaintenanceRequest | null = null;

  maintenanceForm: FormGroup;

  requests: MaintenanceRequest[] = [
    {
      id: 1001,
      title: 'Water leakage in kitchen',
      description:
        'Water leakage detected below the kitchen sink. Requires plumbing inspection.',
      property: 'Greenwood Apartments',
      tenant: 'Rahul Sharma',
      category: 'Plumbing',
      priority: 'URGENT',
      status: 'OPEN',
      contractor: 'Not Assigned',
      scheduledDate: '2026-07-23',
      estimatedCost: 3500,
      actualCost: 0,
      createdAt: '2026-07-20'
    },
    {
      id: 1002,
      title: 'AC maintenance required',
      description:
        'Air conditioning unit is not cooling properly.',
      property: 'Lakeview Towers',
      tenant: 'Priya Reddy',
      category: 'HVAC',
      priority: 'MEDIUM',
      status: 'IN_PROGRESS',
      contractor: 'CoolTech Services',
      scheduledDate: '2026-07-22',
      estimatedCost: 5000,
      actualCost: 0,
      createdAt: '2026-07-18'
    },
    {
      id: 1003,
      title: 'Electrical switch replacement',
      description:
        'Damaged electrical switch needs replacement.',
      property: 'Palm Heights',
      tenant: 'Arjun Kumar',
      category: 'Electrical',
      priority: 'HIGH',
      status: 'COMPLETED',
      contractor: 'Spark Electricals',
      scheduledDate: '2026-07-16',
      estimatedCost: 1800,
      actualCost: 1650,
      createdAt: '2026-07-14'
    },
    {
      id: 1004,
      title: 'Bedroom door repair',
      description:
        'Bedroom door hinge is damaged and requires repair.',
      property: 'Sunrise Residency',
      tenant: 'Sneha Patel',
      category: 'General Repair',
      priority: 'LOW',
      status: 'OPEN',
      contractor: 'Not Assigned',
      scheduledDate: '2026-07-28',
      estimatedCost: 1200,
      actualCost: 0,
      createdAt: '2026-07-20'
    }
  ];

  constructor(private fb: FormBuilder) {

    this.maintenanceForm = this.fb.group({

      title: [
        '',
        Validators.required
      ],

      description: [
        '',
        Validators.required
      ],

      property: [
        '',
        Validators.required
      ],

      tenant: [
        '',
        Validators.required
      ],

      category: [
        '',
        Validators.required
      ],

      priority: [
        'MEDIUM',
        Validators.required
      ],

      status: [
        'OPEN',
        Validators.required
      ],

      contractor: [
        'Not Assigned'
      ],

      scheduledDate: [
        '',
        Validators.required
      ],

      estimatedCost: [
        0,
        [
          Validators.required,
          Validators.min(0)
        ]
      ],

      actualCost: [
        0,
        Validators.min(0)
      ]

    });
  }

  get filteredRequests(): MaintenanceRequest[] {

    const search =
      this.searchTerm.trim().toLowerCase();

    return this.requests.filter(request => {

      const matchesSearch =
        request.title.toLowerCase().includes(search) ||
        request.property.toLowerCase().includes(search) ||
        request.tenant.toLowerCase().includes(search) ||
        request.category.toLowerCase().includes(search);

      const matchesStatus =
        this.selectedStatus === 'ALL' ||
        request.status === this.selectedStatus;

      return matchesSearch && matchesStatus;
    });
  }

  get openCount(): number {

    return this.requests.filter(
      request => request.status === 'OPEN'
    ).length;
  }

  get inProgressCount(): number {

    return this.requests.filter(
      request => request.status === 'IN_PROGRESS'
    ).length;
  }

  get completedCount(): number {

    return this.requests.filter(
      request => request.status === 'COMPLETED'
    ).length;
  }

  get urgentCount(): number {

    return this.requests.filter(
      request =>
        request.priority === 'URGENT' &&
        request.status !== 'COMPLETED'
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

    this.editingRequest = null;

    this.maintenanceForm.reset({
      priority: 'MEDIUM',
      status: 'OPEN',
      contractor: 'Not Assigned',
      estimatedCost: 0,
      actualCost: 0
    });

    this.showModal = true;
  }

  openEditModal(
    request: MaintenanceRequest
  ): void {

    this.editingRequest = request;

    this.maintenanceForm.patchValue(
      request
    );

    this.showModal = true;
  }

  closeModal(): void {

    this.showModal = false;

    this.editingRequest = null;
  }

  viewRequest(
    request: MaintenanceRequest
  ): void {

    this.selectedRequest = request;

    this.showDetailsModal = true;
  }

  closeDetailsModal(): void {

    this.showDetailsModal = false;

    this.selectedRequest = null;
  }

  saveRequest(): void {

    if (this.maintenanceForm.invalid) {

      this.maintenanceForm.markAllAsTouched();

      return;
    }

    if (this.editingRequest) {

      const index =
        this.requests.findIndex(
          request =>
            request.id ===
            this.editingRequest?.id
        );

      if (index !== -1) {

        this.requests[index] = {

          ...this.requests[index],

          ...this.maintenanceForm.value

        };
      }

    } else {

      const newRequest:
        MaintenanceRequest = {

        id: Date.now(),

        createdAt:
          new Date()
            .toISOString()
            .split('T')[0],

        ...this.maintenanceForm.value

      };

      this.requests.unshift(
        newRequest
      );
    }

    this.closeModal();
  }

  startWork(
    request: MaintenanceRequest
  ): void {

    if (request.status === 'OPEN') {

      request.status =
        'IN_PROGRESS';
    }
  }

  completeWork(
    request: MaintenanceRequest
  ): void {

    if (
      request.status ===
      'IN_PROGRESS'
    ) {

      request.status =
        'COMPLETED';
    }
  }

  deleteRequest(
    request: MaintenanceRequest
  ): void {

    const confirmed =
      confirm(
        `Delete maintenance request "${request.title}"?`
      );

    if (!confirmed) {
      return;
    }

    this.requests =
      this.requests.filter(
        item =>
          item.id !== request.id
      );
  }
}