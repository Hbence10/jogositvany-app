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


  get getLicensePlate(): string {
    return this.licensePlate;
  }
  set setLicensePlate(value: string) {
    this.licensePlate = value;
  }

  get getName(): string {
    return this.name;
  }
  set setName(value: string) {
    this.name = value;
  }

  get getVehicleType(): VehicleType {
    return this.vehicleType;
  }
  set setVehicleType(value: VehicleType) {
    this.vehicleType = value;
  }

  get getFuelType(): FuelType {
    return this.fuelType;
  }
  set setFuelType(value: FuelType){
    this.fuelType = value;
  }

  get getInstructor(): Instructors {
    return this.instructor;
  }
  set setInstructor(value: Instructors){
    this.instructor = value;
  }
}
