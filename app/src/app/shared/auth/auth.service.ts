import {Injectable} from '@angular/core';
import {AngularFireAuth} from '@angular/fire/auth';
import {Router} from '@angular/router';
import firebase from 'firebase/app';

@Injectable()
export class AuthService {

  private tokenId: string;
  private user: firebase.User;

  constructor(private auth: AngularFireAuth,
              private router: Router) {
    this.auth.idToken.subscribe({next: value => this.tokenId = value});
    this.auth.user.subscribe({next: value => this.user = value});
  }

  getTokenId(): string {
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
