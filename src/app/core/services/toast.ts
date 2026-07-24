import { Injectable, signal } from '@angular/core';

export type ToastType =
  | 'success'
  | 'error'
  | 'warning'
  | 'info';

export interface ToastMessage {
  id: number;
  message: string;
  type: ToastType;
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  readonly toasts = signal<ToastMessage[]>([]);

  private show(
    message: string,
    type: ToastType
  ): void {

    const toast: ToastMessage = {
      id: Date.now(),
      message,
      type
    };

    this.toasts.update(current => [
      ...current,
      toast
    ]);

    setTimeout(() => {
      this.remove(toast.id);
    }, 3500);
  }

  success(message: string): void {
    this.show(message, 'success');
  }

  error(message: string): void {
    this.show(message, 'error');
  }

  warning(message: string): void {
    this.show(message, 'warning');
  }

  info(message: string): void {
    this.show(message, 'info');
  }

  remove(id: number): void {

    this.toasts.update(current =>
      current.filter(
        toast => toast.id !== id
      )
    );
  }
}