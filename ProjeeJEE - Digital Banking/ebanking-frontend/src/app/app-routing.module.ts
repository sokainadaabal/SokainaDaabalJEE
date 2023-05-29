import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {AccountsComponent} from "./accounts/accounts.component";
import {CustomersComponent} from "./customers/customers.component";
import {NewCustomerComponent} from "./new-customer/new-customer.component";
import {CustomerAccountsComponent} from "./customer-accounts/customer-accounts.component";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";


const routes: Routes = [
  {path:"accounts",component:AccountsComponent},
  {path:"customers",component:CustomersComponent},
  {path:"new-customer",component:NewCustomerComponent},
  {path:"customer-accounts/:id",component:CustomerAccountsComponent},
  {path:"login",component:LoginComponent},
  {path:"",component:HomeComponent,pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
