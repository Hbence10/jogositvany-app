export class Message {
  constructor(
    private id: number,
    private content: string,
    private createdAt: Date
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
