import { Component, OnInit } from '@angular/core';
import {CustomerService} from "../services/customer.service";
import {catchError, map, Observable, retry, throwError} from "rxjs";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  customers!:Observable<Array<Customer>>;
  errorMessage!:String;
  searchformGroup!:FormGroup | undefined;
  isAdmin=localStorage.getItem("isAdmin")
  AdminRole:boolean=this.isAdmin=="true"?true:false;
  constructor(private customerService:CustomerService, private formBuilder:FormBuilder, private router:Router) { }

  ngOnInit(): void {
    this.searchformGroup=this.formBuilder.group({
     keyword:this.formBuilder.control("")
    });
     this.customers= this.customerService.getCustomers().pipe(
       catchError(err => {
         this.errorMessage= err.message;
         return throwError(err);
       })
     );
     this.handleSearchCustomer();
  }

  handleSearchCustomer() {
      let kw = this.searchformGroup?.value.keyword;
      this.customers=this.customerService.searchCustomer(kw).pipe(
      catchError(err => {
        this.errorMessage= err.message;
        return throwError(err);
      })
    );
  }

  handleDeleteCustomer(customer:Customer) {
       let conf= confirm("Are you sure?");
       if(!conf) return;
       this.customerService.deleteCustomer(customer).subscribe({
         next: data => {
           this.customers= this.customers.pipe(
             map(data=>{
               return data;
             })
           );
         },error:(err)=>{
            console.log(err)
         }
       })
  }

  handleCustomerAccounts(customer: Customer) {
     this.router.navigateByUrl("/customer-accounts/"+customer.id,{state:customer});
  }
}
