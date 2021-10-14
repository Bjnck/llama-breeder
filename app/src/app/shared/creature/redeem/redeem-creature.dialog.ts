import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Creature} from '../creature.interface';
import {CreatureService} from '../creature.service';
import {User} from '../../user/user.interface';
import {RedeemCreatureData} from './redeem-creature-data.interface';

@Component({
  templateUrl: './redeem-creature.dialog.html',
  styleUrls: [
    './redeem-creature.dialog.sass'
  ]
})
export class RedeemCreatureDialogComponent implements OnInit, OnDestroy {
  user: User;
  creature: Creature;
  baby: Creature;

  interval;
  step = 0;

  constructor(@Inject(MAT_DIALOG_DATA) public data: RedeemCreatureData,
              private creatureService: CreatureService) {
    this.user = data.user;
    this.creature = data.creature;
  }

  ngOnInit(): void {
    this.setAnimation();
    this.creatureService.redeem(this.creature.id).subscribe({
      next: creature => this.baby = creature
    });
  }

  ngOnDestroy(): void {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  private setAnimation() {
    this.interval = setInterval(() => {
      this.step++;
      if (this.step > 3) {
        clearInterval(this.interval);
      }
    }, 800);
  }


}
