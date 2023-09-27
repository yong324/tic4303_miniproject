import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) {}

  addEmail(email: string) {
      localStorage.setItem('email', email)
  }

  addToken(token: string) {
      localStorage.setItem('token', token)
  }

  isLoggedIn(){
      return !!this.getToken() && !this.isTokenExpired()
  }

  getToken(){
      return localStorage.getItem('token');
  }
  
  getEmail(){
      return localStorage.getItem('email');
  }

  isTokenExpired(){
      const token: string=this.getToken()??"";
      if (!token) {
          return false;
      }
      const tokenSplit:string=token.split('.')[1];
      const decodedString:string=atob(tokenSplit);
      const jsonString=JSON.parse(decodedString);
      const expiry = (jsonString).exp;
      return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }

  logout(){
      localStorage.removeItem("email")
      localStorage.removeItem("token")
      localStorage.removeItem("role")
      this.router.navigate(['/auth/login'])
   }

   getRole(){
      const helper = new JwtHelperService();
      const decodedToken = helper.decodeToken(this.getToken()??"");
      console.info('>>> AuthSvc: getRole() decodedToken: ', decodedToken)
      if (decodedToken) {
          const roleStr = decodedToken['role']
          const role = roleStr.substring(1, roleStr.length - 1)
          console.info('>>> AuthSvc: getRole() role: ', role)
          return role; 
      }
      return "";
    }

    addRole(role: string){
      localStorage.setItem('role', role)
    }
    
}
