import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Item} from '../../shared/item/item.interface';
import {PenService} from '../pen.service';
import {Creature} from '../../shared/creature/creature.interface';
import {PenWithContent} from '../pen-with-content.interface';

@Component({
  selector: 'app-pen-items',
  templateUrl: './pen-items.component.html',
  styleUrls: [
    './pen-items.component.sass'
  ]
})
export class PenItemsComponent {
  @Input() pen: PenWithContent;

  private active = false;

  @Output() creatureUpdate: EventEmitter<Creature> = new EventEmitter<Creature>();
  @Output() creaturesUpdate: EventEmitter<Creature[]> = new EventEmitter<Creature[]>();
  @Output() itemDelete: EventEmitter<Item> = new EventEmitter<Item>();

  constructor(private penService: PenService) {
  }

  get items(): Item[] {
    return this.pen.items.sort((a, b) => a.id.localeCompare(b.id));
  }

  onActivation(item: Item) {
    if (!this.active) {
      this.active = true;
      this.penService.activateItem(this.pen.pen, item)
        .subscribe({
          next: (penActivation) => {
            const itemToUpdate = this.pen.items.find(i => i.id === penActivation.item.id);
            if (itemToUpdate) {
              itemToUpdate.life = penActivation.item.life;
            }
            this.creaturesUpdate.emit(penActivation.creatures);
            penActivation.creatures.forEach(creature => this.creatureUpdate.emit(creature));
          },
          complete: () => this.active = false
        });
    }
  }

  delete(item: Item) {
    if (item.life <= 0) {
      this.itemDelete.emit(item);
    }
  }
}
