import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

interface ScheduleEvent {
  id: number;
  title: string;
  property: string;
  type:
    | 'INSPECTION'
    | 'MAINTENANCE'
    | 'LEASE'
    | 'COMPLIANCE';
  date: string;
  time: string;
  status:
    | 'SCHEDULED'
    | 'PENDING'
    | 'COMPLETED';
  description: string;
}

@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './calendar.html',
  styleUrl: './calendar.css'
})
export class Calendar {

  selectedFilter = 'ALL';
  showModal = false;

  eventForm: FormGroup;

  events: ScheduleEvent[] = [
    {
      id: 1,
      title: 'Structural Inspection',
      property: 'Greenwood Apartments',
      type: 'INSPECTION',
      date: '2026-07-24',
      time: '10:00 AM',
      status: 'SCHEDULED',
      description:
        'Scheduled structural safety inspection.'
    },
    {
      id: 2,
      title: 'AC Maintenance',
      property: 'Lakeview Towers',
      type: 'MAINTENANCE',
      date: '2026-07-25',
      time: '02:30 PM',
      status: 'SCHEDULED',
      description:
        'HVAC technician visit for AC repair.'
    },
    {
      id: 3,
      title: 'Lease Renewal Deadline',
      property: 'Palm Heights',
      type: 'LEASE',
      date: '2026-07-28',
      time: 'All Day',
      status: 'PENDING',
      description:
        'Tenant lease renewal deadline.'
    },
    {
      id: 4,
      title: 'Compliance Review',
      property: 'Sunrise Residency',
      type: 'COMPLIANCE',
      date: '2026-08-02',
      time: '11:00 AM',
      status: 'PENDING',
      description:
        'Review flagged lease compliance clauses.'
    }
  ];

  constructor(
    private fb: FormBuilder
  ) {
    this.eventForm = this.fb.group({
      title: ['', Validators.required],
      property: ['', Validators.required],
      type: ['INSPECTION', Validators.required],
      date: ['', Validators.required],
      time: ['', Validators.required],
      status: ['SCHEDULED', Validators.required],
      description: ['']
    });
  }

  get filteredEvents(): ScheduleEvent[] {

    if (this.selectedFilter === 'ALL') {
      return this.events;
    }

    return this.events.filter(
      event =>
        event.type === this.selectedFilter
    );
  }

  get inspectionCount(): number {
    return this.events.filter(
      event =>
        event.type === 'INSPECTION'
    ).length;
  }

  get maintenanceCount(): number {
    return this.events.filter(
      event =>
        event.type === 'MAINTENANCE'
    ).length;
  }

  get deadlineCount(): number {
    return this.events.filter(
      event =>
        event.type === 'LEASE' ||
        event.type === 'COMPLIANCE'
    ).length;
  }

  onFilterChange(event: Event): void {

    this.selectedFilter =
      (
        event.target as HTMLSelectElement
      ).value;
  }

  openModal(): void {

    this.eventForm.reset({
      type: 'INSPECTION',
      status: 'SCHEDULED'
    });

    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  addEvent(): void {

    if (this.eventForm.invalid) {

      this.eventForm.markAllAsTouched();

      return;
    }

    const newEvent: ScheduleEvent = {
      id: Date.now(),
      ...this.eventForm.value
    };

    this.events = [
      newEvent,
      ...this.events
    ];

    this.closeModal();
  }

  markCompleted(
    event: ScheduleEvent
  ): void {

    event.status = 'COMPLETED';
  }

  deleteEvent(
    event: ScheduleEvent
  ): void {

    const confirmed = confirm(
      `Delete "${event.title}" from schedule?`
    );

    if (!confirmed) {
      return;
    }

    this.events =
      this.events.filter(
        item => item.id !== event.id
      );
  }
}