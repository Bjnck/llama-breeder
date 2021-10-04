import {Component, Input} from '@angular/core';
import {PenWithContent} from '../pen-with-content.interface';
import {MatDialog} from '@angular/material/dialog';
import {Creature} from '../../shared/creature/creature.interface';
import {CreatureDetailsDialogComponent} from '../../shared/creature/details/creature-details.dialog';

@Component({
  selector: 'app-pen-field',
  templateUrl: './pen-field.component.html',
  styleUrls: [
    './pen-field.component.sass'
  ]
})
export class PenFieldComponent {
  @Input() pen: PenWithContent;
  @Input() updatedCreatures: Creature[];

  constructor(private dialog: MatDialog) {
  }

  get creatures(): Creature[] {
    return this.pen.creatures.sort((a, b) => a.id.toString().localeCompare(b.id));
  }

  openDetails(creature: Creature) {
    this.dialog.open(CreatureDetailsDialogComponent, {
      data: {
        creature,
        pen: this.pen.pen,
        creaturesInPen: this.pen.creatures,
        creaturesIdInPen: this.pen.creatures.map(c => c.id),
        closeOnRemove: true
      },
      position: {top: '25%'},
      restoreFocus: false
    });
  }
}
