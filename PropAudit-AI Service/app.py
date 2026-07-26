from fastapi import FastAPI, HTTPException, UploadFile, File
from pydantic import BaseModel, Field
import traceback

from pipeline import process_lease, process_uploaded_lease

from dotenv import load_dotenv
import os
import boto3
from typing import Any


load_dotenv()

print("AWS_ACCESS_KEY_ID =", os.getenv("AWS_ACCESS_KEY_ID"))
print("AWS_DEFAULT_REGION =", os.getenv("AWS_DEFAULT_REGION"))
print("Credentials =", boto3.Session().get_credentials())

app = FastAPI(
    title="PropAudit GenAI Service",
    version="0.1.0",
)


class ProcessLeaseRequest(BaseModel):
    s3_uri: str = Field(
        pattern=r"^s3://[^/]+/.+",
        examples=["s3://propaudit-lease-documents/lease.pdf"],
    )


@app.get("/health")
def health():
    return {"status": "ok"}


@app.post("/process-lease")
def process_lease_endpoint(request: ProcessLeaseRequest):
    try:
        result = process_lease(request.s3_uri)
        return result.model_dump(mode="json")

    except (ValueError, RuntimeError) as error:
        raise HTTPException(
            status_code=422,
            detail=str(error),
        )

    except Exception as error:
        print("\n========== UNEXPECTED ERROR ==========")
        traceback.print_exc()
        print("======================================\n")

        raise HTTPException(
            status_code=500,
            detail=str(error),
        )


@app.post("/process-lease-upload")
async def process_lease_upload(file: UploadFile = File(...)):
    try:
        pdf_bytes = await file.read()

        result = process_uploaded_lease(pdf_bytes)

        return result.model_dump(mode="json")

    except (ValueError, RuntimeError) as error:
        raise HTTPException(
            status_code=422,
            detail=str(error),
        )

    except Exception as error:
        print("\n========== UNEXPECTED ERROR ==========")
        traceback.print_exc()
        print("======================================\n")

        raise HTTPException(
            status_code=500,
            detail=str(error),
        )


@app.post("/webhook")
def openclaw_webhook(jobs: list[dict[str, Any]]):
    print("\n========== OPENCLAW WEBHOOK ==========")

    for job in jobs:
        print(job)

    print("======================================\n")

    return {
        "status": "success",
        "received_jobs": len(jobs)
    }