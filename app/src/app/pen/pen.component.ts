import {Component, OnDestroy, OnInit} from '@angular/core';
import {HeaderService} from '../shared/header/header.service';
import {Pen} from './pen.interface';
import {ActivatedRoute} from '@angular/router';
import {PenWithContent} from './pen-with-content.interface';
import {Creature} from '../shared/creature/creature.interface';
import {PenService} from './pen.service';

@Component({
  templateUrl: './pen.component.html',
  styleUrls: [
    './pen.component.sass',
    '../shared/shared-style.sass'
  ]
})
export class PenComponent implements OnInit, OnDestroy {
  pen: PenWithContent;
  updatedCreatures: Creature[];

  interval;

  constructor(private headerService: HeaderService,
              private route: ActivatedRoute,
              private penService: PenService) {
    this.headerService.showHeader('Pen', false);
  }

  ngOnInit() {
    this.pen = this.route.snapshot.data.pens[0];
    this.setTimer();
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  private setTimer() {
    this.interval = setInterval(() => {
      this.penService.getWithContent(this.pen.pen.id).subscribe({
        next: pen => this.pen = pen
      });
    }, 10000);
  }

  onCreatureUpdate(creature: Creature) {
    const creatureToUpdate = this.pen.creatures.find(c => c.id === creature.id);
    if (creatureToUpdate) {
      creatureToUpdate.pregnant = creature.pregnant;
      creatureToUpdate.pregnancyStartTime = creature.pregnancyStartTime;
      creatureToUpdate.pregnancyEndTime = creature.pregnancyEndTime;
      creatureToUpdate.statistics.energy = creature.statistics.energy;
      creatureToUpdate.statistics.maturity = creature.statistics.maturity;
      creatureToUpdate.statistics.thirst = creature.statistics.thirst;
      creatureToUpdate.statistics.hunger = creature.statistics.hunger;
      creatureToUpdate.statistics.love = creature.statistics.love;
    }
  }

  onCreaturesUpdate(creatures: Creature[]) {
    this.updatedCreatures = creatures;
  }
}
