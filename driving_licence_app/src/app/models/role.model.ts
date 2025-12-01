export class Role {
  constructor(private id: number, private name: string) {}

  get getId(): number {
    return this.id;
  }

  get getName(): string {
    return this.name;
  }

  set setName(newName: string) {
    this.name = newName;
  }
}
