import { Component } from '@angular/core';import { CommonModule } from '@angular/common';import { RouterLink } from '@angular/router';
@Component({standalone:true,imports:[CommonModule,RouterLink],template:`<nav><a routerLink='/admin'>Admin</a><a routerLink='/admin/users'>Users</a><a routerLink='/admin/feedback'>Feedback</a><a routerLink='/admin/sales'>Sales</a></nav><div class='container'><div class='card'><h3>Feedback</h3><ul><li>Customer feedback #1</li><li>Customer feedback #2</li></ul></div></div>`})
export class AdminFeedbackComponent{}
