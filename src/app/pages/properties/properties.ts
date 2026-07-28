import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

interface Property {
  id: number;
  name: string;
  address: string;
  city: string;
  type: string;
  units: number;
  occupiedUnits: number;
  status: string;
}

@Component({
  selector: 'app-properties',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './properties.html',
  styleUrl: './properties.css'
})
export class Properties {

  showModal = false;
  editingProperty: Property | null = null;
  searchTerm = '';
  selectedStatus = 'ALL';

  propertyForm: FormGroup;

  properties: Property[] = [
    {
      id: 1,
      name: 'Greenwood Apartments',
      address: '12 Park Avenue',
      city: 'Hyderabad',
      type: 'Apartment',
      units: 24,
      occupiedUnits: 22,
      status: 'ACTIVE'
    },
    {
      id: 2,
      name: 'Lakeview Towers',
      address: '88 Lake Road',
      city: 'Bangalore',
      type: 'Residential',
      units: 18,
      occupiedUnits: 17,
      status: 'ACTIVE'
    },
    {
      id: 3,
      name: 'Palm Heights',
      address: '45 Palm Street',
      city: 'Chennai',
      type: 'Apartment',
      units: 32,
      occupiedUnits: 27,
      status: 'MAINTENANCE'
    },
    {
      id: 4,
      name: 'Sunrise Residency',
      address: '21 Sunrise Avenue',
      city: 'Pune',
      type: 'Residential',
      units: 16,
      occupiedUnits: 15,
      status: 'ACTIVE'
    }
  ];

  constructor(private fb: FormBuilder) {

    this.propertyForm = this.fb.group({

      name: ['', Validators.required],

      address: ['', Validators.required],

      city: ['', Validators.required],

      type: ['Apartment', Validators.required],

      units: [
        1,
        [
          Validators.required,
          Validators.min(1)
        ]
      ],

      occupiedUnits: [
        0,
        [
          Validators.required,
          Validators.min(0)
        ]
      ],

      status: ['ACTIVE', Validators.required]

    });

  }


  get filteredProperties(): Property[] {

    return this.properties.filter(property => {

      const search = this.searchTerm.toLowerCase();

      const matchesSearch =
        property.name.toLowerCase().includes(search) ||
        property.city.toLowerCase().includes(search) ||
        property.address.toLowerCase().includes(search);

      const matchesStatus =
        this.selectedStatus === 'ALL' ||
        property.status === this.selectedStatus;

      return matchesSearch && matchesStatus;

    });

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

    this.editingProperty = null;

    this.propertyForm.reset({
      type: 'Apartment',
      units: 1,
      occupiedUnits: 0,
      status: 'ACTIVE'
    });

    this.showModal = true;

  }


  openEditModal(property: Property): void {

    this.editingProperty = property;

    this.propertyForm.patchValue(property);

    this.showModal = true;

  }


  closeModal(): void {

    this.showModal = false;

    this.editingProperty = null;

    this.propertyForm.reset();

  }


  saveProperty(): void {

    if (this.propertyForm.invalid) {

      this.propertyForm.markAllAsTouched();

      return;

    }

    if (this.editingProperty) {

      const index =
        this.properties.findIndex(
          property =>
            property.id ===
            this.editingProperty?.id
        );

      if (index !== -1) {

        this.properties[index] = {

          ...this.properties[index],

          ...this.propertyForm.value

        };

      }

    } else {

      const newProperty: Property = {

        id: Date.now(),

        ...this.propertyForm.value

      };

      this.properties.unshift(newProperty);

    }

    this.closeModal();

  }


  deleteProperty(property: Property): void {

    const confirmed =
      confirm(
        `Delete "${property.name}"?`
      );

    if (!confirmed) {
      return;
    }

    this.properties =
      this.properties.filter(
        item => item.id !== property.id
      );

  }


  getOccupancy(property: Property): number {

    if (!property.units) {
      return 0;
    }

    return Math.round(
      (
        property.occupiedUnits /
        property.units
      ) * 100
    );

  }

}