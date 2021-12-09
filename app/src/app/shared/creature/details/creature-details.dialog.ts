import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Creature, CreatureInfo} from '../creature.interface';
import {CreatureService} from '../creature.service';
import {Pen} from '../../../pen/pen.interface';
import {CreatureDetailsData} from './creature-details-data.interface';
import {PenService} from '../../../pen/pen.service';
import {TimerUtil} from '../../timer/timer.util';
import {UserService} from '../../user/user.service';
import {User} from '../../user/user.interface';
import {environment} from '../../../../environments/environment';
import {RedeemCreatureDialogComponent} from '../redeem/redeem-creature.dialog';
import {CreatureDetailsResponse} from './creature-details-response.interface';
import {Observable} from 'rxjs';

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
  pens: Pen[];
  creaturesIdInPen: string[];
  creaturesInPen: Creature[];

  prices: CreatureInfo[];

  name: string;
  timeLeft: number;
  interval;
  baby: Creature;

  constructor(@Inject(MAT_DIALOG_DATA) public data: CreatureDetailsData,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<CreatureDetailsDialogComponent>,
              private creatureService: CreatureService,
              private penService: PenService,
              private userService: UserService) {
    this.user = data.user;
    this.creature = data.creature;
    this.pens = data.pens;
    this.creaturesIdInPen = data.creaturesIdInPen;
    this.creaturesInPen = data.creaturesInPen;
    this.prices = data.prices;
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

  addToPen(creature: Creature, pen: Pen) {
    this.addCreature(creature, pen);
    this.penService.update(pen).subscribe(
      next => this.onClose(),
      error => this.removeCreature(creature, pen));
  }

  addCreature(creature: Creature, pen: Pen) {
    pen.creatures.push({id: creature.id});
    if (this.creaturesIdInPen) {
      this.creaturesIdInPen.push(creature.id);
    }
    if (this.creaturesInPen) {
      this.creaturesInPen.push(creature);
    }
  }

  removeFromPen(creature: Creature) {
    const pen = this.getPen(creature);
    this.sendRemoveFromPen(creature, pen).subscribe(
      next => this.onClose(false, true),
      error => this.addCreature(creature, pen)
    );
  }

  private sendRemoveFromPen(creature: Creature, pen: Pen): Observable<any> {
    this.removeCreature(creature, pen);
    return this.penService.update(pen);
  }

  onDelete(creature: Creature) {
    const pen = this.getPen(creature);
    if (pen) {
      this.sendRemoveFromPen(creature, pen).subscribe(
        next => this.delete(creature),
        error => this.addCreature(creature, pen)
      );
    } else {
      this.delete(creature);
    }
  }

  private removeCreature(creature: Creature, pen: Pen) {
    pen.creatures.splice(
      pen.creatures.indexOf(pen.creatures.find(c => c.id === creature.id)), 1);

    if (this.creaturesIdInPen) {
      this.creaturesIdInPen.splice(this.creaturesIdInPen.indexOf(creature.id), 1);
    }
    if (this.creaturesInPen) {
      this.creaturesInPen.splice(
        this.creaturesInPen.indexOf(this.creaturesInPen.find(c => c.id === creature.id)), 1);
    }
  }

  private delete(creature: Creature) {
    this.creatureService.delete(creature).subscribe({
      next: resp => {
        // todo move updateCoins to creatureService in pipe
        this.userService.updateCoins(this.user.coins + resp.coins);
        this.onClose(true);
      }
    });
  }

  private getPen(creature: Creature): Pen {
    const pens = this.pens.filter(pen => pen.creatures.filter(c => c.id.toString() === creature.id.toString()).length > 0);
    if (pens.length > 0) {
      return pens[0];
    } else {
      return null;
    }
  }

  onClose(isDelete = false, isRemoveFromPen = false) {
    const response: CreatureDetailsResponse = {
      creatureId: this.creature.id,
      baby: this.baby,
      deleted: false,
      removeFromPen: false,
      creatures: this.creaturesInPen
    };
    if (isDelete) {
      response.deleted = true;
    }
    if (isRemoveFromPen) {
      response.removeFromPen = true;
    }
    this.dialogRef.close(response);
  }


  private setTimer() {
    this.timeLeft = TimerUtil.timeLeft(TimerUtil.utc(new Date(this.creature.pregnancyEndTime)));
    if (TimerUtil.utc(new Date(this.creature.pregnancyEndTime)) >= TimerUtil.utc(new Date())) {
      this.timeLeft = TimerUtil.timeLeft(TimerUtil.utc(new Date(this.creature.pregnancyEndTime)));
      this.interval = setInterval(() => {
        if (this.timeLeft > 0) {
          this.timeLeft = TimerUtil.timeLeft(TimerUtil.utc(new Date(this.creature.pregnancyEndTime)));
        } else {
          clearInterval(this.interval);
        }
      }, 100);
    }
  }

  redeem() {
    this.dialog.open(RedeemCreatureDialogComponent, {
      disableClose: true,
      data: {creature: this.creature, user: this.user},
      position: {top: '50px'},
      width: '100%',
      maxWidth: '500px',
      minWidth: '340px',
      restoreFocus: false
    }).afterClosed().subscribe({
      next: (creature: Creature) => {
        if (creature) {
          this.baby = creature;
          this.creature.pregnant = false;
          this.creature.pregnancyStartTime = null;
          this.creature.pregnancyEndTime = null;
          this.creature.pregnancyMale = null;
        }
      }
    });
  }
}
