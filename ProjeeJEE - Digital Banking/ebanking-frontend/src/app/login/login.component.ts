import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SecurityService} from "../services/security.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!:FormGroup;
  submitting=false;
  isConneted?:boolean;
  login?=localStorage.getItem("isLogin");
  constructor( private fb:FormBuilder, private securityService:SecurityService, private router:Router) {

  }

  ngOnInit(): void {
    this.loginForm=this.fb.group({
      username: this.fb.control("", [
        Validators.required, Validators.minLength(3)
      ]),
      password: this.fb.control("", [
        Validators.required, Validators.minLength(4)
      ])
    });

        this.isConneted= localStorage.length.valueOf()>0?true:false;
        if(this.isConneted) this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate([this.router.url]);
        });;



  }

  handleLoginSubmit() {
      this.submitting= true;
      this.securityService.loginRequest(this.loginForm.value.username,this.loginForm.value.password)
        .then(bol=>{
          if(bol){
            alert("Successfully logged-in !"+bol);
            this.router.navigate(["/"])

          }
        }).catch(any=>{
          alert("incorrect username or password !")
      }).finally(()=>{
        this.submitting=false
      })
  }
}
