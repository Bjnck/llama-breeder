import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Creature} from '../creature.interface';
import {CreatureService} from '../creature.service';

@Component({
  templateUrl: './creature-details.dialog.html',
  styleUrls: [
    './creature-details.dialog.sass'
  ]
})
export class CreatureDetailsDialogComponent {

  creature: Creature;

  name: string;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Creature,
              private creatureService: CreatureService) {
    this.creature = data;
    this.creature.name = 'Llama'; // todo remove this
    this.name = this.creature.name;
  }

  onNameChange(value: string) {
    if (value !== this.name) {
      this.name = value;
      this.creature.name = value;
      // todo not yet available
      // this.creatureService.update(this.creature);
    }
  }
}
