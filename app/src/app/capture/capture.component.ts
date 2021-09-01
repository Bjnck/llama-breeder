import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {User} from "../shared/user/user.interface";
import {Capture} from "./capture.interface";
import {CaptureService} from "./capture.service";
import {AuthService} from "../shared/auth/auth.service";
import {HeaderService} from "../shared/header/header.service";

@Component({
  selector: 'capture',
  templateUrl: './capture.component.html',
})

export class CaptureComponent {
  user: User;
  captures: Capture[];

  constructor(private headerService: HeaderService,

    private authService: AuthService,
              private route: ActivatedRoute,
              private captureService: CaptureService) {
  }

  ngOnInit() {
    this.headerService.showHeader("Wild Land", false);
    //todo il faut aussi gérer le cookie token (mettre le code de home dans un abstract)
    this.user = this.route.snapshot.data.user;
    this.captures = this.route.snapshot.data.captures;
    // if (!this.authService.isLoggedIn())
    //   window.location.href = window.location.href.split('/')[0];
  }

  get activeCapture(): Capture {
    return this.captures.find(capture => new Date(capture.endTime) >= new Date());
  }

  get history(): Capture[] {
    return this.captures.filter(capture => new Date(capture.endTime) < new Date()).slice(0, 10);
  }

  onCaptureFinished() {
    console.log("finish");
    this.captureService.listCaptures(11).subscribe(captures => this.captures = captures);
  }
}
