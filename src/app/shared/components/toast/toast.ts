import { Component } from '@angular/core';

import {
  ToastService
} from '../../../core/services/toast';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [],
  templateUrl: './toast.html',
  styleUrl: './toast.css'
})
export class Toast {

  constructor(
    public toastService: ToastService
  ) {}

  remove(id: number): void {

    this.toastService.remove(id);

  }

}