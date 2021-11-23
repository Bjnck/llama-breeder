import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'roman'})
export class RomanPipe implements PipeTransform {

  transform(value: number): string {
    if (value === 0) {
      return '0';
    }
    if (value === 1) {
      return 'I';
    }
    if (value === 2) {
      return 'II';
    }
    if (value === 3) {
      return 'III';
    }
    if (value === 4) {
      return 'IV';
    }
    if (value === 5) {
      return 'V';
    }
    if (value === 6) {
      return 'VI';
    }
    if (value === 7) {
      return 'VII';
    }
    if (value === 8) {
      return 'VIII';
    }
  }
}
