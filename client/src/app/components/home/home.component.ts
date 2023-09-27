import { Component, OnInit } from '@angular/core';
import { EmailValidator, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { ProtectedService } from 'src/app/service/protected.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  isLoggedIn!: boolean
  submitForm!: FormGroup
  role = ""

  constructor(private fb: FormBuilder, private authSvc: AuthService, private protectedSvc: ProtectedService, private router:Router) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authSvc.isLoggedIn()
    console.info('>>> HomeComponent: ngOnInit(): isLoggedIn: ', this.isLoggedIn)
    this.role = this.authSvc.getRole()
    console.info('>>> HomeComponent: ngOnInit(): checkRole(): ', this.role)
    this.submitForm = this.createForm()
  }

  logout() {
    this.authSvc.logout()
  }

  doSubmit() {
    console.info('>>> HomeComponent: doSubmit(): submitForm: ', this.submitForm)
    this.protectedSvc.submitForm(this.submitForm.value)
      .then(result => {
        console.info('>>> HomeComponent: doSubmit(): result: ', result)
      })
      .catch(error => {
        console.error('>>> HomeComponent: doSubmit(): error: ', error)
      })
    this.router.navigate([ '/api/success' ])
  }

  private createForm() {
    return this.fb.group({
      name: this.fb.control('', Validators.required),
      email: this.fb.control('', [ Validators.required, Validators.email ] ),
      phone: this.fb.control('', Validators.required),
      country: this.fb.control('', Validators.required),
      gender: this.fb.control('', Validators.required),
      qualification: this.fb.control('', Validators.required),
    })
  }

}
