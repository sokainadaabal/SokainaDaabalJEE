import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";
import {User} from "../model/user.model";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  userSubject: Subject<User | undefined>= new Subject();
  user?:User;
  refresh_token="";
  access_token="";
  role:string[]=[];
  constructor(private http:HttpClient) {
    this.access_token = localStorage.getItem("access_token") ? localStorage.getItem("access_token") + "" : "";
    this.refresh_token = localStorage.getItem("refresh_token") ? localStorage.getItem("refresh_token") + "" : "";
  }

  public loginRequest(username:string,password:string){
    return new Promise<boolean>((resolve,reject)=>{
      this.http.post<any>(environment.host+"/login",{
        username,
        password
      }).subscribe({
        next:res=>{
          this.access_token = res.access_token
          this.refresh_token = res.refresh_token
          localStorage.setItem("access_token",this.access_token)
          localStorage.setItem("refresh_token",this.refresh_token)
          this.getUser()
          resolve(true)
        },error:err => {
          this.access_token=this.refresh_token=""
          reject(false)
        }
      })
    })
  }

  public getUser() {
      if(this.access_token.length==0) return;
      const authorizationHeader = "Bearer " + this.access_token;
      this.http.get<User>(environment.host+"/V1/profile",{
       headers:{
         "Authorization":authorizationHeader
       }
      }).subscribe({
        next:res=>{
          this.user=res;
          localStorage.setItem("username",this.user.username);
          localStorage.setItem("userId",this.user.userId);
          localStorage.setItem("isLogin","true");
          // @ts-ignore
          localStorage.setItem("roleName",JSON.stringify(res.role));
          const userRoles = localStorage.getItem('roleName');
          if (userRoles){
            const userRole =JSON.parse(userRoles) as { roleName: string }[];
            this.role=userRole.map(role => role.roleName);
          }
          const isAdmin= this.role.some(role=>role=="ADMIN");
          if(isAdmin==true){
            localStorage.setItem("isAdmin","true");
          }
          this.userSubject.next(res)
        }
      ,error : err =>{
        console.error(err.message);
        this.refreshToken()
       }
      })
  }

  public refreshToken() {
    const authorizationHeader = "Bearer " + this.refresh_token
    return this.http.get<any>(environment.host + "/V1/refresh-token", {
      headers: {
        "Authorization": authorizationHeader
      }
    })
  }
  public logout(){
    this.access_token = this.refresh_token = ""
    localStorage.removeItem("access_token")
    localStorage.removeItem("refresh_token")
    localStorage.removeItem("isLogin");
    localStorage.removeItem("roleName");
    localStorage.removeItem("isAdmin")
    this.user=undefined;
    this.userSubject.next(undefined)
  }
}
