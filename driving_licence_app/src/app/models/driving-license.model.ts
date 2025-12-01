import { DrivingLessons } from './driving-lessons.model';

export class DrivingLicenseCategory {
  constructor(
    private id: number,
    private name: string,
    private minAge: number,
    private description: string
  ) {}

  get getName(): string {
    return this.name;
  }
  set setName(value: string) {
    this.name = value;
  }

  get getMinAge(): number {
    return this.minAge;
  }
  set setMinAge(value: number) {
    this.minAge = value;
  }

  get getDescription(): string {
    return this.description;
  }
  set setDescription(newDescription: string) {
    this.description = newDescription;
  }
}
