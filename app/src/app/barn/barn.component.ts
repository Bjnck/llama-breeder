import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user/user.interface';
import {ActivatedRoute} from '@angular/router';
import {HeaderService} from '../shared/header/header.service';
import {UserService} from '../shared/user/user.service';
import {Creature} from '../shared/creature/creature.interface';
import {CreatureService} from '../shared/creature/creature.service';
import {MatDialog} from '@angular/material/dialog';
import {CreatureDetailsDialogComponent} from '../shared/creature/details/creature-details.dialog';
import {Pen} from '../pen/pen.interface';
import {environment} from '../../environments/environment';
import {CreatureDetailsResponse} from '../shared/creature/details/creature-details-response.interface';

@Component({
  templateUrl: './barn.component.html',
  styleUrls: [
    './barn.component.sass',
    '../shared/filter/filter.sass',
    '../shared/shared-style.sass'
  ]
})
export class BarnComponent implements OnInit {
  maturityMax = environment.maturityMax;
  maturityDivider = environment.maturityDivider;
  energyDivider = environment.energyDivider;

  user: User;
  creatures: Creature[];
  creatureCount: number;
  pen: Pen;
  creaturesInPen: string[];

  sex: string;

  throttle = 0;
  distance = 5;
  page = 0;
  size = 20;

  constructor(private headerService: HeaderService,
              private userService: UserService,
              private creatureService: CreatureService,
              private dialog: MatDialog,
              private route: ActivatedRoute) {
    this.headerService.showHeader('Barn', false);
  }

  ngOnInit() {
    this.user = this.route.snapshot.data.user;
    this.creatures = this.route.snapshot.data.creatures;
    this.creatureCount = this.route.snapshot.data.creatureCount.totalElements;
    this.pen = this.route.snapshot.data.pens[0];
    this.creaturesInPen = this.pen.creatures.map(creature => creature.id);
  }

  toggleSexFilter(sex: string) {
    this.sex = sex;
    this.resetList();
  }

  resetList() {
    this.page = 0;
    this.creatureService.list(this.size, this.page, false, this.sex)
      .subscribe((creatures: Creature[]) => {
        this.creatures = creatures;
      });
  }

  onScroll() {
    this.creatureService.list(this.size, ++this.page, false, this.sex)
      .subscribe((creatures: Creature[]) => {
        this.creatures.push(...creatures);
      });
  }

  openDetails(creature: Creature) {
    this.dialog.open(CreatureDetailsDialogComponent, {
      data: {user: this.user, creature, pen: this.pen, creaturesIdInPen: this.creaturesInPen},
      disableClose: true,
      position: {top: '50px'},
      width: '100%',
      maxWidth: '500px',
      minWidth: '340px',
      restoreFocus: false,
      // hasBackdrop: true,
      // backdropClass: 'backdrop-dialog'
    }).afterClosed().subscribe({
      next: (response: CreatureDetailsResponse) => {
        if (response.baby) {
          this.addBabyToList(response.baby);
        }

        if (response.deletedId) {
          this.creatureCount--;
          this.creatures.splice(this.creatures.findIndex(
            i => i.id.toString() === response.deletedId.toString()), 1);
          // todo must list again using filters
          this.creatureService.list(1, this.creatures.length, false)
            .subscribe((creatures: Creature[]) => {
              if (creatures && creatures.length > 0 && !this.creatures.find(
                (i: Creature) => i.id.toString() === creatures[0].id.toString())) {
                this.creatures.push(...creatures);
              }
            });
        }
      }
    });
  }

  private addBabyToList(creature: Creature) {
    // todo also depends on the filters
    if (this.creatures.length < (this.page + 1) * this.size) {
      this.creatureCount++;
      this.creatures.push(creature);
    }
  }
}
