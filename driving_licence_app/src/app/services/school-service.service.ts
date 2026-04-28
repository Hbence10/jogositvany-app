import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OpeningDetails } from '../models/opening-details.model';
import { School } from '../models/school.model';
import { SchoolCategory } from '../models/schoolCategory.model';

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
    return this.http.get<{id: number, name: string}[]>(`${this.baseUrl}/search?town=${town}`)
  }

  handleJoinRequest(id: number, status: "accept" | "refuse") {
    return this.http.post(`${this.baseUrl}/${id}/joinRequest`, {status: status})
  }

  getAllJoinRequest(id: number, pageNumber: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}/joinRequests?page=${pageNumber}&size=10`, {observe: "response"})
  }

  updateSchool(schoolId: number, name: string, email: string, phone: string, county: string, town: string, address: string, promoText: string): Observable<School> {
    return this.http.put<School>(`${this.baseUrl}/${schoolId}`, {schoolName: name, email: email, phoneNumber:phone, county: county, town: town, address:address, promoText: promoText})
  }

  updateOpeningDetails(schoolId: number, updatedOpeningDetails: OpeningDetails[]): Observable<School> {
    return this.http.patch<School>(`${this.baseUrl}/${schoolId}/openingDetails`, updatedOpeningDetails)
  }

  changeBannerImg(schoolId: number, formData: FormData): Observable<School> {
    return this.http.patch<School>(`${this.baseUrl}/${schoolId}/coverImg`, formData)
  }

  deleteSchool(schoolId: number) {
    return this.http.delete(`${this.baseUrl}/${schoolId}`)
  }

  createSchool(newSchool: {schoolName: string, email: string, phoneNumber: string, county: string, town: string, address: string, promoText: string, ownerId: number}) {
    return this.http.post(`${this.baseUrl}`, newSchool)
  }

  getMembersOfSchool(schoolId: number, role: string, pageNumber: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/users?schoolId=${schoolId}&role=${role}&page=${pageNumber}&size=10`, {observe: "response"})
  }

  kickOutInstructor(instructorId: number) {
    return this.http.delete(`${this.baseUrl}/kickout?instructorId=${instructorId}`)
  }

  getAllSchool(pageNumber: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}?page=${pageNumber}&size=10`, {observe: "response"})
  }

  setAdmin(email: string, schoolId: number) {
    return this.http.patch(`${this.baseUrl}/admin`, {email: email, schoolId: schoolId})
  }

  //
  addCategory(requestBody: {categoryId: number, schoolId: number, price: number}): Observable<SchoolCategory> {
    return this.http.post<SchoolCategory>(`${this.baseUrl}/category`, requestBody)
  }

  deleteCategory(id: number) {
    return this.http.delete(`${this.baseUrl}/category/${id}`)
  }
}
