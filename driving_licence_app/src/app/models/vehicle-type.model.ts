import { Vehicle } from "./vehicle.model";

export class VehicleType{
  constructor(
    private id: number,
    private name: string,
    private vehicleList: Vehicle[],
  ){}
}
