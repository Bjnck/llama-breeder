import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Creature} from '../creature.interface';
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
  pen: Pen;
  creaturesIdInPen: string[];
  creaturesInPen: Creature[];

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

  addToPen(creature: Creature) {
    this.addCreature(creature);
    this.penService.update(this.pen).subscribe(
      pen => this.onClose(),
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
    this.sendRemoveFromPen(creature).subscribe({
      next: value => this.onClose(false, true),
      error: err => this.addCreature(creature)
    });
  }

  private sendRemoveFromPen(creature: Creature): Observable<any> {
    this.removeCreature(creature);
    return this.penService.update(this.pen);
  }

  onDelete(creature: Creature) {
    if (this.pen.creatures.find(c => c.id === creature.id)) {
      this.sendRemoveFromPen(creature).subscribe({
        next: value => this.delete(creature),
        error: err => this.addCreature(creature)
      });
    } else {
      this.delete(creature);
    }
  }

  private removeCreature(creature: Creature) {
    this.pen.creatures.splice(
      this.pen.creatures.indexOf(this.pen.creatures.find(c => c.id === creature.id)), 1);

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
        CreatureService.decrementTotalElements();
        this.userService.updateCoins(this.user.coins + resp.coins);
        this.onClose(true);
      }
    });
  }

  onClose(isDelete = false, isRemoveFromPen = false) {
    const response: CreatureDetailsResponse = {
      creatureId: this.creature.id,
      baby: this.baby,
      deleted: false,
      removeFromPen: false
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
