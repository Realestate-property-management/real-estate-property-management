import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  ApiTestService
} from '../../core/services/api-test.service';

import {
  ToastService
} from '../../core/services/toast';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard {

  /* ================================
     DASHBOARD STATISTICS
  ================================= */

  stats = [
    {
      title: 'Total Properties',
      value: '24',
      change: '+3 this month',
      icon: 'P',
      type: 'primary'
    },
    {
      title: 'Active Leases',
      value: '18',
      change: '75% occupancy',
      icon: 'L',
      type: 'success'
    },
    {
      title: 'Open Maintenance',
      value: '7',
      change: '2 high priority',
      icon: 'M',
      type: 'warning'
    },
    {
      title: 'Compliance Alerts',
      value: '4',
      change: 'Requires attention',
      icon: '!',
      type: 'danger'
    }
  ];


  /* ================================
     MAINTENANCE REQUESTS
  ================================= */

  maintenance = [
    {
      title: 'AC repair required',
      property: 'Lakeview Towers',
      priority: 'HIGH',
      status: 'IN_PROGRESS'
    },
    {
      title: 'Kitchen plumbing issue',
      property: 'Greenwood Apartments',
      priority: 'MEDIUM',
      status: 'OPEN'
    },
    {
      title: 'Electrical inspection',
      property: 'Palm Heights',
      priority: 'HIGH',
      status: 'OPEN'
    }
  ];


  /* ================================
     UPCOMING DEADLINES
  ================================= */

  deadlines = [
    {
      title: 'Lease renewal deadline',
      property: 'Greenwood Apartments',
      date: 'Jul 25',
      type: 'LEASE',
      days: 4
    },
    {
      title: 'Structural inspection due',
      property: 'Palm Heights',
      date: 'Jul 28',
      type: 'INSPECTION',
      days: 7
    },
    {
      title: 'Compliance review',
      property: 'Sunrise Residency',
      date: 'Aug 02',
      type: 'COMPLIANCE',
      days: 12
    }
  ];


  /* ================================
     PROPERTY INSPECTIONS
  ================================= */

  inspections = [
    {
      title: 'Structural Inspection',
      type: 'Structural',
      property: 'Greenwood Apartments',
      date: 'Jul 24',
      status: 'SCHEDULED'
    },
    {
      title: 'Electrical Safety',
      type: 'Electrical',
      property: 'Lakeview Towers',
      date: 'Jul 27',
      status: 'SCHEDULED'
    },
    {
      title: 'Fire Safety Inspection',
      type: 'Fire Safety',
      property: 'Palm Heights',
      date: 'Jul 17',
      status: 'COMPLETED'
    }
  ];


  /* ================================
     CONSTRUCTOR
  ================================= */

  constructor(
    private apiTestService: ApiTestService,
    private toast: ToastService
  ) {}


  /* ================================
     TEST TOAST NOTIFICATION
  ================================= */

  


  /* ================================
     TEST SPRING BOOT CONNECTION
  ================================= */

  testBackend(): void {

    this.toast.info(
      'Checking backend connection...'
    );

    this.apiTestService
      .testProperties()
      .subscribe({

        next: (data) => {

          console.log(
            'Backend connected successfully:',
            data
          );

          this.toast.success(
            'Angular connected to Spring Boot successfully!'
          );

        },

        error: (error) => {

          console.error(
            'Backend connection failed:',
            error
          );

          this.toast.error(
            'Backend connection failed. Check console.'
          );

        }

      });

  }

}