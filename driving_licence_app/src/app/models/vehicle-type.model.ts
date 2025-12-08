import { Vehicle } from './vehicle.model';

export class VehicleType {
  constructor(
    public id: number,
    public name: string,
    public vehicleList: Vehicle[]
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
