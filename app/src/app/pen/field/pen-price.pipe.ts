import {Pipe, PipeTransform} from '@angular/core';
import {PenInfo} from '../pen.interface';

@Pipe({name: 'price'})
export class PenPricePipe implements PipeTransform {

  transform(penInfo: PenInfo, size: number): number {
    return penInfo.sizes.find(s => s.count === size).price;
  }
}
