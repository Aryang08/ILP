export interface User {
  userId: string;
  name: string;
  email: string;
  phone: string;
  role: string;
}

export interface LoginRequest {
  userId: string;
  password: string;
}

export interface LoginResponse {
  sessionId: string;
  role: string;
  userId: string;
  name: string;
  email: string;
}

export interface SessionInfo {
  userId: string;
  role: string;
  name: string;
  email: string;
}
