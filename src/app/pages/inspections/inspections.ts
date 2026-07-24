import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

interface Inspection {
  id: number;
  inspectionType: string;
  title: string;
  description: string;
  property: string;
  scheduledDate: string;
  dueDate: string;
  status: 'SCHEDULED' | 'COMPLETED';
  priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT';
  contractor: string;
  sourceType: 'MANUAL' | 'AI_LEASE_ANALYSIS';
  notes: string;
  createdAt: string;
}

@Component({
  selector: 'app-inspections',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './inspections.html',
  styleUrl: './inspections.css'
})
export class Inspections {

  searchTerm = '';
  selectedStatus = 'ALL';

  showModal = false;
  showDetailsModal = false;

  editingInspection: Inspection | null = null;
  selectedInspection: Inspection | null = null;

  inspectionForm: FormGroup;

  inspections: Inspection[] = [
    {
      id: 2001,
      inspectionType: 'Structural',
      title: 'Move-in structural inspection',
      description:
        'Required structural inspection identified from the lease agreement.',
      property: 'Greenwood Apartments',
      scheduledDate: '2026-07-24',
      dueDate: '2026-07-30',
      status: 'SCHEDULED',
      priority: 'HIGH',
      contractor: 'SafeBuild Inspections',
      sourceType: 'AI_LEASE_ANALYSIS',
      notes:
        'Inspection must be completed within the lease-defined deadline.',
      createdAt: '2026-07-19'
    },
    {
      id: 2002,
      inspectionType: 'Electrical',
      title: 'Annual electrical safety inspection',
      description:
        'Routine inspection of electrical systems and safety equipment.',
      property: 'Lakeview Towers',
      scheduledDate: '2026-07-27',
      dueDate: '2026-08-02',
      status: 'SCHEDULED',
      priority: 'MEDIUM',
      contractor: 'Spark Safety Services',
      sourceType: 'MANUAL',
      notes: 'Check wiring, panels and common-area systems.',
      createdAt: '2026-07-18'
    },
    {
      id: 2003,
      inspectionType: 'Fire Safety',
      title: 'Fire equipment inspection',
      description:
        'Inspection of alarms, extinguishers and emergency systems.',
      property: 'Palm Heights',
      scheduledDate: '2026-07-17',
      dueDate: '2026-07-20',
      status: 'COMPLETED',
      priority: 'HIGH',
      contractor: 'SecureFire Systems',
      sourceType: 'MANUAL',
      notes: 'Inspection completed successfully.',
      createdAt: '2026-07-12'
    },
    {
      id: 2004,
      inspectionType: 'Plumbing',
      title: 'Water system inspection',
      description:
        'Inspection deadline detected from property compliance review.',
      property: 'Sunrise Residency',
      scheduledDate: '2026-07-21',
      dueDate: '2026-07-22',
      status: 'SCHEDULED',
      priority: 'URGENT',
      contractor: 'AquaCheck Services',
      sourceType: 'AI_LEASE_ANALYSIS',
      notes: 'Priority inspection required before deadline.',
      createdAt: '2026-07-20'
    }
  ];

  constructor(
    private fb: FormBuilder
  ) {

    this.inspectionForm =
      this.fb.group({

        title: [
          '',
          Validators.required
        ],

        inspectionType: [
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

        scheduledDate: [
          '',
          Validators.required
        ],

        dueDate: [
          '',
          Validators.required
        ],

        priority: [
          'MEDIUM',
          Validators.required
        ],

        contractor: [
          '',
          Validators.required
        ],

        sourceType: [
          'MANUAL',
          Validators.required
        ],

        notes: ['']

      });
  }

  get filteredInspections():
    Inspection[] {

    const search =
      this.searchTerm
        .trim()
        .toLowerCase();

    return this.inspections.filter(
      inspection => {

        const matchesSearch =
          inspection.title
            .toLowerCase()
            .includes(search) ||

          inspection.property
            .toLowerCase()
            .includes(search) ||

          inspection.inspectionType
            .toLowerCase()
            .includes(search) ||

          inspection.contractor
            .toLowerCase()
            .includes(search);

        const matchesStatus =
          this.selectedStatus === 'ALL' ||

          inspection.status ===
          this.selectedStatus;

        return (
          matchesSearch &&
          matchesStatus
        );
      }
    );
  }

  get scheduledCount(): number {

    return this.inspections.filter(
      inspection =>
        inspection.status ===
        'SCHEDULED'
    ).length;
  }

  get completedCount(): number {

    return this.inspections.filter(
      inspection =>
        inspection.status ===
        'COMPLETED'
    ).length;
  }

  get aiScheduledCount(): number {

    return this.inspections.filter(
      inspection =>
        inspection.sourceType ===
        'AI_LEASE_ANALYSIS'
    ).length;
  }

  get urgentCount(): number {

    return this.inspections.filter(
      inspection =>
        inspection.priority ===
        'URGENT' &&

        inspection.status ===
        'SCHEDULED'
    ).length;
  }

  onSearch(
    event: Event
  ): void {

    this.searchTerm =
      (
        event.target as
        HTMLInputElement
      ).value;
  }

  onStatusChange(
    event: Event
  ): void {

    this.selectedStatus =
      (
        event.target as
        HTMLSelectElement
      ).value;
  }

  openAddModal(): void {

    this.editingInspection = null;

    this.inspectionForm.reset({

      priority: 'MEDIUM',

      sourceType: 'MANUAL'

    });

    this.showModal = true;
  }

  openEditModal(
    inspection: Inspection
  ): void {

    this.editingInspection =
      inspection;

    this.inspectionForm.patchValue(
      inspection
    );

    this.showModal = true;
  }

  closeModal(): void {

    this.showModal = false;

    this.editingInspection = null;
  }

  viewInspection(
    inspection: Inspection
  ): void {

    this.selectedInspection =
      inspection;

    this.showDetailsModal = true;
  }

  closeDetailsModal(): void {

    this.showDetailsModal = false;

    this.selectedInspection = null;
  }

  saveInspection(): void {

    if (
      this.inspectionForm.invalid
    ) {

      this.inspectionForm
        .markAllAsTouched();

      return;
    }

    if (
      this.editingInspection
    ) {

      const index =
        this.inspections.findIndex(
          item =>
            item.id ===
            this.editingInspection?.id
        );

      if (index !== -1) {

        this.inspections[index] = {

          ...this.inspections[index],

          ...this.inspectionForm.value

        };
      }

    } else {

      const inspection:
        Inspection = {

        id: Date.now(),

        status: 'SCHEDULED',

        createdAt:
          new Date()
            .toISOString()
            .split('T')[0],

        ...this.inspectionForm.value

      };

      this.inspections.unshift(
        inspection
      );
    }

    this.closeModal();
  }

  completeInspection(
    inspection: Inspection
  ): void {

    if (
      inspection.status ===
      'SCHEDULED'
    ) {

      inspection.status =
        'COMPLETED';
    }
  }

  deleteInspection(
    inspection: Inspection
  ): void {

    const confirmed =
      confirm(
        `Delete inspection "${inspection.title}"?`
      );

    if (!confirmed) {
      return;
    }

    this.inspections =
      this.inspections.filter(
        item =>
          item.id !==
          inspection.id
      );
  }
}