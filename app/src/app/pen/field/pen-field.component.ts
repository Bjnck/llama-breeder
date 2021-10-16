import {Component, Input} from '@angular/core';
import {PenWithContent} from '../pen-with-content.interface';
import {MatDialog} from '@angular/material/dialog';
import {Creature} from '../../shared/creature/creature.interface';
import {CreatureDetailsDialogComponent} from '../../shared/creature/details/creature-details.dialog';
import {User} from '../../shared/user/user.interface';

@Component({
  selector: 'app-pen-field',
  templateUrl: './pen-field.component.html',
  styleUrls: [
    './pen-field.component.sass'
  ]
})
export class PenFieldComponent {
  @Input() user: User;
  @Input() pen: PenWithContent;
  @Input() updatedCreatures: Creature[];

  constructor(private dialog: MatDialog) {
  }

  get creatures(): Creature[] {
    return this.pen.creatures.sort((a, b) => a.id.toString().localeCompare(b.id));
  }

  openDetails(creature: Creature) {
    // todo send user to dialog (needed for birth)
    this.dialog.open(CreatureDetailsDialogComponent, {
      data: {
        user: this.user,
        creature,
        pen: this.pen.pen,
        creaturesInPen: this.pen.creatures,
        creaturesIdInPen: this.pen.creatures.map(c => c.id)
      },
      disableClose: true,
      position: {top: '50px'},
      width: '100%',
      maxWidth: '500px',
      minWidth: '340px',
      restoreFocus: false
    });
  }
}
