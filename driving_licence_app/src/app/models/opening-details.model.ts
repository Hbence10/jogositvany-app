import { School } from './school.model';

export class OpeningDetails {
  constructor(
    private id: number,
    private openingTime: Date,
    private closeTime: Date,
    private day: string,
    private schoolOpeningDetail: School
  ) {}
  get getOpeningTime(): Date {
    return this.openingTime;
  }
  set setOpeningTime(value: Date) {
    this.openingTime = value;
  }

  get getCloseTime(): Date {
    return this.closeTime;
  }
  set setCloseTime(value: Date) {
    this.closeTime = value;
  }

  get getDay(): string {
    return this.day;
  }
  set setDay(value: string) {
    this.day = value;
  }

  get getSchoolOpeningDetail(): School {
    return this.schoolOpeningDetail;
  }
  set setSchoolOpeningDetail(value: School) {
    this.schoolOpeningDetail = value;
  }
}
