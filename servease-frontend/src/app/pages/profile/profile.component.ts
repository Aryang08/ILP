import { Component } from '@angular/core';import { CommonModule } from '@angular/common';import { AuthService } from '../../core/services/auth.service';
@Component({standalone:true,imports:[CommonModule],template:`<div class='container'><div class='card'><h3>Profile</h3><p>Role: {{auth.role}}</p><p>Session: {{auth.sessionId}}</p><button (click)='auth.logout()'>Logout</button></div></div>`})
export class ProfileComponent{constructor(public auth:AuthService){}}
