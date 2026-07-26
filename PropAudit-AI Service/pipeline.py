"""Single GenAI entry point for the backend team."""

from pydantic import BaseModel

from langchain.s3_lease_reader import (
    extract_s3_pdf_text,
    extract_pdf_text_from_bytes,
    make_text_chunks,
)
from nous_hermes.analyzer import LeaseAnalysis, analyze_lease
from openclaw.maintenance import (
    MaintenanceJob,
    create_jobs,
    submit_jobs,
)


class LeaseProcessingResult(BaseModel):
    extracted_text: str
    text_chunks: list[str]
    analysis: LeaseAnalysis
    maintenance_jobs: list[MaintenanceJob]


def process_lease_text(
    text: str,
    source: str = "uploaded-file",
) -> LeaseProcessingResult:
    """
    Process already extracted lease text.
    Used by both S3 and uploaded PDF workflows.
    """

    # Split lease into chunks
    chunks = make_text_chunks(text)

    # AI analysis
    analysis = analyze_lease(text)

    # Create maintenance jobs
    jobs = create_jobs(source, analysis)

    # Submit jobs only if OpenClaw is configured
    try:
        submit_jobs(jobs)
        print("✓ OpenClaw jobs submitted successfully.")
    except RuntimeError as e:
        print(f"⚠ OpenClaw skipped: {e}")
    except Exception as e:
        print(f"⚠ OpenClaw submission failed: {e}")

    return LeaseProcessingResult(
        extracted_text=text,
        text_chunks=chunks,
        analysis=analysis,
        maintenance_jobs=jobs,
    )


def process_lease(s3_uri: str) -> LeaseProcessingResult:
    """
    Process a lease stored in Amazon S3.
    """

    text = extract_s3_pdf_text(s3_uri)

    return process_lease_text(
        text=text,
        source=s3_uri,
    )


def process_uploaded_lease(pdf_bytes: bytes) -> LeaseProcessingResult:
    """
    Process an uploaded PDF file.
    """

    text = extract_pdf_text_from_bytes(pdf_bytes)

    return process_lease_text(
        text=text,
        source="uploaded-file",
    )