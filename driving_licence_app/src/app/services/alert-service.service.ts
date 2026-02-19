import { Injectable } from '@angular/core';

interface alert {
  isShow: boolean,
  message: string,
  type: "success" | "error"
}

@Injectable({
  providedIn: 'root'
})
export class AlertServiceService {
  alert: alert = {isShow: false, message: "Sikeres regisztráció", type: "success"}

  constructor() { }

  setAlert(message: string, type: "success" | "error") {
    this.alert.isShow = true
    this.alert.message = message
    this.alert.type = type

    setTimeout(() => {
      this.alert.isShow = false
    }, 3000)
  }

}
