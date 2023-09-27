import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit{

  isLoggedIn!: boolean
  role = ""

  constructor(private authSvc: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authSvc.isLoggedIn()
    console.info('>>> ErrorComponent: ngOnInit(): isLoggedIn: ', this.isLoggedIn)
    this.role = this.authSvc.getRole()
    console.info('>>> ErrorComponent: ngOnInit(): checkRole(): ', this.role)
    // Redirect to another route after 10 seconds
    setTimeout(() => {
      this.router.navigate(['']); // Replace with your desired route
    }, 5000); // 10000 milliseconds = 10 seconds
  }

  logout() {
    this.authSvc.logout()
  }

}
