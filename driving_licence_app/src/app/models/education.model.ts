export class Education {
  constructor(public id?: number, public name?: string) {}

  get getId(): number {
    return this.id!;
  }

  get getName(): string {
    return this.name!;
  }
}
