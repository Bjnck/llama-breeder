import {Component} from '@angular/core';
import {Event, NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router} from '@angular/router';
import {MatIconRegistry} from '@angular/material/icon';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  loading = true;

  constructor(private router: Router,
              private matIconRegistry: MatIconRegistry,
              private sanitizer: DomSanitizer) {
    this.matIconRegistry.addSvgIcon('best', sanitizer.bypassSecurityTrustResourceUrl('../assets/icon/best.svg'));
    this.matIconRegistry.addSvgIcon('net', sanitizer.bypassSecurityTrustResourceUrl('../assets/icon/net.svg'));
    this.matIconRegistry.addSvgIcon('hay', sanitizer.bypassSecurityTrustResourceUrl('../assets/icon/hay.svg'));

    this.router.events.subscribe((event: Event) => {
      switch (true) {
        case event instanceof NavigationStart: {
          this.loading = true;
          break;
        }

        case event instanceof NavigationEnd:
        case event instanceof NavigationCancel:
        case event instanceof NavigationError: {
          this.loading = false;
          break;
        }
        default: {
          break;
        }
      }
    });
  }

}
