import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

interface ComplianceIssue {
  id: number;
  title: string;
  lease: string;
  property: string;
  clause: string;
  severity: 'HIGH' | 'MEDIUM' | 'LOW';
  status: 'PENDING' | 'REVIEWED';
  category: string;
  recommendation: string;
}

interface Analysis {
  id: number;
  lease: string;
  property: string;
  score: number;
  issues: number;
  status: string;
  analyzedDate: string;
}

@Component({
  selector: 'app-compliance',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './compliance.html',
  styleUrl: './compliance.css'
})
export class Compliance {

  selectedSeverity = 'ALL';
  selectedIssue: ComplianceIssue | null = null;
  showDetailsModal = false;

  analyses: Analysis[] = [
    {
      id: 1,
      lease: 'Lease #PA-1001',
      property: 'Greenwood Apartments',
      score: 94,
      issues: 1,
      status: 'COMPLETED',
      analyzedDate: 'Jul 20, 2026'
    },
    {
      id: 2,
      lease: 'Lease #PA-1002',
      property: 'Lakeview Towers',
      score: 86,
      issues: 2,
      status: 'COMPLETED',
      analyzedDate: 'Jul 18, 2026'
    },
    {
      id: 3,
      lease: 'Lease #PA-1003',
      property: 'Palm Heights',
      score: 72,
      issues: 3,
      status: 'REVIEW REQUIRED',
      analyzedDate: 'Jul 16, 2026'
    }
  ];

  issues: ComplianceIssue[] = [
    {
      id: 1,
      title: 'Non-standard utility responsibility clause',
      lease: 'Lease #PA-1003',
      property: 'Palm Heights',
      clause:
        'Tenant shall be responsible for all utility-related repairs regardless of cause.',
      severity: 'HIGH',
      status: 'PENDING',
      category: 'Utility Clause',
      recommendation:
        'Review this clause against local rental regulations. Maintenance responsibilities may require clarification.'
    },
    {
      id: 2,
      title: 'Move-in inspection deadline detected',
      lease: 'Lease #PA-1002',
      property: 'Lakeview Towers',
      clause:
        'A structural move-in inspection must be completed within 30 days of occupancy.',
      severity: 'MEDIUM',
      status: 'PENDING',
      category: 'Inspection Deadline',
      recommendation:
        'Schedule the required inspection before the identified deadline.'
    },
    {
      id: 3,
      title: 'Maintenance response period unclear',
      lease: 'Lease #PA-1003',
      property: 'Palm Heights',
      clause:
        'Landlord shall respond to maintenance requests within a reasonable period.',
      severity: 'MEDIUM',
      status: 'REVIEWED',
      category: 'Maintenance',
      recommendation:
        'Consider defining a specific maintenance response timeframe.'
    },
    {
      id: 4,
      title: 'Minor notice-period inconsistency',
      lease: 'Lease #PA-1001',
      property: 'Greenwood Apartments',
      clause:
        'Written notice should be provided prior to scheduled maintenance.',
      severity: 'LOW',
      status: 'REVIEWED',
      category: 'Notice Period',
      recommendation:
        'Verify that the notice period aligns with property policy.'
    }
  ];

  get filteredIssues(): ComplianceIssue[] {

    if (this.selectedSeverity === 'ALL') {
      return this.issues;
    }

    return this.issues.filter(
      issue => issue.severity === this.selectedSeverity
    );
  }

  get highRiskCount(): number {
    return this.issues.filter(
      issue => issue.severity === 'HIGH'
    ).length;
  }

  get pendingCount(): number {
    return this.issues.filter(
      issue => issue.status === 'PENDING'
    ).length;
  }

  get reviewedCount(): number {
    return this.issues.filter(
      issue => issue.status === 'REVIEWED'
    ).length;
  }

  onSeverityChange(event: Event): void {

    this.selectedSeverity =
      (event.target as HTMLSelectElement).value;
  }

  openIssue(issue: ComplianceIssue): void {

    this.selectedIssue = issue;

    this.showDetailsModal = true;
  }

  closeIssue(): void {

    this.showDetailsModal = false;

    this.selectedIssue = null;
  }

  markReviewed(): void {

    if (!this.selectedIssue) {
      return;
    }

    const issue = this.issues.find(
      item => item.id === this.selectedIssue?.id
    );

    if (issue) {
      issue.status = 'REVIEWED';
    }

    this.closeIssue();
  }

  runAnalysis(): void {

    alert(
      'AI analysis will be connected to the Spring Boot compliance API during backend integration.'
    );
  }

  getScoreClass(score: number): string {

    if (score >= 90) {
      return 'excellent';
    }

    if (score >= 80) {
      return 'good';
    }

    return 'warning';
  }
}