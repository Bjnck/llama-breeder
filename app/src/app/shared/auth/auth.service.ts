import {Injectable} from '@angular/core';
import {AngularFireAuth} from '@angular/fire/auth';
import {Router} from '@angular/router';
import firebase from 'firebase/app';
import {environment} from '../../../environments/environment';

@Injectable()
export class AuthService {
  production = environment.production;

  private user: firebase.User;

  constructor(private auth: AngularFireAuth,
              private router: Router) {
    this.auth.user.subscribe({next: value => this.user = value});
  }

  getUser(): firebase.User {
    return this.user;
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
    if (this.production) {
      this.user.delete().then(() => this.logout());
    } else {
      this.logout();
    }
  }
}
