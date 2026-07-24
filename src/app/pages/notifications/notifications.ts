import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

type NotificationType =
  | 'LEASE'
  | 'COMPLIANCE'
  | 'INSPECTION'
  | 'MAINTENANCE'
  | 'SYSTEM';

interface AppNotification {
  id: number;
  title: string;
  message: string;
  type: NotificationType;
  createdAt: string;
  read: boolean;
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
}

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notifications.html',
  styleUrl: './notifications.css'
})
export class Notifications {

  selectedFilter = 'ALL';

  notifications: AppNotification[] = [
    {
      id: 1,
      title: 'Urgent compliance issue detected',
      message:
        'AI analysis detected a high-risk utility clause in the Palm Heights lease.',
      type: 'COMPLIANCE',
      createdAt: '10 minutes ago',
      read: false,
      priority: 'HIGH'
    },
    {
      id: 2,
      title: 'Inspection due tomorrow',
      message:
        'Water system inspection for Sunrise Residency is approaching its due date.',
      type: 'INSPECTION',
      createdAt: '1 hour ago',
      read: false,
      priority: 'HIGH'
    },
    {
      id: 3,
      title: 'Maintenance work started',
      message:
        'AC maintenance at Lakeview Towers has moved to In Progress.',
      type: 'MAINTENANCE',
      createdAt: '3 hours ago',
      read: false,
      priority: 'MEDIUM'
    },
    {
      id: 4,
      title: 'Lease expiry approaching',
      message:
        'The lease for Unit C-302 at Palm Heights is approaching its expiry date.',
      type: 'LEASE',
      createdAt: 'Yesterday',
      read: true,
      priority: 'MEDIUM'
    },
    {
      id: 5,
      title: 'Inspection completed',
      message:
        'Fire safety inspection at Palm Heights was completed successfully.',
      type: 'INSPECTION',
      createdAt: 'Yesterday',
      read: true,
      priority: 'LOW'
    },
    {
      id: 6,
      title: 'Lease analysis completed',
      message:
        'AI compliance analysis for Greenwood Apartments has been completed.',
      type: 'SYSTEM',
      createdAt: '2 days ago',
      read: true,
      priority: 'LOW'
    }
  ];

  get filteredNotifications(): AppNotification[] {

    if (this.selectedFilter === 'ALL') {
      return this.notifications;
    }

    if (this.selectedFilter === 'UNREAD') {
      return this.notifications.filter(
        notification => !notification.read
      );
    }

    return this.notifications.filter(
      notification =>
        notification.type === this.selectedFilter
    );
  }

  get unreadCount(): number {
    return this.notifications.filter(
      notification => !notification.read
    ).length;
  }

  get highPriorityCount(): number {
    return this.notifications.filter(
      notification =>
        notification.priority === 'HIGH' &&
        !notification.read
    ).length;
  }

  onFilterChange(event: Event): void {

    this.selectedFilter =
      (event.target as HTMLSelectElement).value;
  }

  markAsRead(
    notification: AppNotification
  ): void {

    notification.read = true;

    // Later:
    // PATCH /api/notifications/{id}/read
  }

  markAllAsRead(): void {

    this.notifications.forEach(
      notification =>
        notification.read = true
    );
  }

  deleteNotification(
    notification: AppNotification
  ): void {

    this.notifications =
      this.notifications.filter(
        item => item.id !== notification.id
      );
  }

  getIcon(
    type: NotificationType
  ): string {

    switch (type) {

      case 'LEASE':
        return 'L';

      case 'COMPLIANCE':
        return '✦';

      case 'INSPECTION':
        return 'I';

      case 'MAINTENANCE':
        return 'M';

      default:
        return 'S';
    }
  }
}