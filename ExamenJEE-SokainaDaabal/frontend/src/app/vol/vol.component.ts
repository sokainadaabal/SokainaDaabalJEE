import { Component, OnInit } from '@angular/core';
import {catchError, Observable, throwError} from "rxjs";
import {VolService} from "../services/vol.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-vol',
  templateUrl: './vol.component.html',
  styleUrls: ['./vol.component.css']
})
export class VolComponent implements OnInit {
  vols!:Observable<Array<any>>;
  searchformGroup!:FormGroup|undefined;
  errorMessage!:String;
  constructor(private volservice:VolService,private formBuilder:FormBuilder) { }

  ngOnInit(): void {
    this.searchformGroup=this.formBuilder.group({
      keyword:this.formBuilder.control("")
    });
    this.vols= this.volservice.getVols().pipe(
      catchError(err => {
        this.errorMessage= err.message;
        return throwError(err);
      })
    );
  }

  handleSearchCustomer() {

  }
}
