import { Component, OnInit } from '@angular/core';
import { Interest } from '../model/model';
import { ProtectedService } from 'src/app/service/protected.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{

  interests: Interest[] = []
  email!: string
  isLoggedIn!: boolean;
  role = ""

  constructor(private protectedSvc: ProtectedService, private authSvc: AuthService, private router:Router) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authSvc.isLoggedIn()
    console.info('>>> AppComponent: checkIfLoggedIn(): ', this.isLoggedIn)
    this.role = this.authSvc.getRole()
    console.info('>>> AppComponent: checkIfLoggedIn(): ', this.role)
    this.protectedSvc.getInterests()
      .then(result => {
        console.info('>>> DashboardComponent: result: ', result)
        this.interests = result
      })
      .catch(error => {
        console.error('>>> DashboardComponent: error: ', error)
      })

    this.email = this.authSvc.getEmail()??""

  }

  logout() {
    this.authSvc.logout()
  }

}
