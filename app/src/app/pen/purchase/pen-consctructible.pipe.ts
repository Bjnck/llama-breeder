import {Pipe, PipeTransform} from '@angular/core';
import {PenWithContent} from '../pen-with-content.interface';
import {environment} from '../../../environments/environment';

@Pipe({name: 'construct'})
export class PenConstructiblePipe implements PipeTransform {
  maxSize = environment.penMaxSize;

  transform(pens: PenWithContent[]): boolean {
    const filter = pens.filter(pen => pen.pen.size < this.maxSize);
    return filter.length > 0;
  }
}
