import { Component, OnInit } from '@angular/core';
import {Subscription} from "rxjs";
import {SecurityService} from "../services/security.service";
import {User} from "../model/user.model";
import {Router} from "@angular/router";


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  user ?: User;
  userRole ="";
  isAdmin=localStorage.getItem("isAdmin")
  AdminRole:boolean=this.isAdmin=="true"?true:false;
  userSub$ ?:Subscription; constructor( private securityService:SecurityService, private router:Router) {
    this.userSub$ = this.securityService.userSubject.subscribe({
      next: user=>{
        this.user = user;
        this.userRole = user?.role.find(e=>e.roleName=="ADMIN")!=undefined?"ADMIN":"USER";
      },
      error: (err)=>{
        console.error(err)
      }
    })
  }
  ngOnInit(): void {
    this.securityService.getUser()

    this.user = this.securityService.user;
    this.userRole = this.securityService.user?.role.find(e=>e.roleName=="ADMIN")!=undefined?"ADMIN":"USER";
  }

  logout(){
    this.securityService.logout();
    localStorage.clear();
    this.router.navigate(['/']);
  }

  ngOnDestroy(): void {
    this.userSub$?.unsubscribe();
  }

}
