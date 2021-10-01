import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-creature-gene',
  templateUrl: './creature-gene.component.html'
})

export class CreatureGeneComponent {
  @Input() code: string;
}
