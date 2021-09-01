import {Pipe, PipeTransform} from '@angular/core';
import {ShopItem} from "../item/shop-item.interface";

@Pipe({name: 'shop_category'})
export class ShopCategoryPipe implements PipeTransform {

  transform(shopItems: ShopItem[], category: string): ShopItem[] {
    return  shopItems.filter(shopItem => shopItem.code.toLowerCase() === category.toLowerCase())
      .sort((a, b) => a.quality - b.quality);
  }
}
