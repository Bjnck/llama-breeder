import {Component, OnDestroy, OnInit} from '@angular/core';
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
import {CreatureUtil} from '../shared/creature/creature.util';

@Component({
  templateUrl: './barn.component.html',
  styleUrls: [
    './barn.component.sass',
    '../shared/filter/filter.sass',
    '../shared/shared-style.sass'
  ]
})
export class BarnComponent implements OnInit, OnDestroy {
  maturityMax = environment.maturityMax;
  maturityDivider = environment.maturityDivider;
  energyMax = environment.energyMax;
  energyDivider = environment.energyDivider;
  statsMax = environment.statsMax;
  breedingMax = environment.breedingMax;

  user: User;
  creatures: Creature[];
  totalCount: number;
  filterCount: number;
  pen: Pen;
  creaturesInPen: string[];
  // date updated at each list reset or timer, use to display baby ready status
  filterDate: Date;

  // filters
  generation: number;
  sex: string;
  inPen: boolean;
  baby: boolean;
  pregnant: boolean;
  old: boolean;

  throttle = 0;
  distance = 2;
  page = 0;
  size = 20;
  loading = false;

  // flag so 2 calls do not compute at the same time
  computing = false;

  detailsOpen = false;

  interval;
  inReload = false;

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

    this.setTimer();
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  private setTimer() {
    this.interval = setInterval(() => {
      this.filterDate = TimerUtil.utc(new Date());

      // do not update stats if list already in computing (filter change) or if still in reload or if details opened
      if (!this.computing && !this.inReload && !this.detailsOpen) {
        this.computing = true;
        this.inReload = true;
        let computeForLoop = true; // flag to only compute once

        // reload low energy, not needed if filters inPen or baby
        if (!this.inPen && !this.baby) {
          const lowEnergyCreatures = this.creatures
            .filter(creature => this.creaturesInPen.indexOf(creature.id) === -1)
            .filter(creature => creature.statistics.energy < this.energyMax);
          if (lowEnergyCreatures.length > 0) {
            this.reloadEnergy(lowEnergyCreatures, computeForLoop);
            computeForLoop = false;
          }
        }

        // reload pen related stats
        const inPenCreatures = this.creatures
          .filter(creature => this.creaturesInPen.indexOf(creature.id) > -1);
        if (inPenCreatures.length > 0) {
          this.reloadPenRelatedStatus(inPenCreatures, computeForLoop);
          computeForLoop = false;
        }

        // if no reload needed change computing flag back
        if (computeForLoop && this.computing) {
          this.computing = false;
        }
        this.inReload = false;
      }
    }, 10000);
  }

  private reloadEnergy(creatures: Creature[], compute: boolean) {
    // max 20 results returned by the backend
    const splice = creatures.splice(0, 20);
    this.reloadCreatures(compute, false, splice);
    if (splice.length === 20) {
      this.reloadEnergy(creatures, false);
    }
  }

  private reloadPenRelatedStatus(creatures: Creature[], compute: boolean) {
    this.reloadCreatures(compute, true, creatures);
  }

  private reloadCreatures(compute: boolean, inPen: boolean, creatures: Creature[]) {
    this.creatureService.list(this.size, 0, compute, {inPen, ids: creatures.map(c => c.id)})
      .subscribe({
        next: (updatedCreatures: Creature[]) => {
          if (compute && this.computing) {
            this.computing = false;
          }
          updatedCreatures.forEach(updatedCreature => {
            const creature = this.creatures.find(c => c.id === updatedCreature.id);
            if (creature) {
              CreatureUtil.updateStats(creature, updatedCreature);
              // remove form filtered list if no more a baby
              if (this.baby && creature.statistics.maturity >= this.maturityMax) {
                this.deleteFromList(creature.id);
              }
            }
          });
        }
      });
  }


  toggleGenerationFilter(generation: number) {
    this.generation = generation;
    this.resetList();
  }

  toggleSexFilter(sex: string) {
    this.sex = sex;
    this.resetList();
  }

  toggleInPenFilter(inPen: boolean) {
    this.inPen = inPen;
    this.resetList();
  }

  toggleOldFilter(old: boolean) {
    this.old = old;
    this.resetList();
  }

  togglePregnantFilter(pregnant: boolean) {
    this.pregnant = pregnant;
    this.resetList();
  }

  toggleBabyFilter(baby: boolean) {
    this.baby = baby;
    this.resetList();
  }

  resetList() {
    this.page = 0;
    this.filterDate = TimerUtil.utc(new Date());
    let compute = false;
    if (!this.computing) {
      this.computing = true;
      compute = true;
    }
    this.creatureService.list(this.size, this.page, compute, this.getSearchParams())
      .subscribe((creatures: Creature[]) => {
        if (compute && this.computing) {
          this.computing = false;
        }
        this.creatures = creatures;
        this.filterCount = CreatureService.getFilterElements();
      });
  }

  onScroll() {
    if (this.creatures.length === (this.page + 1) * this.size) {
      this.loading = true;
    }
    this.creatureService.list(this.size, ++this.page, false, this.getSearchParams())
      .subscribe((creatures: Creature[]) => {
        if (creatures.length < this.size) {
          this.loading = false;
        }
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
    if (this.inPen) {
      search.inPen = true;
    }
    if (this.baby) {
      search.maxMaturity = (this.statsMax - 1).toString();
    }
    if (this.pregnant) {
      search.pregnant = true;
    }
    if (this.old) {
      search.minPregnancyCount = this.breedingMax.toString();
    }
    return search;
  }

  openDetails(creature: Creature) {
    this.detailsOpen = true;
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
        if (response.deleted) {
          this.deleteFromList(response.creatureId);
        }
        if (response.removeFromPen && this.inPen) {
          this.deleteFromList(response.creatureId);
        }
      },
      complete: () => this.detailsOpen = false
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
    if (this.inPen || this.pregnant || this.old) {
      return false;
    }
    return true;
  }
}
