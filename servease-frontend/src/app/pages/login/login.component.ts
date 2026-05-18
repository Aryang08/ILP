import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone:true,
  imports:[ReactiveFormsModule,CommonModule],
  template:`<div class='container'><div class='card'><h2>Servease Login</h2>
  <form [formGroup]='form' (ngSubmit)='doLogin()'>
  <input formControlName='userId' placeholder='User ID'><div class='error' *ngIf='form.controls.userId.touched && form.controls.userId.invalid'>User ID is required</div><br>
  <input formControlName='password' type='password' placeholder='Password'><div class='error' *ngIf='form.controls.password.touched && form.controls.password.invalid'>Password must be min 6 chars</div><br>
  <button [disabled]='form.invalid'>Login</button></form><p class='error' *ngIf='error'>{{error}}</p></div></div>`
})
export class LoginPageComponent{
  form=this.fb.group({userId:['',[Validators.required]],password:['',[Validators.required,Validators.minLength(6)]]});
  error='';
  constructor(private fb:FormBuilder,private auth:AuthService,private router:Router){}
  doLogin(){if(this.form.invalid){this.form.markAllAsTouched();return;} const {userId,password}=this.form.getRawValue();
    this.auth.login(userId!,password!).subscribe({next:r=>{this.auth.saveSession(r);this.router.navigate(['/'+r.role.toLowerCase()]);},error:()=>this.error='Invalid credentials'});
  }
}
