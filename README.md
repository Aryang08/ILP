# Servease Full-Stack Role-Based Application

This repository now contains:
- `servease-backend`: Java Spring Boot REST API with session auth + role checks.
- `servease-frontend`: Angular app with login/logout, guards, role dashboards, profile, and admin user management.

## Roles Supported
- `ADMIN`
- `CUSTOMER`
- `SUPERVISOR`
- `TECHNICIAN`

## Backend highlights
- Auth endpoints:
  - `POST /auth/login?userId=&password=`
  - `POST /auth/logout`
  - `GET /auth/session/validate`
- User registration + management via `/user`
- Admin-only user listing/update/delete in `UserController`
- In-memory session handling using `sessionId` header
- PostgreSQL via environment-configurable datasource properties

## Frontend highlights
- Angular route protection with:
  - Auth guard (must be logged in)
  - Role guard (must match route role)
- Separate pages:
  - `/admin`
  - `/customer`
  - `/supervisor`
  - `/technician`
  - `/profile`
  - `/admin/users`
- Session storage + interceptor for attaching `sessionId`
- Basic API error interceptor handling unauthorized sessions

## Local setup

### 1) PostgreSQL
```bash
createdb servease_db
```
Or configure your own DB and export:
```bash
export DB_URL=jdbc:postgresql://localhost:5432/your_db
export DB_USERNAME=your_user
export DB_PASSWORD=your_password
```

### 2) Run backend
```bash
cd servease-backend
mvn clean install
mvn spring-boot:run
```
Backend runs at `http://localhost:8080` by default.

### 3) Run frontend
```bash
cd servease-frontend
npm install
npm start
```
Frontend runs at `http://localhost:4200` and talks to `http://localhost:8080`.

## Notes
- `sessionId` is sent as an HTTP header for protected requests.
- Admin user management page calls backend `/user` API and is role-protected in frontend and backend.
