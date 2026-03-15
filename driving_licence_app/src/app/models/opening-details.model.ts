export class OpeningDetails {
  constructor(
    public id: number,
    public openingTime: Date,
    public closeTime: Date,
    public day: string,
    public isClosed: boolean,
  ) {}
}
