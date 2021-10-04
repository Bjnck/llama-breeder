import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Creature} from '../creature.interface';
import {CreatureService} from '../creature.service';
import {Pen} from '../../../pen/pen.interface';
import {CreatureDetailsData} from './creature-details-data.interface';
import {PenService} from '../../../pen/pen.service';

@Component({
  templateUrl: './creature-details.dialog.html',
  styleUrls: [
    './creature-details.dialog.sass'
  ]
})
export class CreatureDetailsDialogComponent {
  creature: Creature;
  pen: Pen;
  creaturesIdInPen: string[];
  creaturesInPen: Creature[];
  closeOnRemove = false;

  name: string;

  constructor(@Inject(MAT_DIALOG_DATA) public data: CreatureDetailsData,
              private creatureService: CreatureService,
              private penService: PenService) {
    this.creature = data.creature;
    this.pen = data.pen;
    this.creaturesIdInPen = data.creaturesIdInPen;
    this.creaturesInPen = data.creaturesInPen;
    if (data.closeOnRemove) {
      this.closeOnRemove = data.closeOnRemove;
    }
    this.name = this.creature.name;
  }

  onNameChange(value: string) {
    if (value !== this.name) {
      this.name = value;
      this.creature.name = value;
      // todo not yet available
      // this.creatureService.update(this.creature);
    }
  }

  addToPen(creature: Creature) {
    this.pen.creatures.push({id: creature.id});
    if (this.creaturesIdInPen) {
      this.creaturesIdInPen.push(creature.id);
    }
    this.penService.update(this.pen).subscribe(
      pen => {
      },
      error => this.removeCreature(creature));
  }

  removeFromPen(creature: Creature) {
    this.removeCreature(creature);
    this.penService.update(this.pen).subscribe(
      pen => {
      },
      error => {
        this.pen.items.push({id: creature.id});
        if (this.creaturesIdInPen) {
          this.creaturesIdInPen.push(creature.id);
        }
        if (this.creaturesInPen) {
          this.creaturesInPen.push(creature);
        }
      });
  }

  private removeCreature(creature: Creature) {
    this.pen.creatures.forEach((i, index) => {
      if (i.id === creature.id) {
        this.pen.creatures.splice(index, 1);
      }
    });

    if (this.creaturesIdInPen) {
      this.creaturesIdInPen.splice(this.creaturesIdInPen.indexOf(creature.id), 1);
    }
    if (this.creaturesInPen) {
      this.creaturesInPen.splice(
        this.creaturesInPen.indexOf(this.creaturesInPen.find(c => c.id === creature.id)), 1);
    }
  }
}
