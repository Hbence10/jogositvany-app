export class Message {
  constructor(
    public id: number,
    public content: string,
    public createdAt: Date
  ) {}

  get getContent(): string {
    return this.content;
  }
  set setContent(value: string) {
    this.content = value;
  }

  get getCreatedAt(): Date {
    return this.createdAt;
  }
  set setCreatedAt(value: Date) {
    this.createdAt = value;
  }
}
