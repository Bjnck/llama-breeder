import {Component, OnDestroy, OnInit} from '@angular/core';
import {HeaderService} from '../shared/header/header.service';
import {ActivatedRoute} from '@angular/router';
import {PenWithContent} from './pen-with-content.interface';
import {Creature, CreatureInfo} from '../shared/creature/creature.interface';
import {PenService} from './pen.service';
import {Item} from '../shared/item/item.interface';
import {ItemService} from '../shared/item/item.service';
import {User} from '../shared/user/user.interface';
import {CreatureUtil} from '../shared/creature/creature.util';
import {MatTabChangeEvent} from '@angular/material/tabs';
import {Pen, PenInfo} from './pen.interface';

@Component({
  templateUrl: './pen.component.html',
  styleUrls: [
    './pen.component.sass',
    '../shared/shared-style.sass'
  ]
})
export class PenComponent implements OnInit, OnDestroy {
  user: User;

  creaturePrices: CreatureInfo[];

  pens: PenWithContent[];
  price1: PenInfo;
  price2: PenInfo;

  updatedCreatures: Creature[];

  focus = 1;

  detailsOpen = false;

  interval;

  constructor(private headerService: HeaderService,
              private route: ActivatedRoute,
              private penService: PenService,
              private itemService: ItemService) {
    this.headerService.showHeader('Pens', false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;

    this.creaturePrices = this.route.snapshot.data.creaturePrices;

    const prices: PenInfo[] = this.route.snapshot.data.prices;
    this.price1 = prices.find(price => price.count === 1);
    this.price2 = prices.find(price => price.count === 2);

    this.pens = this.route.snapshot.data.pens;
    this.pens.sort((a, b) => a.pen.id.toString().localeCompare(b.pen.id));

    this.setTimer(this.getPenFocus());
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  onFocusChange(event: MatTabChangeEvent) {
    this.focus = event.index + 1;
    this.setTimer(this.getPenFocus());
  }

  private getPenFocus(): PenWithContent {
    if (this.focus === 1) {
      return this.pens[0];
    }
    if (this.pens.length > 1 && this.focus === 2) {
      return this.pens[1];
    }
    return null;
  }

  setDetailsOpen(state: boolean) {
    this.detailsOpen = state;
  }

  private setTimer(pen: PenWithContent) {
    if (this.interval) {
      clearInterval(this.interval);
    }
    if (pen) {
      this.interval = setInterval(() => {
        if (!this.detailsOpen) {
          this.penService.getWithContent(pen.pen.id).subscribe(
            next => {
              pen.pen = next.pen;
              next.creatures.forEach(creature => this.onCreatureUpdate(creature));
              pen.items = next.items;
            }
          );
        }
      }, 10000);
    }
  }

  onCreatureUpdate(creature: Creature) {
    const creatureToUpdate = this.getPenFocus().creatures.find(c => c.id.toString() === creature.id.toString());
    if (creatureToUpdate) {
      CreatureUtil.updateStats(creatureToUpdate, creature);
    }
  }

  onCreaturesUpdate(creatures: Creature[]) {
    this.updatedCreatures = creatures;
  }

  onItemDelete(item: Item) {
    this.getPenFocus().items.splice(this.getPenFocus().items.findIndex(i => i.id.toString() === item.id.toString()), 1);
    this.getPenFocus().pen.items.splice(this.getPenFocus().pen.items.findIndex(i => i.id.toString() === item.id.toString()), 1);
    this.penService.update(this.getPenFocus().pen).subscribe(
      next => this.itemService.delete(item)
    );
  }

  onPenConstruct() {
    this.penService.create().subscribe(
      next => this.pens.push(next)
    );
  }

  onPenExtend(pen: Pen) {
    this.pens.find(p => p.pen.id === pen.id).pen.size = pen.size;
  }
}
