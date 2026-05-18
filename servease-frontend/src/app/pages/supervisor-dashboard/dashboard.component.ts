import { Component } from '@angular/core';import { RouterLink } from '@angular/router';
@Component({standalone:true,imports:[RouterLink],template:`<nav><a routerLink='/supervisor'>Supervisor</a><a routerLink='/profile'>Profile</a></nav><div class='container grid'><div class='card'><h3>Supervisor Dashboard</h3><p>Team allocation and oversight cards.</p></div></div>`})
export class SupervisorDashboardComponent{}
