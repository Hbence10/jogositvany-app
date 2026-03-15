import { Vehicle } from './vehicle.model';

export class FuelType {
  constructor(
    public id?: number,
    public name?: string,
    public vehicles?: Vehicle[]
  ) {}
}
