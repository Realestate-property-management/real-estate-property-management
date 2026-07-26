# PropAudit — Python GenAI code

This folder contains only the GenAI developer scope:

1. `langchain/`: download a lease PDF from S3, extract text, and create LangChain chunks.
2. `nous_hermes/`: use Nous Hermes through Ollama to identify unusual clauses, risks, and maintenance deadlines.
3. `openclaw/`: create structured maintenance or inspection jobs through an OpenClaw workflow webhook. Every job includes a `calendar_notice` (`CREATE_EVENT`, event date, and one-day reminder) for OpenClaw to send to the external calendar app.

There are no React components, Spring Boot controllers, database writes, AWS bucket setup, or deployment files here.

## Setup

```bash
cd gen-ai-codes
python -m venv .venv
.venv\Scripts\activate
pip install -e .
copy .env.example .env
```

Set the values in `.env`. AWS credentials are supplied through the normal AWS credential chain (for example, an IAM role or `aws configure`).

Pull and serve the local model with Ollama:

```bash
ollama pull nous-hermes2
ollama serve
```

## Use from the backend team

```python
from pipeline import process_lease

result = process_lease("s3://propaudit-leases/lease-1001.pdf")
# Backend stores/returns result.model_dump()
```

`process_lease` returns extracted text, chunks, lease analysis, and submitted maintenance jobs. It does not save data or expose an HTTP API.

## Run directly

After installing dependencies and configuring `.env`, run:

```bash
uvicorn app:app --host 0.0.0.0 --port 8000
```

Spring Boot calls the GenAI service with:

```http
POST http://localhost:8000/process-lease
Content-Type: application/json

{"s3_uri":"s3://propaudit-leases/lease-1001.pdf"}
```

The API returns extracted text, chunks, AI analysis, and OpenClaw calendar-notice jobs as JSON. Use `GET /health` for a health check. Interactive API documentation is available at `http://localhost:8000/docs` while the service is running.

## Scan-only PDFs

`pypdf` reads embedded PDF text. An image-only scanned agreement needs OCR first (such as Amazon Textract); pass the OCR text to `analyze_lease` afterward.
