"""Structured lease analysis using a locally hosted Nous Hermes model."""

import os
from datetime import date

from dotenv import load_dotenv
from langchain_ollama import ChatOllama
from pydantic import BaseModel, Field

load_dotenv()


class MaintenanceDeadline(BaseModel):
    description: str
    due_date: date | None = None
    priority: str = Field(pattern="^(LOW|MEDIUM|HIGH)$")


class LeaseAnalysis(BaseModel):
    risk: str = Field(pattern="^(LOW|MEDIUM|HIGH)$")
    non_standard_clauses: list[str]
    compliance_issues: list[str]
    maintenance_deadlines: list[MaintenanceDeadline]


SYSTEM_PROMPT = """
You are an expert lease compliance analyst.

Your ONLY task is to extract information that is explicitly present in the lease.

==========================
STRICT RULES
==========================

1. NEVER invent facts.
2. NEVER guess dates.
3. NEVER infer deadlines.
4. NEVER fabricate maintenance schedules.
5. NEVER use outside legal knowledge.
6. NEVER assume blank fields contain values.
7. If information is missing, do NOT make it up.
8. Return ONLY valid JSON.
9. Do not include markdown.
10. Do not explain your reasoning.

==========================
RISK
==========================

Assign:

LOW
- Standard lease
- Few or no unusual clauses

MEDIUM
- Some clauses favor one party
- Minor legal or compliance concerns

HIGH
- Multiple risky clauses
- Significant legal/compliance concerns
- Strong restrictions on one party

==========================
NON-STANDARD CLAUSES
==========================

Return ONLY clauses explicitly written that are unusual,
restrictive, or risky.

Do not rewrite them.

==========================
COMPLIANCE ISSUES
==========================

Return ONLY compliance issues directly supported by
the lease.

If none exist:

[]

==========================
MAINTENANCE DEADLINES
==========================

Only include obligations involving:

- Maintenance
- Repairs
- Painting
- Whitewashing
- Inspection
- Insurance renewal
- Servicing
- Safety checks

ONLY when the lease explicitly specifies:

• an exact date
• every X months
• every X years
• annually
• quarterly
• monthly
• before a specific date

Examples:

✔ Paint every 3 years
✔ Inspection before 01-Aug-2027
✔ Insurance renewed annually

These should become maintenance_deadlines.

General obligations like:

- Maintain the property.
- Keep premises clean.
- Use reasonable care.

are NOT deadlines.

DO NOT create deadlines from these.

If no maintenance deadline exists:

"maintenance_deadlines": []

If there is a maintenance obligation but NO date:

{
  "description": "...",
  "due_date": null,
  "priority": "LOW"
}

==========================
DATES
==========================

Use ISO format:

YYYY-MM-DD

If the date is missing:

null

Never invent dates.

==========================
OUTPUT
==========================

Return ONLY JSON matching the LeaseAnalysis schema.
"""


def analyze_lease(lease_text: str) -> LeaseAnalysis:
    """
    Analyse a lease using a locally running
    Nous Hermes model through Ollama.
    """

    model = ChatOllama(
        base_url=os.getenv(
            "OLLAMA_BASE_URL",
            "http://localhost:11434",
        ),
        model=os.getenv(
            "NOUS_HERMES_MODEL",
            "nous-hermes2",
        ),
        temperature=0,
        format="json",
    ).with_structured_output(LeaseAnalysis)

    return model.invoke(
        [
            ("system", SYSTEM_PROMPT),
            ("human", f"Lease agreement:\n\n{lease_text}"),
        ]
    )