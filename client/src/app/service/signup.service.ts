import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { SignupRequest, Status } from '../components/model/model';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  constructor(private http: HttpClient) {}
    
  signup(signupReq: SignupRequest): Promise<Status> {
      const headers = new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Accept', 'application/json')

      return firstValueFrom(
        this.http.post<Status>('/auth/register', signupReq, { headers: headers })
      )
  }

}
