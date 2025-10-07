import { Vehicle } from './vehicle.model';

export class FuelType {
  constructor(
    private id: number,
    private name: string,
    private vehicles: Vehicle[]
  ) {}
  get getName(): string {
    return this.name;
  }
  set setName(value: string){
    this.name = value;
  }

  get getVehicles(): Vehicle[] {
    return this.vehicles;
  }
  set setVehicles(value: Vehicle[]){
    this.vehicles = value;
  }
}
