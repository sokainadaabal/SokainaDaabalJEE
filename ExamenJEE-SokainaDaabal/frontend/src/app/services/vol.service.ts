import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VolService {

  url= environment.host+"/reservation";
  constructor( private http:HttpClient) { }


  public getVols():Observable<Array<any>>{
    return this.http.get<Array<any>>(this.url+"/findAll");
  }
}
