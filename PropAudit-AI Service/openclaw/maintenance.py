"""Send AI-derived inspection and maintenance actions to OpenClaw."""

import os
from datetime import date

import requests
from dotenv import load_dotenv
from pydantic import BaseModel

from nous_hermes.analyzer import LeaseAnalysis

load_dotenv()


class CalendarNotice(BaseModel):
    """Instruction for OpenClaw to create an external calendar notice."""

    operation: str = "CREATE_EVENT"
    title: str
    event_date: date | None
    reminder_days_before: int = 1


class MaintenanceJob(BaseModel):
    source: str = "PropAudit"
    lease_s3_uri: str
    action: str = "CREATE_INSPECTION"
    description: str
    due_date: date | None
    priority: str
    calendar_notice: CalendarNotice


def create_jobs(
    lease_s3_uri: str,
    analysis: LeaseAnalysis,
) -> list[MaintenanceJob]:
    """
    Convert AI maintenance deadlines into OpenClaw jobs.

    Invalid or hallucinated deadlines are ignored.
    """

    jobs: list[MaintenanceJob] = []

    for deadline in analysis.maintenance_deadlines:

        description = deadline.description.strip().lower()

        # Ignore hallucinated maintenance tasks
        invalid_phrases = [
            "not specified",
            "not mentioned",
            "no maintenance",
            "no maintenance deadline",
            "maintenance deadlines are not specified",
            "maintenance deadline is not specified",
            "none specified",
            "unknown",
            "n/a",
        ]

        if (
            not description
            or any(text in description for text in invalid_phrases)
        ):
            print(
                f"Skipping invalid maintenance job: "
                f"{deadline.description}"
            )
            continue

        jobs.append(
            MaintenanceJob(
                lease_s3_uri=lease_s3_uri,
                description=deadline.description,
                due_date=deadline.due_date,
                priority=deadline.priority,
                calendar_notice=CalendarNotice(
                    title=f"Property inspection: {deadline.description}",
                    event_date=deadline.due_date,
                ),
            )
        )

    return jobs


def submit_jobs(jobs: list[MaintenanceJob]) -> None:
    """
    Submit jobs to the configured OpenClaw webhook.

    If no webhook is configured, skip gracefully.
    """

    if not jobs:
        print("No maintenance jobs to submit.")
        return

    endpoint = os.getenv("OPENCLAW_WEBHOOK_URL")

    if not endpoint:
        print("⚠ OpenClaw skipped: OPENCLAW_WEBHOOK_URL not configured.")
        return

    response = requests.post(
        endpoint,
        json=[job.model_dump(mode="json") for job in jobs],
        timeout=20,
    )

    response.raise_for_status()

    print(f"Submitted {len(jobs)} maintenance job(s) to OpenClaw.")