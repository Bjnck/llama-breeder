import {Injectable} from '@angular/core';
import {AngularFireAuth} from '@angular/fire/auth';
import {Router} from '@angular/router';
import firebase from 'firebase/app';
import {TimerUtil} from '../timer/timer.util';

@Injectable()
export class AuthService {

  private tokenId: string;
  private user: firebase.User;

  interval;
  tokenDate: Date;

  constructor(private auth: AngularFireAuth,
              private router: Router) {
    this.auth.idToken.subscribe({
      next: value => {
        this.tokenId = value;
        this.tokenDate = new Date();
      }
    });
    this.auth.user.subscribe({next: value => this.user = value});

    this.setTimer();
  }

  private setTimer() {
    this.interval = setInterval(() => {
      // less than 5 min remaining
      if (this.user && TimerUtil.timeLeft(this.tokenDate) > 3300000) {
        this.user.getIdToken(true).then(value => {
          this.tokenId = value;
          this.tokenDate = new Date();
        });
      }
    }, 100);
  }

  getTokenId(): string {
    this.user.getIdToken().then(value => this.tokenId = value);
    return this.tokenId;
  }

  login() {
    const provider = new firebase.auth.GoogleAuthProvider();
    provider.setCustomParameters({prompt: 'select_account'});
    this.auth.signInWithRedirect(provider);
  }

  logout() {
    this.auth.signOut().then(() => this.router.navigate(['/login']));
  }

  delete() {
    this.user.delete().then(() => this.logout());
  }
}
