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
import {TimerUtil} from '../shared/timer/timer.util';
import {CreatureSearch} from '../shared/creature/creature-search.interface';

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
  statsMax = environment.statsMax;
  breedingMax = environment.breedingMax;

  user: User;
  creatures: Creature[];
  totalCount: number;
  filterCount: number;
  pen: Pen;
  creaturesInPen: string[];
  filterDate: Date;

  generation: number;
  sex: string;
  barn: boolean;
  love: boolean;
  baby: boolean;
  pregnant: boolean;
  redeem: boolean;
  old: boolean;

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

    this.totalCount = CreatureService.getTotalElements();
    this.filterCount = CreatureService.getFilterElements();

    this.filterDate = TimerUtil.utc(new Date());

    this.pen = this.route.snapshot.data.pens[0];
    this.creaturesInPen = this.pen.creatures.map(creature => creature.id);
  }

  toggleGenerationFilter(generation: number) {
    this.generation = generation;
    this.resetList();
  }

  toggleSexFilter(sex: string) {
    this.sex = sex;
    this.resetList();
  }

  toggleBarnFilter(barn: boolean) {
    this.barn = barn;
    this.resetList();
  }

  toggleLoveFilter(love: boolean) {
    this.love = love;
    this.resetList();
  }

  toggleOldFilter(old: boolean) {
    this.old = old;
    this.resetList();
  }

  toggleChildFilter(type: string) {
    if (!type) {
      this.redeem = null;
      this.baby = null;
      this.pregnant = null;
    } else if (type === 'redeem') {
      this.redeem = true;
      this.baby = null;
      this.pregnant = null;
    } else if (type === 'pregnant') {
      this.redeem = null;
      this.baby = null;
      this.pregnant = true;
    } else if (type === 'baby') {
      this.redeem = null;
      this.baby = true;
      this.pregnant = null;
    }
    this.resetList();
  }

  resetList() {
    this.page = 0;
    this.filterDate = TimerUtil.utc(new Date());
    this.creatureService.list(this.size, this.page, false, this.getSearchParams())
      .subscribe((creatures: Creature[]) => {
        this.creatures = creatures;
        this.filterCount = CreatureService.getFilterElements();
      });
  }

  onScroll() {
    this.creatureService.list(this.size, ++this.page, false, this.getSearchParams())
      .subscribe((creatures: Creature[]) => {
        this.creatures.push(...creatures);
      });
  }

  private getSearchParams(): CreatureSearch {
    const search: CreatureSearch = {};
    if (this.generation) {
      search.generation = this.generation.toString();
    }
    if (this.sex) {
      search.sex = this.sex;
    }
    if (this.barn) {
      search.inPen = true;
    }
    if (this.love) {
      search.minLove = this.statsMax.toString();
    }
    if (this.baby) {
      search.maxMaturity = (this.statsMax - 1).toString();
    }
    if (this.pregnant) {
      search.pregnant = true;
      search.minPregnancyEndTime = this.filterDate;
    }
    if (this.redeem) {
      search.maxPregnancyEndTime = this.filterDate;
    }
    if (this.old) {
      search.minPregnancyCount = this.breedingMax.toString();
    }
    return search;
  }

  openDetails(creature: Creature) {
    this.dialog.open(CreatureDetailsDialogComponent, {
      data: {user: this.user, creature, pen: this.pen, creaturesIdInPen: this.creaturesInPen},
      disableClose: true,
      position: {top: '50px'},
      width: '100%',
      maxWidth: '500px',
      minWidth: '340px',
      restoreFocus: false
    }).afterClosed().subscribe({
      next: (response: CreatureDetailsResponse) => {
        if (response.baby) {
          this.addBabyToList(response.baby);
        }
        if (response.deletedId) {
          this.deleteFromList(response.deletedId);
        }
      }
    });
  }

  private deleteFromList(id: string) {
    this.totalCount--;
    this.filterCount--;
    this.creatures.splice(this.creatures.findIndex(i => i.id.toString() === id.toString()), 1);
    this.creatureService.list(1, this.creatures.length, false, this.getSearchParams())
      .subscribe((creatures: Creature[]) => {
        if (creatures && creatures.length > 0 && !this.creatures.find(
          (i: Creature) => i.id.toString() === creatures[0].id.toString())) {
          this.creatures.push(...creatures);
        }
      });
  }

  private addBabyToList(creature: Creature) {
    this.totalCount++;
    if (this.babyMatchesFilter(creature)) {
      this.filterCount++;
      if (this.creatures.length < (this.page + 1) * this.size) {
        this.creatures.push(creature);
      }
    }
  }

  private babyMatchesFilter(creature: Creature): boolean {
    if (this.generation && creature.generation !== this.generation) {
      return false;
    }
    if (this.sex && creature.sex !== this.sex) {
      return false;
    }
    if (this.barn || this.love || this.pregnant || this.redeem || this.old) {
      return false;
    }
  }
}
