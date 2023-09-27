import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authSvc: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    // Check if user is logged in
    if (!this.authSvc.isLoggedIn()) {
      this.router.navigate(['auth/login']);
      return false;
    }
    
    // Check if user is an admin
    if (route.data['isAdmin'] && this.authSvc.getRole() != 'ADMIN') {
      this.router.navigate(['api/error']);
      return false;
    }
    
    return true;
  }
  
}
