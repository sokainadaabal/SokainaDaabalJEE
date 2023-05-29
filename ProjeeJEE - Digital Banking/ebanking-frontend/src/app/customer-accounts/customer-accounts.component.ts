import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../model/customer.model";
import {Observable} from "rxjs";
import {Account} from "../model/account.model";
import {CustomerService} from "../services/customer.service";

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrls: ['./customer-accounts.component.css']
})
export class CustomerAccountsComponent implements OnInit {

  customerId!:String;
  customer! : Customer;
  listAccount!:Observable<Array<Account>>;
  constructor(private customerService:CustomerService,private route:ActivatedRoute,private  router:Router) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
  }

  ngOnInit(): void {
   this.customerId=this.route.snapshot.params['id'];
   this.handleCustomerAccounts();
  }

  handleCustomerAccounts(){
    this.listAccount = this.customerService.getCustomerAccounts(this.customerId);
  }

  handleAccount(account: Account) {
this.router.navigateByUrl("/accounts",{state:account})
  }
}
