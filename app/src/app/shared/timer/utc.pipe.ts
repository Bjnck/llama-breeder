import {Pipe, PipeTransform} from '@angular/core';
import {TimerUtil} from './timer.util';

@Pipe({name: 'utc'})
export class UtcPipe implements PipeTransform {

  transform(date: string): Date {
    return TimerUtil.utc(new Date(date));
  }
}
