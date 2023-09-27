import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Status } from '../model/model';
import { LoginService } from 'src/app/service/login.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm!: FormGroup
  status!: Status
  email!: string
  role = ""

  constructor(private fb: FormBuilder, private loginSvc: LoginService, private authSvc: AuthService, private router: Router) {}
  
  ngOnInit(): void {
    this.loginForm = this.createForm()
    }

    doPost() {
      console.info('>>> loginForm: ngSubmit(): ', this.loginForm)
      this.email = this.loginForm.value.email
      this.status = {statusCode: 0, message:"wait..."}
      this.loginSvc.login(this.loginForm.value)
        .then(result => {
          console.info('>>> loginForm: result: ', result)
          // save token into local storage using authSvc
          this.authSvc.addToken(result.token)
          // save email into local storage using authSvc
          this.authSvc.addEmail(this.email)
          // save email into local storage using authSvc
          this.authSvc.addRole(this.authSvc.getRole())
          this.status.statusCode = 200
          this.status.message = "Log in success! Redirecting..."
          this.router.navigate(['/'])
        })
        .catch(error => {
          console.error('>>> loginForm: error: ', error)
          this.status.statusCode = 0
          this.status.message = "Invalid email or password!"
        })
    }

  private createForm() {
    return this.fb.group({
      email: this.fb.control('', Validators.required),
      password: this.fb.control('', [ Validators.required, Validators.email ] ),
    })
  }

}
