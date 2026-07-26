.

🏢 PropAudit – Automated Rental Compliance & Maintenance Hub






















📖 Project Overview

PropAudit is an AI-powered Enterprise Property Management platform developed to automate lease compliance verification, property inspection scheduling, maintenance management, and tenant administration.

The system combines modern cloud infrastructure with Generative AI to eliminate manual review of lease agreements and automate compliance workflows.

Instead of manually reading lengthy lease contracts, identifying legal clauses, tracking maintenance deadlines, creating inspections, and notifying contractors, PropAudit performs these tasks automatically through an AI-driven backend pipeline.

The application integrates:

Spring Boot for backend services
Amazon S3 for lease document storage
Amazon RDS (MySQL) for structured data
FastAPI as the AI microservice
LangChain4j for document processing
Nous Hermes LLM for legal lease analysis
OpenClaw for automated maintenance scheduling

The project demonstrates the integration of enterprise backend development, cloud computing, database management, RESTful APIs, and Generative AI into a single automated property management platform.

🎯 Business Problem

Property management organizations process hundreds of lease agreements every month.

Traditionally, leasing teams manually perform the following tasks:

Review lease agreements
Verify legal compliance
Detect risky clauses
Identify maintenance responsibilities
Schedule inspections
Notify contractors
Maintain audit records

This manual workflow creates several challenges:

Time-consuming document review
Missed legal clauses
Delayed maintenance scheduling
Human errors
Poor compliance tracking
Lack of centralized monitoring
No automated inspection planning
Difficult audit management

As the number of properties grows, these issues significantly reduce operational efficiency and increase maintenance costs.

💡 Proposed Solution

PropAudit automates the complete lease compliance lifecycle using Artificial Intelligence.

The platform performs the following workflow automatically:

Leasing agent uploads a lease agreement.
Lease PDF is stored securely in Amazon S3.
Spring Boot triggers AI analysis.
FastAPI retrieves the document.
LangChain4j extracts and chunks lease content.
Nous Hermes analyzes legal clauses.
Compliance issues are identified.
Maintenance deadlines are extracted.
Compliance reports are stored in Amazon RDS.
Property inspections are automatically created.
Notifications are generated.
Audit logs are recorded.
OpenClaw schedules contractor maintenance.

The result is a fully automated lease compliance pipeline that reduces manual effort while improving legal accuracy and maintenance planning.

🚀 Key Features
🏢 Property Management
Register new properties
Update property details
Delete properties
Manage property status
View complete property information
Property dashboard statistics
👥 Tenant Management
Tenant registration
Tenant profile management
Contact information management
Tenant status tracking
Tenant history
📄 Lease Management
Create lease agreements
Update lease information
Delete lease records
Upload lease PDF documents
Download lease documents
Lease document management using Amazon S3
Lease analysis status monitoring
🤖 AI Lease Compliance Analysis
Automated lease review
Compliance risk detection
Identification of non-standard clauses
Legal clause verification
Maintenance deadline extraction
AI-generated compliance summary
Risk classification
🔍 Inspection Management
Automatic inspection generation
Inspection scheduling
Property inspection tracking
Inspection status management
Inspection priority assignment
🔔 Notification Management
AI analysis completion notifications
Inspection notifications
Maintenance alerts
User notification history
📝 Audit Logging
User activity tracking
Lease upload tracking
AI processing history
Inspection activity logging
Compliance workflow logging
📊 Dashboard

Real-time dashboard displaying:

Total Properties
Total Tenants
Active Leases
Pending Maintenance Requests
Upcoming Inspections
Compliance Issues
🌟 Project Highlights

✔ Enterprise Spring Boot Backend

✔ RESTful API Architecture

✔ Hibernate ORM & JPA

✔ Amazon S3 Cloud Storage

✔ Amazon RDS Integration

✔ FastAPI AI Microservice

✔ LangChain4j Integration

✔ Nous Hermes Large Language Model

✔ OpenClaw Automation

✔ AI-based Lease Compliance Detection

✔ Automated Inspection Generation

✔ Automated Notifications

✔ Audit Trail Management

✔ Dashboard Analytics

🏗 High-Level System Architecture
                    React Frontend
                           │
                           ▼
                Spring Boot Backend
                (REST APIs & Business Logic)
                           │
       ┌───────────────────┼────────────────────┐
       │                   │                    │
       ▼                   ▼                    ▼
 Amazon RDS           Amazon S3           FastAPI AI Service
(MySQL Database)     Lease Documents         (Python)
       │                                        │
       │                                        ▼
       │                              LangChain4j Pipeline
       │                                        │
       │                                        ▼
       │                                 Nous Hermes LLM
       │                                        │
       │                                        ▼
       │                               AI JSON Response
       │                                        │
       └──────────────────────► Spring Boot ◄───┘
                                   │
             ┌─────────────────────┼────────────────────┐
             │                     │                    │
             ▼                     ▼                    ▼
    Compliance Analysis     Inspection Module     Notifications
             │
             ▼
         OpenClaw
             │
             ▼
  Maintenance Scheduling & Contractor Workflow
🛠 Technology Stack
Frontend
React.js
HTML5
CSS3
JavaScript
Backend
Java 17
Spring Boot 3.x
Spring MVC
Spring Data JPA
Hibernate ORM
REST APIs
Maven
Database
MySQL
Amazon RDS
Cloud Services
Amazon S3
Amazon RDS
Generative AI
FastAPI
LangChain4j
Nous Hermes LLM
OpenClaw
Python 3.11
Development Tools
IntelliJ IDEA
Eclipse
Visual Studio Code
Postman
MySQL Workbench
Git
GitHub
Maven

📦 Project Modules

The backend is organized into multiple independent modules following a layered Spring Boot architecture. Each module is responsible for a specific business function while working together as a complete property management platform.

🏢 1. Property Management Module

This module manages residential and commercial properties within the system.

Features
Register new properties
Update property information
Delete properties
View property details
Retrieve property list
Property availability tracking
Database Table
properties
👥 2. Tenant Management Module

This module maintains tenant information associated with lease agreements.

Features
Tenant registration
Update tenant details
Delete tenant
Retrieve tenant records
Maintain tenant contact information
Database Table
tenants
📄 3. Lease Management Module

The Lease Module is the central component of PropAudit.

It manages lease agreements and integrates the complete AI workflow.

Features
Create lease
Update lease
Delete lease
Upload lease agreement PDF
Download lease document
Store lease in Amazon S3
Track AI analysis status
Maintain lease lifecycle
Database Table
leases
🤖 4. AI Compliance Analysis Module

This module integrates Generative AI for automated lease analysis.

After a lease PDF is uploaded:

Spring Boot sends the S3 URI to the FastAPI service.
LangChain4j retrieves and chunks the lease document.
Nous Hermes analyzes the legal clauses.
The AI returns:
Risk level
Compliance issues
Non-standard clauses
Maintenance deadlines

The backend processes and stores this information.

Features
AI lease analysis
Compliance score generation
Risk assessment
Legal clause detection
Analysis summary generation
Compliance report storage
Database Table
compliance_analyses
🔍 5. Inspection Module

Based on AI analysis, property inspections are automatically generated.

Features
Automatic inspection creation
Inspection scheduling
Inspection status tracking
Priority assignment
Inspection management
Database Table
inspections
🔔 6. Notification Module

The system automatically informs users whenever important events occur.

Notifications include
AI analysis completed
Inspection created
Maintenance scheduling
System alerts
Database Table
notifications
📝 7. Audit Log Module

Every important backend operation is recorded for traceability.

Logged Activities
Lease uploaded
AI analysis completed
Inspection generated
Maintenance scheduled
User actions
Database Table
audit_logs
🛠 8. Maintenance Scheduling Module

After AI detects maintenance-related clauses or deadlines, OpenClaw automation is triggered.

Responsibilities
Generate maintenance jobs
Schedule contractor activities
Track maintenance requests
Support future calendar integration
Current Status
OpenClaw integration implemented
Maintenance scheduling API connected
Ready for external contractor/calendar integration
📊 9. Dashboard Module

Provides real-time statistics across the platform.

Dashboard Metrics
Total Properties
Total Tenants
Active Leases
Pending Maintenance Requests
Upcoming Inspections
Compliance Issues

Example response:

{
  "totalProperties": 2,
  "totalTenants": 2,
  "activeLeases": 2,
  "pendingMaintenance": 4,
  "upcomingInspections": 3,
  "complianceIssues": 8
}
☁️ 10. Cloud Integration Module
Amazon S3

Stores:

Lease agreement PDFs
Uploaded documents

Operations:

Upload
Download
Delete
Amazon RDS

Stores all structured application data, including:

Users
Properties
Tenants
Leases
Compliance reports
Inspections
Notifications
Audit logs
# ⚙️ Installation & Setup

## Prerequisites

Before running the project, ensure the following software is installed:

### Backend
- Java 17
- Maven 3.9+
- Spring Boot 3.x

### Database
- MySQL 8.x
- Amazon RDS (Production)

### Cloud
- AWS Account
- Amazon S3 Bucket

### Generative AI Stack
- Python 3.11+
- FastAPI
- Ollama
- Nous Hermes Model
- LangChain
- boto3
- OpenClaw Service

### Development Tools
- IntelliJ IDEA / Eclipse
- VS Code
- MySQL Workbench
- Git
- Postman

---

# 🚀 Backend Setup

Clone the repository

```bash
git clone https://github.com/<username>/PropAudit-Backend.git
```

Move into the project

```bash
cd PropAudit-Backend
```

Install dependencies

```bash
mvn clean install
```

Run the application

```bash
mvn spring-boot:run
```

Backend will start on

```
http://localhost:8080
```

---

# 🤖 FastAPI AI Service Setup

Navigate to AI service

```bash
cd GenAI-Service
```

Create Virtual Environment

Windows

```bash
python -m venv venv
```

Activate

```bash
venv\Scripts\activate
```

Install requirements

```bash
pip install -r requirements.txt
```

Run FastAPI

```bash
uvicorn main:app --reload
```

FastAPI runs on

```
http://localhost:8000
```

Swagger

```
http://localhost:8000/docs
```

---

# 🧠 Ollama Setup

Install Ollama

https://ollama.ai/download

Pull Nous Hermes Model

```bash
ollama pull nous-hermes
```

Verify

```bash
ollama list
```

Run model

```bash
ollama run nous-hermes
```

Default Endpoint

```
http://localhost:11434
```

---

# 📦 Python Dependencies

```txt
fastapi
uvicorn
langchain
langchain-community
langchain-text-splitters
boto3
pydantic
requests
python-multipart
pymupdf
```

Install

```bash
pip install -r requirements.txt
```

---

# ☁ Amazon S3 Configuration

Create an S3 bucket

Example

```
propaudit-lease-documents
```

Folder Structure

```
lease-documents/
```

Grant permissions

- PutObject
- GetObject
- DeleteObject

---

# 🗄 Amazon RDS Configuration

Create MySQL RDS instance

Database

```
propaudit
```

Configure

- Username
- Password
- Security Group
- Public Access (if required)

Update Spring Boot

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

---

# 🔐 Environment Variables

## Spring Boot

```properties
spring.datasource.url=

spring.datasource.username=

spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

aws.accessKey=

aws.secretKey=

aws.region=

aws.bucketName=

fastapi.baseUrl=http://localhost:8000

openclaw.baseUrl=http://localhost:9000
```

---

## FastAPI

```env
AWS_ACCESS_KEY=

AWS_SECRET_KEY=

AWS_BUCKET=

OLLAMA_BASE_URL=http://localhost:11434
```

---

# 📋 Deployment Architecture

```
React Frontend
       │
       ▼
Spring Boot Backend
       │
 ┌─────┴───────────────┐
 │                     │
 ▼                     ▼
Amazon RDS         Amazon S3
       │
       ▼
FastAPI AI Service
       │
       ▼
LangChain
       │
       ▼
Nous Hermes
       │
       ▼
Spring Boot
       │
 ┌─────┼─────────────┐
 ▼     ▼             ▼
Compliance
Inspection
Notification
Audit Log
       │
       ▼
OpenClaw
```

---

# 🧪 API Testing

All APIs were validated using Postman.

Major tested endpoints include:

- Property CRUD
- Tenant CRUD
- Lease CRUD
- Lease Upload
- Lease Download
- AI Analysis
- Compliance Analysis
- Dashboard
- Notifications
- Audit Logs
- Inspection APIs

Database verification performed using MySQL Workbench.

---

# 📊 Sample Dashboard Response

```json
{
  "totalProperties": 2,
  "totalTenants": 2,
  "activeLeases": 2,
  "pendingMaintenance": 4,
  "upcomingInspections": 3,
  "complianceIssues": 8
}
```

---

# 📷 Sample AI Response

```json
{
  "analysis": {
    "risk": "HIGH",
    "compliance_issues": [
      "Broad lessor warranty clause",
      "Transfer restriction clause"
    ],
    "maintenance_deadlines": [
      {
        "description": "Maintenance deadline missing",
        "priority": "HIGH"
      }
    ]
  }
}
```

---

# 📈 Future Scope

The platform can be further enhanced with:

- JWT Authentication
- Spring Security
- Docker
- Kubernetes
- Jenkins CI/CD
- AWS ECS Deployment
- OCR-based scanned PDF parsing
- Multi-language lease analysis
- AI chatbot for leasing agents
- Google Calendar integration
- Microsoft Outlook integration
- Email notifications
- SMS alerts
- Contractor mobile application
- Predictive maintenance using AI

---

# 📚 Skills Demonstrated

### Backend

- Spring Boot
- REST API Development
- Hibernate ORM
- Spring Data JPA
- Maven
- Exception Handling
- Layered Architecture

### Database

- MySQL
- Amazon RDS
- Entity Relationships
- Database Normalization

### Cloud

- Amazon S3
- Amazon RDS

### AI & Automation

- FastAPI
- LangChain
- Ollama
- Nous Hermes
- Prompt Engineering
- JSON Processing
- OpenClaw Integration

### DevOps

- Git
- GitHub
- Postman
- MySQL Workbench

---

# 🏆 Project Highlights

✔ Enterprise Spring Boot Backend

✔ AWS Cloud Integration

✔ Amazon S3 File Storage

✔ Amazon RDS Database

✔ AI-powered Lease Analysis

✔ LangChain Integration

✔ Nous Hermes LLM Integration

✔ FastAPI Microservice Communication

✔ Automated Compliance Analysis

✔ Automated Inspection Scheduling

✔ Notification System

✔ Audit Logging

✔ Dashboard Analytics

✔ RESTful API Design

✔ End-to-End Lease Processing Pipeline

---

# 👨‍💻 Author

## Charan Sri Hari Sai

**Backend Developer | Java Developer | Spring Boot Developer | Generative AI Integration Engineer**

### Responsibilities

- Designed backend architecture
- Developed REST APIs
- Built complete Lease Module
- Developed Property Module
- Developed Tenant Module
- Developed Inspection Module
- Developed Notification Module
- Developed Dashboard Module
- Developed Audit Log Module
- Integrated Amazon S3
- Connected Amazon RDS
- Integrated FastAPI AI Service
- Processed AI responses
- Stored compliance reports
- Automated inspection creation
- Triggered OpenClaw maintenance scheduling
- Performed API testing using Postman
- Validated database operations using MySQL Workbench

---

## ⭐ If you found this project useful, consider giving it a Star.