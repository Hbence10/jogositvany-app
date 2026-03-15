import { FuelType } from './fuel-type.model';
import { Instructors } from './instructors.model';
import { VehicleType } from './vehicle-type.model';

export class Vehicle {
  constructor(
    public id: number,
    public licensePlate: string,
    public name: string,
    public vehicleType: VehicleType,
    public fuelType: FuelType,
    public instructor: Instructors
  ) {}
}
