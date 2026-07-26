"""Read lease PDFs from S3 and prepare them for an LLM."""

import os
from io import BytesIO
from urllib.parse import urlparse

import boto3
from botocore.exceptions import (
    NoCredentialsError,
    ClientError,
)
from dotenv import load_dotenv
from langchain_text_splitters import RecursiveCharacterTextSplitter
from pypdf import PdfReader

# -------------------------
# Load Environment Variables
# -------------------------
load_dotenv()

AWS_ACCESS_KEY_ID = os.getenv("AWS_ACCESS_KEY_ID")
AWS_SECRET_ACCESS_KEY = os.getenv("AWS_SECRET_ACCESS_KEY")
AWS_DEFAULT_REGION = os.getenv("AWS_DEFAULT_REGION")

print("====================================")
print("AWS_ACCESS_KEY_ID :", AWS_ACCESS_KEY_ID)
print("AWS_DEFAULT_REGION:", AWS_DEFAULT_REGION)
print("Credentials Loaded:", boto3.Session().get_credentials())
print("====================================")

# -------------------------
# Create S3 Client
# -------------------------
s3 = boto3.client(
    "s3",
    aws_access_key_id=AWS_ACCESS_KEY_ID,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY,
    region_name=AWS_DEFAULT_REGION,
)


# ===========================================================
# Read PDF from Amazon S3
# ===========================================================
def extract_s3_pdf_text(s3_uri: str) -> str:
    """
    Download a PDF from S3 and extract text.
    """

    parsed = urlparse(s3_uri)

    if parsed.scheme != "s3":
        raise ValueError(
            "Invalid S3 URI. Example: s3://bucket-name/file.pdf"
        )

    bucket = parsed.netloc
    key = parsed.path.lstrip("/")

    print("\n========== S3 REQUEST ==========")
    print("Bucket :", bucket)
    print("Key    :", key)
    print("================================\n")

    try:

        response = s3.get_object(
            Bucket=bucket,
            Key=key,
        )

    except NoCredentialsError:
        raise RuntimeError(
            "AWS credentials not found."
        )

    except ClientError as error:

        code = error.response["Error"]["Code"]

        if code == "NoSuchBucket":
            raise RuntimeError(f"S3 bucket '{bucket}' not found.")

        if code == "NoSuchKey":
            raise RuntimeError(f"S3 object '{key}' not found.")

        if code == "AccessDenied":
            raise RuntimeError(
                f"Access denied while reading '{key}' from bucket '{bucket}'."
            )

        raise RuntimeError(str(error))

    reader = PdfReader(BytesIO(response["Body"].read()))

    text = "\n".join(
        page.extract_text() or ""
        for page in reader.pages
    ).strip()

    if not text:
        raise ValueError(
            "The PDF has no embedded text. OCR is required."
        )

    print("PDF successfully read from S3.")
    print("Characters extracted:", len(text))

    return text


# ===========================================================
# Read Uploaded PDF
# ===========================================================
def extract_pdf_text_from_bytes(pdf_bytes: bytes) -> str:
    """
    Extract text from uploaded PDF.
    """

    reader = PdfReader(BytesIO(pdf_bytes))

    text = "\n".join(
        page.extract_text() or ""
        for page in reader.pages
    ).strip()

    if not text:
        raise ValueError(
            "The PDF has no embedded text. OCR is required."
        )

    return text


# ===========================================================
# Split into LangChain Chunks
# ===========================================================
def make_text_chunks(
    text: str,
    chunk_size: int = 2000,
    chunk_overlap: int = 200,
) -> list[str]:

    splitter = RecursiveCharacterTextSplitter(
        chunk_size=chunk_size,
        chunk_overlap=chunk_overlap,
        separators=[
            "\n\n",
            "\n",
            ". ",
            " ",
            "",
        ],
    )

    return splitter.split_text(text)