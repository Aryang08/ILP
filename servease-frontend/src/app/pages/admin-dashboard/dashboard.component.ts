import { Component } from '@angular/core';import { RouterLink } from '@angular/router';
@Component({standalone:true,imports:[RouterLink],template:`<nav><a routerLink='/admin'>Admin</a><a routerLink='/admin/users'>Users</a><a routerLink='/profile'>Profile</a></nav><div class='container grid'><div class='card'><h3>Admin Dashboard</h3><p>Manage users, roles and platform governance.</p></div><div class='card'><p>Quick link: user administration.</p></div></div>`})
export class AdminDashboardComponent{}
