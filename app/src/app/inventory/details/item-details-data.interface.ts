import {Item} from '../../shared/item/item.interface';
import {User} from '../../shared/user/user.interface';
import {Pen} from '../../pen/pen.interface';

export interface ItemDetailsData {
  item: Item;
  pens: Pen[];
  itemsIdInPen: string[];
}


