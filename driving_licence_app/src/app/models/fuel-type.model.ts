import { Vehicle } from './vehicle.model';

export class FuelType {
  constructor(
    public id?: number,
    public name?: string,
    public vehicles?: Vehicle[]
  ) {}

  get getId(): number {
    return this.id!
  }

  get getName(): string {
    return this.name!;
  }
  set setName(value: string) {
    this.name = value;
  }

  get getVehicles(): Vehicle[] {
    return this.vehicles!;
  }
  set setVehicles(value: Vehicle[]) {
    this.vehicles = value;
  }
}
