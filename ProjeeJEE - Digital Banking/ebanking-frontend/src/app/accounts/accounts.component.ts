import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountService} from "../services/account.service";
import {AccountDetails} from "../model/accountDetails.model";
import {catchError, Observable, throwError} from "rxjs";
import {Account} from "../model/account.model";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  operationAccount!:Observable<AccountDetails>;
  currentPage : number =0;
  sizePage:number=5;
  accountFormGroup!:FormGroup;
  operationFormGroup!:FormGroup;
  errorMessage!:String;
  account!:Account;
  totalePage:number=0;
  constructor(private route : ActivatedRoute, private router : Router,private fb:FormBuilder,private accountService:AccountService) {
    if(this.router.getCurrentNavigation()?.extras.state){
      this.account = this.router.getCurrentNavigation()?.extras.state as Account;
      this.operationAccount= this.accountService.getAccount(this.account.id, this.currentPage, this.sizePage);
      this.operationAccount.subscribe(data=>{
        this.totalePage=data.totalePage;
      })
    }
  }

  ngOnInit(): void {

    this.accountFormGroup= this.fb.group({
      accountId:this.fb.control(null)
    });
    this.operationFormGroup= this.fb.group({
      operationType:this.fb.control(null),
      amount:this.fb.control(0),
      description:this.fb.control(''),
      accountDestination:this.fb.control('')
    })
  }

  handleSearchAccout() {
     let accountId= this.accountFormGroup.value.accountId || this.account.id;
     this.operationAccount=this.accountService.getAccount(accountId,this.currentPage,this.sizePage).pipe(
       catchError(err=>{
         this.errorMessage=err.error.message;
         return throwError(err);
       })
     );
    this.operationAccount.subscribe(data=>{
      this.totalePage=data.totalePage;
    })
  }

  gotToPage(page: number) {
    this.currentPage=page;
    this.handleSearchAccout();
  }

  handlAccountOpperation() {
    let accountId:String=this.accountFormGroup.value.accountId;
    let operationType=this.operationFormGroup.value.operationType;
    let amount=this.operationFormGroup.value.amount;
    let description=this.operationFormGroup.value.description;
    let accountDestination=this.operationFormGroup.value.accountDestination;
    if(operationType=='DEBIT'){
        this.accountService.debit(accountId,amount,description).subscribe({
          next:(data)=>{
            alert("Success Debit Operation");
            this.operationFormGroup.reset();
            this.handleSearchAccout();
          },error:(err)=>{
            console.log(err);
          }
        });
    }
    else if(operationType=='CREDIT'){
      this.accountService.credit(accountId,amount,description).subscribe({
        next:(data)=>{
          alert("Success Credit Operation");
          this.operationFormGroup.reset();
          this.handleSearchAccout();
        },error:(err)=>{
          console.log(err);
        }
      });
    }
    else if(operationType=='TRANSFER'){
      this.accountService.transfer(accountId,amount,accountDestination).subscribe({
        next:(data)=>{
          alert("Success Transfer Operation");
          this.operationFormGroup.reset();
          this.handleSearchAccout();
        },error:(err)=>{
          console.log(err);
        }
      });
    }
  }

}
