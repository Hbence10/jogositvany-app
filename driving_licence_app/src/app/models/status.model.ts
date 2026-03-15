import { DrivingLessons } from './driving-lessons.model';

export class Status {
  constructor(
    public id: number,
    public name: string,
    public drivingLessonList: DrivingLessons[],
    public requestList: Request[]
  ) {}
}
