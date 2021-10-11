import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Creature} from '../creature.interface';
import {CreatureService} from '../creature.service';
import {Pen} from '../../../pen/pen.interface';
import {CreatureDetailsData} from './creature-details-data.interface';
import {PenService} from '../../../pen/pen.service';
import {TimerUtil} from '../../timer/timer.util';
import {UserService} from '../../user/user.service';
import {User} from '../../user/user.interface';
import {environment} from '../../../../environments/environment';

@Component({
  templateUrl: './creature-details.dialog.html',
  styleUrls: [
    './creature-details.dialog.sass'
  ]
})
export class CreatureDetailsDialogComponent implements OnInit, OnDestroy {
  maturityMax = environment.maturityMax;
  maturityDivider = environment.maturityDivider;
  energyDivider = environment.energyDivider;
  statsLoveRequirement = environment.statsLoveRequirement;
  breedingMax = environment.breedingMax;

  user: User;
  creature: Creature;
  pen: Pen;
  creaturesIdInPen: string[];
  creaturesInPen: Creature[];

  name: string;
  timeLeft: number;
  interval;

  constructor(@Inject(MAT_DIALOG_DATA) public data: CreatureDetailsData,
              private dialogRef: MatDialogRef<CreatureDetailsDialogComponent>,
              private creatureService: CreatureService,
              private penService: PenService,
              private userService: UserService) {
    this.user = data.user;
    this.creature = data.creature;
    this.pen = data.pen;
    this.creaturesIdInPen = data.creaturesIdInPen;
    this.creaturesInPen = data.creaturesInPen;
    this.name = this.creature.name;
  }

  ngOnInit() {
    this.setTimer();
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  onNameChange(value: string) {
    if (value !== this.name) {
      this.name = value;
      this.creature.name = value;
      this.creatureService.update(this.creature);
    }
  }

  onDelete(creature: Creature) {
    this.creatureService.delete(creature).subscribe({
      next: resp => {
        this.userService.updateCoins(this.user.coins + resp.coins);
        this.dialogRef.close({delete: creature});
      }
    });
  }

  addToPen(creature: Creature) {
    this.addCreature(creature);
    this.penService.update(this.pen).subscribe(
      pen => {
      },
      error => this.removeCreature(creature));
  }

  addCreature(creature: Creature) {
    this.pen.creatures.push({id: creature.id});
    if (this.creaturesIdInPen) {
      this.creaturesIdInPen.push(creature.id);
    }
    if (this.creaturesInPen) {
      this.creaturesInPen.push(creature);
    }
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
      if (i.id.toString() === creature.id.toString()) {
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

  private setTimer() {
    this.timeLeft = TimerUtil.timeLeft(new Date(this.creature.pregnancyEndTime));
    if (TimerUtil.utc(new Date(this.creature.pregnancyEndTime)) >= TimerUtil.utc(new Date())) {
      this.timeLeft = TimerUtil.timeLeft(new Date(this.creature.pregnancyEndTime));
      this.interval = setInterval(() => {
        if (this.timeLeft > 0) {
          this.timeLeft = TimerUtil.timeLeft(new Date(this.creature.pregnancyEndTime));
        } else {
          // todo send event to notify barn to update creature in list
          clearInterval(this.interval);
        }
      }, 100);
    }
  }
}
