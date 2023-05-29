import {Account} from "./account.model";

export interface AccountDetails{
  accountId:string;
  balance:number;
  currentPage:number;
  totalePage:number;
  pageSize:number;
  type:string;
  accountOperationDTOS:Account[];

}
