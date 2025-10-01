import { Vehicle } from "./vehicle.model";

export class FuelType{
  constructor(
    private id: number,
    private name: string,
    private vehicles: Vehicle[]
  ){}
}
