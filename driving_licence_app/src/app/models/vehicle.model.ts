import { FuelType } from "./fuel-type.model";
import { Instructors } from "./instructors.model";
import { VehicleType } from "./vehicle-type.model";

export class Vehicle {
  constructor(
    private id: number,
    private licensePlate: string,
    private name: string,
    private vehicleType: VehicleType,
    private fuelType: FuelType,
    private instructor: Instructors,
  ){}
}
