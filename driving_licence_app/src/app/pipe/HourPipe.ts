import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "hour"
})
export class HourPipe implements PipeTransform {
  transform(value: string): string {
    return value.substring(0, 5);
  }
}
