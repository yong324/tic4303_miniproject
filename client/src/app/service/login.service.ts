import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { LoginRequest, LoginResp } from '../components/model/model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {}

    login(loginReq: LoginRequest): Promise<LoginResp> {
        const headers = new HttpHeaders()
            .set('Content-Type', 'application/json')
            .set('Accept', 'application/json')

        return firstValueFrom(
            this.http.post<LoginResp>('/auth/authenticate', loginReq, { headers: headers })
        )
    }
    
}
