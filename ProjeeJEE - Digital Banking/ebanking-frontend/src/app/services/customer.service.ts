import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Customer} from "../model/customer.model";
import {Account} from "../model/account.model";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  url= environment.host+"/customer";
  constructor( private http:HttpClient) { }


  public getCustomers():Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.url+"/findAll");
  }

  public searchCustomer(keyword:string):Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.url+"/search?keyword="+keyword);
  }

  public saveCustomer(customer:Customer):Observable<Customer>{
    const authorizationHeader = "Bearer "+localStorage.getItem("access_token")
    return  this.http.post<Customer>(this.url+"/add",customer,{
      headers:{
        "Authorization":authorizationHeader
      }
    });
  }
  public deleteCustomer(customer:Customer){
    const authorizationHeader = "Bearer "+localStorage.getItem("access_token")
    return  this.http.delete(this.url+"/delete/"+customer.id,{
      headers:{
        "Authorization":authorizationHeader,
      }
    });
  }

  getCustomerAccounts(customerId: String) {
    return this.http.get<Array<Account>>(this.url+'/'+customerId+"/bankAccounts");
  }
}
