import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit{

  isLoggedIn!: boolean
  role = ""

  constructor(private authSvc: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authSvc.isLoggedIn()
    console.info('>>> SuccessComponent: ngOnInit(): ', this.isLoggedIn)
    this.role = this.authSvc.getRole()
    console.info('>>> SuccessComponent: ngOnInit(): ', this.role)
  }

  logout() {
    this.authSvc.logout()
  }


}
