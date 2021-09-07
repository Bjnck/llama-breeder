import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-llama',
  templateUrl: './llama.component.svg'
})
export class LlamaComponent {
  @Input() color1: string;
  @Input() color2: string;
}
