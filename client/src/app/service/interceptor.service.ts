import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InterceptorService implements HttpInterceptor{

  constructor() {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {

    // if (localStorage.getItem('email') && localStorage.getItem('token')) {
    //   req = req.clone({
    //     setHeaders: {
    //       Authorization: `Bearer ${localStorage.getItem('token')}`
    //     }
    //   })
    // }

    if (localStorage.getItem('token')) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      })
    }
    
    return next.handle(req);
  }
  
}
