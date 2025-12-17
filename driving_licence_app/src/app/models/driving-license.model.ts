import { DrivingLessons } from './driving-lessons.model';

export class DrivingLicenseCategory {
  constructor(
    public id: number,
    public name: string,
    public minAge: number,
    public description: string
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
