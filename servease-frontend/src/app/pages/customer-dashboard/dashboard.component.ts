import { Component } from '@angular/core';import { RouterLink } from '@angular/router';
@Component({standalone:true,imports:[RouterLink],template:`<nav><a routerLink='/customer'>Customer</a><a routerLink='/profile'>Profile</a></nav><div class='container grid'><div class='card'><h3>Customer Dashboard</h3><p>Your service requests and history.</p></div></div>`})
export class DashboardComponent{}
