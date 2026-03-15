import { Vehicle } from './vehicle.model';

export class VehicleType {
  constructor(
    public id: number,
    public name: string,
    public vehicleList: Vehicle[]
  ) {}
}
