import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {VolComponent} from "./vol/vol.component";

const routes: Routes = [
  {path:'vol',component:VolComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
