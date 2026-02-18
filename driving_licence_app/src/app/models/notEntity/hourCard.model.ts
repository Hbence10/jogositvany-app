export class HourCard {
  constructor(
    public startTime: Date,
    public endTime: Date,
    public name: string,
    public drivingLessonId: number,
    public date: Date
  ) {}
}
