import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {Creature} from '../../shared/creature/creature.interface';
import {Pen} from '../pen.interface';
import {MatRipple} from '@angular/material/core';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-pen-field-creature',
  templateUrl: './pen-field-creature.component.html',
  styleUrls: [
    './pen-field-creature.component.sass'
  ]
})
export class PenFieldCreatureComponent implements OnChanges {
  maturityMax = environment.maturityMax;
  maturityDivider = environment.maturityDivider;
  energyDivider = environment.energyDivider;
  statsMax = environment.statsMax;
  statsLoveRequirement = environment.statsLoveRequirement;

  @Input() pen: Pen;
  @Input() creature: Creature;
  @Input() updatedCreatures: Creature[];

  @ViewChild(MatRipple) ripple: MatRipple;

  @Output() detailsEventEmitter: EventEmitter<Creature> = new EventEmitter<Creature>();

  openDetails(creature: Creature) {
    this.detailsEventEmitter.emit(creature);
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.updatedCreatures) {
      if (this.creature && !changes.updatedCreatures.isFirstChange() &&
        changes.updatedCreatures.currentValue.findIndex((c: Creature) => c.id === this.creature.id) > -1) {
        this.ripple.launch({centered: true});
      }
    }
  }
}
