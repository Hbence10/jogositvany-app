import { DrivingLicenseCategory } from "./driving-license.model";

export class SchoolCategory {
  constructor(
    public id: number,
    public hourlyRate: number,
    public licenseCategory: DrivingLicenseCategory
  ) {}
}
