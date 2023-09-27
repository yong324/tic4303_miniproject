import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Account, Interest } from '../components/model/model';

@Injectable({
  providedIn: 'root'
})
export class ProtectedService {

  constructor(private http: HttpClient) {}
  
    // call backend to retrieve a list of existing accounts in DB for ADMIN user
    getInterests(): Promise<Interest[]> {
      const headers = new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Accept', 'application/json')
      
      return firstValueFrom<Interest[]>(
        this.http.get<Interest[]>('/api/dashboard/interests', { headers: headers })
      )
    }

    submitForm(interest: Interest): Promise<Interest[]> {
      const headers = new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Accept', 'application/json')
      
      return firstValueFrom<Interest[]>(
        this.http.post<Interest[]>('/api/submit', interest, { headers: headers })
      )
    }

}