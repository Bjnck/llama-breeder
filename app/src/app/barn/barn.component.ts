import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user/user.interface';
import {ActivatedRoute} from '@angular/router';
import {HeaderService} from '../shared/header/header.service';
import {UserService} from '../shared/user/user.service';
import {Creature} from '../shared/creature/creature.interface';
import {CreatureService} from '../shared/creature/creature.service';
import {MatDialog} from '@angular/material/dialog';
import {CreatureDetailsDialogComponent} from '../shared/creature/details/creature-details.dialog';

@Component({
  templateUrl: './barn.component.html',
  styleUrls: [
    './barn.component.sass',
    '../shared/filter/filter.sass',
    '../shared/shared-style.sass'
  ]
})
export class BarnComponent implements OnInit {
  user: User;
  creatures: Creature[];
  creatureCount: number;

  sex: string;

  throttle = 0;
  distance = 2;
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
  }

  toggleSexFilter(sex: string) {
    this.sex = sex;
    this.resetList();
  }

  resetList() {
    this.page = 0;
    this.creatureService.list(this.size, this.page, this.sex)
      .subscribe((creatures: Creature[]) => {
        this.creatures = creatures;
      });
  }

  onScroll() {
    this.creatureService.list(this.size, ++this.page, this.sex)
      .subscribe((creatures: Creature[]) => {
        this.creatures.push(...creatures);
      });
  }

  openDetails(creature: Creature) {
    this.dialog.open(CreatureDetailsDialogComponent, {
      data: creature,
      restoreFocus: false
    });
  }

  delete(creature: Creature) {
    this.creatureService.delete(creature).subscribe((resp: any) => {
      this.creatureCount--;
      this.creatures.splice(this.creatures.findIndex(i => i.id === creature.id), 1);
      this.creatureService.list(1, this.creatures.length, this.sex)
        .subscribe((creatures: Creature[]) => {
          if (creatures && creatures.length > 0 && !this.creatures.find((i: Creature) => i.id === creatures[0].id)) {
            this.creatures.push(...creatures);
          }
        });
    });
  }
}
