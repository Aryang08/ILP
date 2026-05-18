import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="login-wrapper">
      <div class="login-card">
        <div class="login-header">
          <div class="brand-icon">🔧</div>
          <h1>ServEase</h1>
          <p class="subtitle">Sign in to your account</p>
        </div>

        <form [formGroup]="loginForm" (ngSubmit)="onLogin()">
          <div class="form-group">
            <label for="userId">User ID <span class="required">*</span></label>
            <input id="userId" type="text" formControlName="userId" placeholder="Enter your User ID"
                   [class.input-error]="loginForm.get('userId')?.touched && loginForm.get('userId')?.invalid">
            <span class="error-msg" *ngIf="loginForm.get('userId')?.touched && loginForm.get('userId')?.hasError('required')">
              User ID is required
            </span>
          </div>

          <div class="form-group">
            <label for="password">Password <span class="required">*</span></label>
            <div class="password-wrap">
              <input [type]="showPassword ? 'text' : 'password'" id="password" formControlName="password"
                     placeholder="Enter your password"
                     [class.input-error]="loginForm.get('password')?.touched && loginForm.get('password')?.invalid">
              <button type="button" class="toggle-pw" (click)="showPassword = !showPassword">
                {{ showPassword ? '🙈' : '👁️' }}
              </button>
            </div>
            <span class="error-msg" *ngIf="loginForm.get('password')?.touched && loginForm.get('password')?.hasError('required')">
              Password is required
            </span>
          </div>

          <div class="error-alert" *ngIf="errorMessage">
            ⚠️ {{ errorMessage }}
          </div>

          <button type="submit" class="btn-login" [disabled]="loginForm.invalid || isLoading">
            <span *ngIf="!isLoading">Sign In</span>
            <span *ngIf="isLoading" class="spinner"></span>
          </button>
        </form>

        <div class="login-footer">
          <a routerLink="/reset-password" class="forgot-link">Forgot password?</a>
        </div>
      </div>

      <footer class="page-footer">
        <p>© 2026 ServEase Services</p>
      </footer>
    </div>
  `,
  styles: [`
    .login-wrapper {
      min-height: 100vh;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      background: linear-gradient(135deg, #1d3557 0%, #2c4f8a 50%, #457b9d 100%);
      padding: 20px;
    }

    .login-card {
      background: #fff;
      border-radius: 16px;
      padding: 40px;
      width: 100%;
      max-width: 420px;
      box-shadow: 0 20px 60px rgba(0, 0, 0, 0.25);
      animation: fadeIn 0.5s ease;
    }

    .login-header {
      text-align: center;
      margin-bottom: 32px;
    }

    .brand-icon {
      font-size: 40px;
      margin-bottom: 8px;
    }

    .login-header h1 {
      font-size: 28px;
      font-weight: 700;
      color: #1d3557;
      margin: 0;
    }

    .subtitle {
      color: #888;
      font-size: 14px;
      margin: 6px 0 0;
    }

    .form-group {
      margin-bottom: 20px;
    }

    label {
      display: block;
      font-size: 13px;
      font-weight: 600;
      color: #444;
      margin-bottom: 6px;
    }

    .required { color: #e74c3c; }

    input {
      width: 100%;
      padding: 12px 14px;
      border: 1.5px solid #ddd;
      border-radius: 8px;
      font-size: 14px;
      transition: border-color 0.2s;
      box-sizing: border-box;
    }

    input:focus {
      outline: none;
      border-color: #2c4f8a;
      box-shadow: 0 0 0 3px rgba(44, 79, 138, 0.1);
    }

    input.input-error {
      border-color: #e74c3c;
    }

    .password-wrap {
      position: relative;
    }

    .toggle-pw {
      position: absolute;
      right: 12px;
      top: 50%;
      transform: translateY(-50%);
      background: none;
      border: none;
      cursor: pointer;
      font-size: 16px;
    }

    .error-msg {
      display: block;
      color: #e74c3c;
      font-size: 12px;
      margin-top: 4px;
    }

    .error-alert {
      background: #fde8e8;
      color: #c0392b;
      padding: 10px 14px;
      border-radius: 8px;
      font-size: 13px;
      margin-bottom: 16px;
      text-align: center;
    }

    .btn-login {
      width: 100%;
      padding: 13px;
      background: linear-gradient(135deg, #2c4f8a, #1d3557);
      color: #fff;
      border: none;
      border-radius: 8px;
      font-size: 15px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s ease;
    }

    .btn-login:hover:not(:disabled) {
      background: linear-gradient(135deg, #1d3557, #2c4f8a);
      box-shadow: 0 4px 15px rgba(44, 79, 138, 0.4);
      transform: translateY(-1px);
    }

    .btn-login:disabled {
      opacity: 0.6;
      cursor: not-allowed;
    }

    .spinner {
      display: inline-block;
      width: 18px;
      height: 18px;
      border: 2px solid rgba(255,255,255,0.3);
      border-top: 2px solid #fff;
      border-radius: 50%;
      animation: spin 0.8s linear infinite;
    }

    .login-footer {
      text-align: center;
      margin-top: 16px;
    }

    .forgot-link {
      color: #2c4f8a;
      font-size: 13px;
      text-decoration: none;
    }

    .forgot-link:hover {
      text-decoration: underline;
    }

    .page-footer {
      margin-top: 30px;
      color: rgba(255, 255, 255, 0.6);
      font-size: 12px;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    @keyframes spin {
      to { transform: rotate(360deg); }
    }

    @media (max-width: 480px) {
      .login-card { padding: 28px 20px; }
    }
  `]
})
export class LoginComponent {
  loginForm = new FormGroup({
    userId: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  showPassword = false;
  isLoading = false;
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {
    // If already logged in, redirect
    if (this.authService.isLoggedIn()) {
      this.router.navigate([this.authService.getDashboardRoute()]);
    }
  }

  onLogin(): void {
    if (this.loginForm.invalid) return;

    this.isLoading = true;
    this.errorMessage = '';

    const { userId, password } = this.loginForm.value;

    this.authService.login(userId!, password!).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.router.navigate([this.authService.getDashboardRoute()]);
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || err.error || 'Login failed. Please check your credentials.';
      }
    });
  }
}
