import {Component, OnDestroy, OnInit} from '@angular/core';
import {HeaderService} from '../shared/header/header.service';
import {ActivatedRoute} from '@angular/router';
import {PenWithContent} from './pen-with-content.interface';
import {Creature} from '../shared/creature/creature.interface';
import {PenService} from './pen.service';
import {Item} from '../shared/item/item.interface';
import {ItemService} from '../shared/item/item.service';
import {User} from '../shared/user/user.interface';
import {CreatureUtil} from '../shared/creature/creature.util';

@Component({
  templateUrl: './pen.component.html',
  styleUrls: [
    './pen.component.sass',
    '../shared/shared-style.sass'
  ]
})
export class PenComponent implements OnInit, OnDestroy {
  user: User;
  pen: PenWithContent;
  updatedCreatures: Creature[];

  interval;

  constructor(private headerService: HeaderService,
              private route: ActivatedRoute,
              private penService: PenService,
              private itemService: ItemService) {
    this.headerService.showHeader('Pens', false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
    this.pen = this.route.snapshot.data.pens[0];
    this.setTimer();
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  private setTimer() {
    this.interval = setInterval(() => {
      this.penService.getWithContent(this.pen.pen.id).subscribe({
        next: pen => {
          this.pen.pen = pen.pen;
          pen.creatures.forEach(creature => this.onCreatureUpdate(creature));
          this.pen.items = pen.items;
        }
      });
    }, 10000);
  }

  onCreatureUpdate(creature: Creature) {
    const creatureToUpdate = this.pen.creatures.find(c => c.id.toString() === creature.id.toString());
    if (creatureToUpdate) {
      CreatureUtil.updateStats(creatureToUpdate, creature);
    }
  }

  onCreaturesUpdate(creatures: Creature[]) {
    this.updatedCreatures = creatures;
  }

  onItemDelete(item: Item) {
    this.pen.items.splice(this.pen.items.findIndex(i => i.id.toString() === item.id.toString()), 1);
    this.pen.pen.items.splice(this.pen.pen.items.findIndex(i => i.id.toString() === item.id.toString()), 1);
    this.penService.update(this.pen.pen).subscribe({
      next: value => this.itemService.delete(item)
    });
  }
}
