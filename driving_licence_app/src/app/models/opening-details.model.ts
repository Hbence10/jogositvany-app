import { School } from "./school.model";

export class OpeningDetails{
  constructor(
    private id: number,
    private openingTime: Date,
    private closeTime: Date,
    private day: string,
    private schoolOpeningDetail: School,
  ){}
}
