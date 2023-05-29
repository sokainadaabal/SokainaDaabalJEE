import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountDetails} from "../model/accountDetails.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  url= environment.host+"/bankAccount/";
  constructor(private http:HttpClient) { }
  public getAccount(accountId:String,page:number,size:number) :Observable<AccountDetails>{
    const authorizationHeader = "Bearer " + localStorage.getItem("access_token");
    return this.http.get<AccountDetails>(this.url+accountId+"/operationPage?page="+page+"&size="+size,{
      headers:{
        "Authorization":authorizationHeader
      }
    });
  }
  public debit(accountId:String,amount:number,description:String){
    let data={accountID:accountId, amount:amount,description:description};
    const authorizationHeader = "Bearer " + localStorage.getItem("access_token");
    return this.http.post(this.url+"debit",data,{
      headers:{
        "Authorization": authorizationHeader,
        "Content-Type" : "application/json"
      }
    });
  }

  public credit(accountId:String,amount:number,description:String){
    let data={accountID:accountId, amount:amount,description:description};
    const authorizationHeader = "Bearer " + localStorage.getItem("access_token");
    return this.http.post(this.url+"credit",data,{
      headers:{
        "Authorization": authorizationHeader,
        "Content-Type" : "application/json"
      }
    });
  }

  public transfer(accountId:String,amount:number,accountDestination:String){
    let data={accountSourceID:accountId,accountDestinationID:accountDestination,amount:amount};
    const authorizationHeader = "Bearer " + localStorage.getItem("access_token");
    return this.http.post(this.url+"transfer",data,{
      headers:{
        "Authorization": authorizationHeader,
        "Content-Type" : "application/json"
      }
    });
  }
}
