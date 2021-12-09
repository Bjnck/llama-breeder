import {Component, EventEmitter, Input, Output} from '@angular/core';
import {PenWithContent} from '../pen-with-content.interface';
import {MatDialog} from '@angular/material/dialog';
import {Creature, CreatureInfo} from '../../shared/creature/creature.interface';
import {CreatureDetailsDialogComponent} from '../../shared/creature/details/creature-details.dialog';
import {User} from '../../shared/user/user.interface';
import {CreatureDetailsResponse} from '../../shared/creature/details/creature-details-response.interface';
import {environment} from '../../../environments/environment';
import {Pen, PenInfo} from '../pen.interface';
import {PenService} from '../pen.service';

@Component({
  selector: 'app-pen-field',
  templateUrl: './pen-field.component.html',
  styleUrls: [
    './pen-field.component.sass'
  ]
})
export class PenFieldComponent {
  maxSize = environment.penMaxSize;

  @Input() user: User;
  @Input() pen: PenWithContent;
  @Input() price: PenInfo;
  @Input() updatedCreatures: Creature[];
  @Input() creaturePrices: CreatureInfo[];

  @Output() detailsDialogEventEmitter: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() extendEventEmitter: EventEmitter<Pen> = new EventEmitter<Pen>();

  constructor(private dialog: MatDialog,
              private penService: PenService) {
  }

  get creatures(): Creature[] {
    return this.pen.creatures.sort((a, b) => a.id.toString().localeCompare(b.id));
  }

  openDetails(creature: Creature) {
    this.detailsDialogEventEmitter.emit(true);
    this.dialog.open(CreatureDetailsDialogComponent, {
      data: {
        user: this.user,
        creature,
        pens: [this.pen.pen],
        creaturesInPen: this.pen.creatures,
        creaturesIdInPen: this.pen.creatures.map(c => c.id),
        prices: this.creaturePrices
      },
      disableClose: true,
      position: {top: '50px'},
      width: '100%',
      maxWidth: '500px',
      minWidth: '340px',
      restoreFocus: false
    }).afterClosed().subscribe({
      next: (value: CreatureDetailsResponse) => this.pen.creatures = value.creatures,
      complete: () => this.detailsDialogEventEmitter.emit(false)
    });
  }

  extend(size: number) {
    const originalSize = this.pen.pen.size;
    this.pen.pen.size = size;
    this.penService.update(this.pen.pen).subscribe(
      next => this.extendEventEmitter.emit(this.pen.pen),
      error => this.pen.pen.size = originalSize
    );
  }
}
