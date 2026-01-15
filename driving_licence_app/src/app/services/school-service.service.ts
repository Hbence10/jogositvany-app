import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { School } from '../models/school.model';
import { ExamRequest } from '../models/exam-request.model';
import { SchoolJoinRequest } from '../models/school-join-request.model';
import { OpeningDetails } from '../models/opening-details.model';

@Injectable({
  providedIn: 'root'
})
export class SchoolServiceService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/school';

  constructor() { }

  getSchoolById(id: number): Observable<School> {
    return this.http.get<School>(`${this.baseUrl}/${id}`)
  }
  getSchoolsBySearch(town: string): Observable<{id: number, name: string}[]>{
    return this.http.get<{id: number, name: string}[]>(`${this.baseUrl}?town=${town}`)
  }



  handleJoinRequest(id: number, status: "accept" | "refuse") {
    return this.http.post("", {})
  }

  getAllJoinRequest(id: number): Observable<SchoolJoinRequest[]> {
    return this.http.get<SchoolJoinRequest[]>("")
  }

  getAllExamRequest(id: number): Observable<ExamRequest[]> {
    return this.http.get<ExamRequest[]>("")
  }



  //Updatek
  updateSchool(schoolId: number, name: string, email: string, phone: string, country: string, town: string, address: string, promoText: string): Observable<School> {
    return this.http.put<School>(`${this.baseUrl}/update/${schoolId}`, {name: name, email: email, phone:phone, country: country, town: town, address:address, promoText: promoText})
  }

  updateOpeningDetails(schoolId: number, updatedOpeningDetails: OpeningDetails[]): Observable<School> {
    console.log(`${this.baseUrl}/${schoolId}/openingDetails`)
    return this.http.patch<School>(`${this.baseUrl}/${schoolId}/openingDetails`, updatedOpeningDetails)
  }

  changeBannerImg(schoolId: number, formData: FormData): Observable<School> {
    return this.http.patch<School>(`${this.baseUrl}/pfp/${schoolId}`, formData)
  }


  deleteSchool(schoolId: number) {
    return this.http.delete(`${this.baseUrl}/${schoolId}`)
  }
}
