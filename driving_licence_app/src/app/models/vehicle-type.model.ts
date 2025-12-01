import { Vehicle } from './vehicle.model';

export class VehicleType {
  constructor(
    private id: number,
    private name: string,
    private vehicleList: Vehicle[]
  ) {}

  get getName(): string {
    return this.name;
  }
  set setName(value: string) {
    this.name = value;
  }

  get getVehicleList(): Vehicle[] {
    return this.vehicleList;
  }
  set setVehicleList(value: Vehicle[]) {
    this.vehicleList = value;
  }
}
